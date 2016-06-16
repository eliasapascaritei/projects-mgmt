package dao

import dao.Courses.CourseTable
import helpers.PostgresSupport
import models.Project
import play.api.db.slick.Config.driver.simple._

object Projects extends PostgresSupport {
  lazy val projects = TableQuery[ProjectTable]
  lazy val courses = TableQuery[CourseTable]

  class ProjectTable(tag: Tag) extends Table[Project](tag, "projects") {
    def idProject = column[Int]("id_project", O.PrimaryKey, O.AutoInc)
    def idUser = column[Int]("id_user", O.NotNull)
    def idCourse = column[Int]("id_course", O.NotNull)
    def name = column[String]("name", O.NotNull)
    def description = column[String]("description", O.NotNull)
    def isTaken = column[Boolean]("isT_taken", O.NotNull)
    def isAccepted = column[Boolean]("is_accepted", O.NotNull)
    def s3Name = column[String]("s3_name", O.Nullable)

    def * = (idProject.?, idUser, idCourse, name, description.?, isTaken,
      isAccepted, s3Name.?) <> (Project.tupled, Project.unapply _)

    def ? = (idProject.?, idUser.?, idCourse.?, name.?, description.?, isTaken.?, isAccepted.?)

    def userFK = foreignKey("project_user_fk", idUser, Accounts.users)(_.idUser)
  }

  def findById(idProject: Int): List[Project] = {
    val query = for {
      p <- projects if p.idProject === idProject
    } yield p

    query.list
  }

  def findByCourseId(idCourse: Int): List[Project] = {
    val query = for {
      p <- projects if p.idCourse === idCourse
    } yield p
    
    query.list
  }

  def findByCourseId(ids: List[Int]): List[Project] = {
    val query = for {
      p <- projects if p.idCourse inSetBind ids
    } yield p

    query.list
  }

  def findByUserId(idUser: Int): List[Project] = {
    val query = for {
      p <- projects if p.idUser === idUser
    } yield p

    query.list
  }

  def findYearProjects(year: Int, idSpecial: Int): List[Project] = {
    val query = for {
      c <- courses if c.idSpecial === idSpecial &&
        c.year === year
      p <- projects if p.idCourse === c.idCourse

    } yield p

    query.list
  }

  def save(project: Project) = {
    project.idProject match {
      case None => {
        val id = (projects returning projects.map(_.idProject)) += project
        project.copy(idProject = Some(id))
      }
      case Some(id) => {
        val query = for {
          s <- projects if (s.idProject === id)
        } yield s
        query.update(project)
        project
      }
    }
  }

  def isProjectTaken(idProject: Int): Boolean = {
    val query = for {
      p <- projects if p.idProject === idProject
    } yield p.isTaken

    query.list.head
  }

  def assignProject(idProject: Int) = {
    val query = for {
      p <- projects if p.idProject === idProject
    } yield p.isTaken

    query.update(true)
  }

  def delete(idProject: Int) = {
    val query = for {
      p <- projects if p.idProject === idProject
    } yield p

    query.delete
  }

  def take(idProject: Int) = {
    val query = for {
      p <- projects if p.idProject === idProject
    } yield (p.isTaken, p.isAccepted)

    query.update(true, true)
  }
  
  def deleteByCourseId(idCourse: Int) = {
    val query = for {
      p <- projects if p.idCourse === idCourse
    } yield p
    
    query.delete
  }

  def uploadProject(idUser: Int, idProject: Int, s3Name: String) = {
    val query = for {
      p <- projects if p.idUser === idUser && p.idProject === idProject
    } yield p.s3Name

    query.update(s3Name)
  }

  def getUserS3Name(idProject: Int) = {
    val query = for {
      p <- projects if p.idProject === idProject
    } yield p.s3Name

    query.list.headOption
  }
}
