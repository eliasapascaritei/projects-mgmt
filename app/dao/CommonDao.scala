package dao

import dao.Accounts.AccountTable
import dao.Courses.CourseTable
import dao.ProjectToUsers.ProjectToUserTable
import dao.Projects.ProjectTable
import dao.Specializations.SpecializationTable
import helpers.PostgresSupport
import models.Account
import play.api.db.slick.Config.driver.simple._

object CommonDao extends PostgresSupport {
  lazy val courses = TableQuery[CourseTable]
  lazy val users = TableQuery[AccountTable]
  lazy val projects = TableQuery[ProjectTable]
  lazy val specials = TableQuery[SpecializationTable]
  lazy val userProjects = TableQuery[ProjectToUserTable]

  def findCourseProject(idUser: Int) = {
    val q1 = for {
      (c, p) <- courses leftJoin projects if
        c.idUser === idUser &&
        p.idCourse === c.idCourse
    } yield (c, p.idProject.?, p.name.?, p.idUser.?)

    val q2 = for {
      (query, u) <- q1 leftJoin users on (_._4 === _.idUser)
    } yield (query._1, query._2, query._3, u.idUser.?, u.firstName.?, u.lastName.?)

    q2.list
  }

  def findUserSpecialization(idUser: Int) = {
    val query = for {
      u <- users if u.idUser === idUser
      s <- specials if s.idSpecial === u.idSpecialization
    } yield s

    query.list.head
  }

  def findUserCourses(auth: Account) = {
    val query = for {
      c <- courses if c.idSpecial === auth.idSpecialization
    } yield c

    query.list
  }

  def findUsersProjects(projectsIds: List[Int]) = {
    val query = for {
      p <- userProjects if p.idProject inSetBind projectsIds
      u <- users if u.idUser === p.idUser
    } yield (u, p)
    
    query.list
  }
}
