package dao

import helpers.PostgresSupport
import models.Specialization
import play.api.db.slick.Config.driver.simple._

object Specializations extends PostgresSupport {
  lazy val specials = TableQuery[SpecializationTable]

  class SpecializationTable(tag: Tag) extends Table[Specialization](tag, "specializations") {
    def idSpecial = column[Int]("id_special", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name", O.NotNull)

    def * = (idSpecial.?, name) <> (Specialization.tupled, Specialization.unapply _)

  }

  def isTableEmpty = specials.list.isEmpty

  def findAll = specials.list

  def save(special: Specialization) = {
    special.idSpecial match {
      case None => {
        val id = (specials returning specials.map(_.idSpecial)) += special
        special.copy(idSpecial = Some(id))
      }
      case Some(id) => {
        val query = for {
          s <- specials if (s.idSpecial === id)
        } yield s
        query.update(special)
        special
      }
    }
  }
}