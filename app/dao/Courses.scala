package dao

import helpers.PostgresSupport
import models.{Course, Specialization}
import play.api.db.slick.Config.driver.simple._

object Courses extends PostgresSupport {
  lazy val courses = TableQuery[CourseTable]

  class CourseTable(tag: Tag) extends Table[Course](tag, "courses"){
    def idCourse = column[Int]("id_course", O.PrimaryKey, O.AutoInc)
    def idSpecial = column[Int]("id_special", O.NotNull)
    def idUser = column[Int]("id_user", O.NotNull)
    def name = column[String]("name", O.NotNull)
    def year = column[Int]("year", O.NotNull)

    def * =(idCourse.?, idSpecial, idUser.?, name, year) <> (Course.tupled, Course.unapply _)
  }

  def isTableEmpty: Boolean = courses.list.isEmpty

  def findByUserId(idUser: Int): List[Course] = {
    val query = for {
      c <- courses if c.idUser === idUser
    } yield c

    query.list
  }
  
  def findBySpecialId(idSpecial: Int): List[Course] = {
    val query = for {
      c <- courses if c.idSpecial === idSpecial
    } yield c

    query.list
  }

  def findBySpecialId(idSpecial: Int, idUser: Int): List[Course] = {
    val query = for {
      c <- courses if c.idSpecial === idSpecial &&
        c.idUser === idUser
    } yield c

    query.list
  }

  def findAll = courses.list

  def save(course: Course) = {
    course.idCourse match {
      case None => {
        val id = (courses returning courses.map(_.idCourse)) += course
        course.copy(idCourse = Some(id))
      }
      case Some(id) => {
        val query = for {
          s <- courses if s.idCourse === id
        } yield s
        query.update(course)
        course
      }
    }
  }

  def delete(idCourse: Int) = {
    val query = for {
      c <- courses if c.idCourse === idCourse
    } yield c

    query.delete
  }
}
