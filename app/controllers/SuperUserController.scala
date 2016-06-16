package controllers

import helpers.{EmailSender, TextToJson}
import jp.t2v.lab.play2.auth._
import jp.t2v.lab.play2.auth.AuthElement
import play.api.Logger
import scala.concurrent.ExecutionContext.Implicits.global
import models.{Project, Course, Account}
import play.api.libs.json.{Json, JsError}
import play.api.mvc.Controller
import repository.{AccountRepository, ProjectRepository, CourseRepository}
import security.{NormalUser, SuperUser}
import models._

object SuperUserController extends Controller with TextToJson with AuthElement with AuthConfigImpl {


  def saveCourse = authorizedAction(parse.json, SuperUser) { auth: Account => implicit rs =>
    rs.body.validate[Course].map {
      case create: Course =>
        Ok(Json.toJson(CourseRepository.saveCourse(create.copy(idUser = Some(auth.idUser.get)))))
    }.recoverTotal {
      e =>
        BadRequest("Detected error: " + JsError.toFlatJson(e))
    }
  }

  def findCourses = authorizedAction(SuperUser) {auth: Account => implicit rs =>
    Ok(Json.toJson(CourseRepository.findUserCourses(auth.idUser.get)))
  }

  def deleteCourse(idCourse: Int) = authorizedAction(SuperUser) { auth: Account => implicit rs =>
    CourseRepository.deleteCourse(idCourse)
    Ok(textToJson("Course deleted"))
  }

  def findCourseByProject(idCourse: Int) = authorizedAction(SuperUser) {auth: Account => implicit rs =>
    Ok(Json.toJson(CourseRepository.findProjectByCourse(idCourse)))
  }
  
  def findCourseProjects = authorizedAction(SuperUser) {auth: Account => implicit rs =>
    Ok(Json.toJson(CourseRepository.findCourseProjects(auth.idUser.get)))
  }

  def findProject(idProject: Int) = authorizedAction(SuperUser) { auth: Account => implicit rs =>
    Ok(Json.toJson(CourseRepository.findProject(idProject)))
  }

  def saveProject = authorizedAction(parse.json, SuperUser) { auth: Account => implicit rs =>
    rs.body.validate[Project].map {
      case create: Project =>
        val project = CourseRepository.saveProject(create.copy(
          idUser = auth.idUser.get,
          isTaken = false,
          isAccepted = true
        ))

        Ok(Json.toJson(project))
    }.recoverTotal {
      e =>
        BadRequest("Detected error: " + JsError.toFlatJson(e))
    }
  }

  def deleteProject(idProject: Int) = authorizedAction(SuperUser) { auth: Account => implicit rs =>
    Ok(Json.toJson(CourseRepository.deleteProject(idProject)))
  }

  def takeProject(idProject: Int) = authorizedAction(SuperUser) { auth: Account => implicit rs =>
    Logger.debug("take project: " + idProject)
    CourseRepository.takeProject(idProject)
    Ok(textToJson("Accept Project"))
  }
  
  def findUsers = authorizedAction(SuperUser) { auth: Account => implicit rs =>
    Ok(Json.toJson(AccountRepository.findNormalUsers(auth.idSpecialization.get)))
  }

  def findUser(idUser: Int) = authorizedAction(SuperUser) { auth: Account => implicit rs =>
    Ok(Json.toJson(AccountRepository.findUser(idUser)))
  }

  def saveUser = authorizedAction(parse.json, SuperUser) { auth: Account => implicit rs =>
    rs.body.validate[CreateAccount].map {
      case create: CreateAccount =>
        val user = AccountRepository.createAccount(Account.applyNow(create, NormalUser))
        Ok(Json.toJson(user))
    }.recoverTotal {
      e =>
        BadRequest("Detected error: " + JsError.toFlatJson(e))
    }
  }

  def updateUser = authorizedAction(parse.json, SuperUser) { auth: Account => implicit rs =>
    rs.body.validate[UpdateUser].map {
      case create: UpdateUser =>
        AccountRepository.updateUser(create)

        Ok(textToJson("Updated user"))
    }.recoverTotal {
      e =>
        BadRequest("Detected error: " + JsError.toFlatJson(e))
    }
  }
}
