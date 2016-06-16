package repository

import contract.{ProjectUser, CourseProject}
import dao._
import models._
import play.api.Logger

object CourseRepository {
  lazy val amazonDownloadUrl = play.Play.application.configuration.getString("amazon_download_url")

  /**
   * save course to DB
   * @param course coruse to be added in DB
   * @return new creded course
   */
  def saveCourse(course: Course): Course = Courses.save(course)

  /**
   * search for user coruses in DB
   * @param idUser user id by who courses will be searched
   * @return a list of courses
   */
  def findUserCourses(idUser: Int): List[Course] = Courses.findByUserId(idUser)

  /**
   * search courses for specialization in DB
   * @return a list of courses
   */
  def findSpecialCourses(auth: Account) = {
    val courses = CommonDao.findUserCourses(auth)

    Logger.debug("courese: " + courses)

    val projects = Projects.findByCourseId(courses.map(_.idCourse.get)).filter {
      prj => prj.isAccepted || prj.idUser == auth.idUser.get
    }

    val users = Accounts.findUserById(projects.map(_.idUser))

    courses.map{ course =>
      CourseProject(
        idCourse = course.idCourse,
        idSpecialization = course.idSpecialization,
        idUser = course.idUser,
        name = course.name,
        year = course.year,
        projects = projects.filter(_.idCourse == course.idCourse.get).map{ proj =>
          ProjectUser(
            idProject = proj.idProject,
            projectName = Some(proj.name),
            idUser = Some(proj.idUser),
            userName = try {
              val user = users.find(_.idUser.get == proj.idUser).get
              Some(user.firstName + " " + user.lastName)
            }
            catch {
              case e: Exception => Some("")
            }
          )
        }
      )
    }
  }

  /**
   * find courses in DB by id specialization and id user
   * @param idSpecial specialization's id
   * @param idUser users's id
   * @return a list of courses
   */
  def findSpecialCourses(idSpecial: Int, idUser: Int): List[Course] =
    Courses.findBySpecialId(idSpecial, idUser)

  def deleteCourse(idCourse: Int) = {
    Projects.deleteByCourseId(idCourse)
    Courses.delete(idCourse)
  }

  /**
   *
   * @return return all specializations in db
   */
  def findSpecialization(): List[Specialization] = Specializations.findAll

  /**
   *
   * @param idUser: Int
   * @return
   */
  def findSpecialization(idUser: Int): Specialization = CommonDao.findUserSpecialization(idUser)

  /**
   * create new project
   * @param project project to create
   * @return new created project
   */
  def saveProject(project: Project): Project = Projects.save(project)

  def assignProjectToUser(idUser: Int, idProject: Int) = {
    ProjectToUsers.save(
      ProjectToUser(
        idProjectToUser = None,
        idProject = idProject,
        idUser = idUser
      )
    )
  }

  def findProjects(idUser: Int): List[UserProject] = {
    val courses: List[Course] = Courses.findAll


    Projects.findByUserId(idUser).map{ up =>
      UserProject.applyNow(up, Some(courses.find(_.idCourse.get == up.idCourse).get.name), idUser)

    }
  }

  /**
   * find project by it;s id
   * @param idProject project's id
   * @return a project
   */
  def findProject(idProject: Int): List[UserProject] = {
    val user = CommonDao.findUsersProjects(List(idProject))
    val projects = Projects.findById(idProject)
    val userName: Option[String] =
      user.find(_._2.idProject == idProject) match {
        case Some(usr) => Some(usr._1.firstName + " " + usr._1.lastName)
        case None => None
      }


    projects.map{ project =>
      if(project.s3Name.isDefined) {
        val s3Name = project.s3Name.get
        UserProject.applyNow(project, userName, 0).copy(s3Name = Some(amazonDownloadUrl + s3Name))
      }
      else
        UserProject.applyNow(project, userName, 0)
    }
  }

  /**
   * find all projects in a year for a specialization
   * @param year year to filter projects
   * @param idSpecial specialization's id
   * @return a list of projects.
   */
  def findYearProjects(year: Int, idSpecial: Int): List[Project] =
    Projects.findYearProjects(year, idSpecial)

  /**
   * get all projects for a course
   * @param idCourse course's id
   * @return a list of projects
   */
  def findProjectByCourse(idCourse: Int): List[UserProject] = {
    val projects = Projects.findByCourseId(idCourse)
    val users = CommonDao.findUsersProjects(projects.map(_.idProject.get))

    projects.map{ proj =>
      users.find(_._2.idProject == proj.idProject.get) match {
        case Some(obj) =>
          UserProject.applyNow(proj, Some(obj._1.firstName + obj._1.lastName), obj._1.idUser.get)
        case None =>
          UserProject.applyNow(proj, None, 0)
      }
    }
  }

  /**
   * ge a list of corses and for every course a list of projects and who applicated
   * for that project
   * @param idUser user's id
   * @return
   */
  def findsCourseProjects(idUser: Int): List[CourseProject] = {
    CommonDao.findCourseProject(idUser: Int).groupBy(_._1)
      .mapValues(_.map{v => (v._2, v._3, v._4, v._5, v._6)})
      .toList.map{
        case (course, objects) =>
          CourseProject(
            idCourse = course.idCourse,
            idSpecialization = course.idSpecialization,
            idUser = course.idUser,
            name = course.name,
            year = course.year,
            projects = objects.map{ obj =>
              ProjectUser(
                idProject = if(obj._1.isDefined) Some(obj._1.get) else None,
                projectName = if(obj._2.isDefined) Some(obj._2.get) else None,
                idUser = if(obj._3.isDefined) Some(obj._3.get) else None,
                userName = if(obj._1.isDefined) Some(obj._4.get + " " + obj._5.get) else None
              )
            }
          )
      }
  }

  /**
   * ge a list of corses and for every course a list of projects and who applicated
   * for that project
   * @param idUser user's id
   * @return
   */
  def findCourseProjects(idUser: Int): List[CourseProject] = {
    val courses = Courses.findByUserId(idUser)
    Logger.debug("courese: " + courses)
    val projects = Projects.findByCourseId(courses.map(_.idCourse.get))
    val users = CommonDao.findUsersProjects(projects.map(_.idProject.get))

    courses.map{ course =>
      CourseProject(
        idCourse = course.idCourse,
        idSpecialization = course.idSpecialization,
        idUser = course.idUser,
        name = course.name,
        year = course.year,
        projects = projects.filter(_.idCourse == course.idCourse.get).map{ proj =>
          ProjectUser(
            idProject = proj.idProject,
            projectName = Some(proj.name),
            idUser = Some(proj.idUser),
            userName = try {
              val user = users.find(_._2.idProject == proj.idProject.get).get
              Some(user._1.firstName + " " + user._1.lastName)
            }
            catch {
              case e: Exception => Some("")
            }
          )
        }
      )

    }
  }

  /**
   * assign project for a list of users
   * @param idUsers user's ids
   * @param idProject project id for that they applied
   */
  def assignProject(idUsers: List[Int], idProject: Int) = {
    if(!Projects.isProjectTaken(idProject)){
      Projects.assignProject(idProject)
      idUsers.foreach(idUser =>
        ProjectToUsers.save(
          ProjectToUser(
            idProjectToUser = None,
            idProject = idProject,
            idUser = idUser
          )
        )
      )
    }
  }

  /**
   * delete a project
   * @param idProject project's id
   * @return 1 if deletion has happend, 0 if not
   */
  def deleteProject(idProject: Int) = Projects.delete(idProject)

  /**
   * accept project, as a valid one
   * @param idProject project's id
   * @return 1 if succeded else 0
   */
  def takeProject(idProject: Int) = Projects.take(idProject)
}
