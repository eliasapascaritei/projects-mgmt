import java.io.File

import dao.{Courses, Specializations, Accounts}
import models.{Course, Specialization, Account}
import play.api._
import play.api.mvc.RequestHeader
import play.api.mvc.Results._
import repository.AccountRepository
import security.{NormalUser, SuperUser}

import scala.concurrent.Future

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    if (Specializations.isTableEmpty){
      Seq(
        Specialization(
          idSpecial = None,
          name = "Calculatoare"
        ),
        Specialization(
          idSpecial = None,
          name = "Automatica"
        ),
        Specialization(
          idSpecial = None,
          name = "Inginerie"
        )
      ).foreach(Specializations.save)
    }

    if (Accounts.isTableEmpty) {
      Seq(Account(
        idSpecialization = Some(1),
        email = "superuser@frunza.ro",
        lastName = "Super",
        firstName = "User",
        role = SuperUser,
        password = "123456"
      ),
        Account(
        idSpecialization = Some(1),
        email = "elias@elias.ro",
        lastName = "Elias",
        firstName = "Apascaritei",
        role = SuperUser,
        password = "123456"
      ),
        Account(
          idSpecialization = Some(1),
          email = "normaluser@frunza.ro",
          lastName = "Normal",
          firstName = "User",
          role = NormalUser,
          password = "123456"
        )
      ).foreach(AccountRepository.saveUser)
    }

    if(Courses.isTableEmpty) {
      Seq(
        Course(
          idCourse = None,
          idSpecialization = 1,
          idUser = Some(1),
          name = "PCLP",
          year = 1
        ),
        Course(
          idCourse = None,
          idSpecialization = 1,
          idUser = Some(1),
          name = "Matematica",
          year = 1
        )
      ).foreach(Courses.save)
    }
  }
}