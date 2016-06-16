package controllers

import controllers.SuperUserController._
import helpers.{EmailSender, TextToJson}
import jp.t2v.lab.play2.auth._
import jp.t2v.lab.play2.auth.AuthElement
import play.api.Logger
import scala.concurrent.ExecutionContext.Implicits.global
import models.{Project, Course, Account}
import play.api.libs.json.{Json, JsError}
import play.api.mvc.Controller
import repository.{ProjectRepository, CourseRepository}
import security.{SuperUser, NormalUser}
import models._

object NormalUserController extends Controller with TextToJson with AuthElement with AuthConfigImpl {


  def findSpecializations = authorizedAction(NormalUser) {auth: Account => implicit rs =>
    Ok(Json.toJson(CourseRepository.findSpecialization()))
  }
  
  def findAllSpecials = authorizedAction(NormalUser) {auth: Account => implicit rs =>
    Ok(Json.toJson(CourseRepository.findSpecialization(auth.idUser.get)))
  }
  
  //TODO assign project to user, or to users
  def saveProject = authorizedAction(parse.json, NormalUser) { auth: Account => implicit rs =>
    rs.body.validate[Project].map {
      case create: Project =>
        val project =  CourseRepository.saveProject(create.copy(
          idUser = auth.idUser.get,
          isAccepted = false,
          isTaken = true
        ))

        CourseRepository.assignProjectToUser(auth.idUser.get, project.idProject.get)

        Ok(Json.toJson(project))
    }.recoverTotal {
      e =>
        BadRequest("Detected error: " + JsError.toFlatJson(e))
    }
  }

  def findYearProjects(year: Int) = authorizedAction(NormalUser) { auth: Account => implicit rs =>
    Ok(Json.toJson(CourseRepository.findYearProjects(year, auth.idSpecialization.get)))
  }

  def findCourseProjects(idCourse: Int) = authorizedAction(NormalUser) { auth: Account => implicit rs =>
    Ok(Json.toJson(CourseRepository.findProjectByCourse(idCourse)))
  }

  def findProjects = authorizedAction(NormalUser) { auth: Account => implicit rs =>
    Ok(Json.toJson(CourseRepository.findProjects(auth.idUser.get)))
  }

  def findSpecialCourses = authorizedAction(NormalUser) { auth: Account => implicit rs =>
    Ok(Json.toJson(CourseRepository.findSpecialCourses(auth)))
  }

  def findUserCourses(idUser: Int) = authorizedAction(NormalUser) { auth: Account => implicit rs =>
    Ok(Json.toJson(CourseRepository.findSpecialCourses(auth.idSpecialization.get, idUser)))
  }

  def requestProject(idProject: Int) = authorizedAction(NormalUser) { auth: Account => implicit rs =>
    try {
      CourseRepository.assignProject(List(auth.idUser.get), idProject)
      Ok(textToJson("You just got a project, good job!"))
    }
    catch {
      case e: Exception =>
        BadRequest(textToJson("You can't get this project"))
    }
  }

  def findProject(idProject: Int) = authorizedAction(NormalUser) { auth: Account => implicit rs =>
    Ok(Json.toJson(CourseRepository.findProject(idProject)))
  }

  def assignProject(idProject: Int) = authorizedAction(NormalUser) { auth: Account => implicit rs =>
    CourseRepository.assignProject(List(auth.idUser.get), idProject)
    Ok(textToJson("Assign Complete"))
  }

  def uploadFile(idProject: Int, extension: String) = authorizedAction(parse.temporaryFile, NormalUser) { auth: Account => implicit rs =>
    val file = rs.body.file
    try {
      ProjectRepository.uploadProject(auth.idUser.get, idProject, file, extension)
      Ok(textToJson("file uploaded"))
    }
    catch {
      case e: Exception =>
        BadRequest(textToJson(e.getMessage))
    }
  }

  //TODO If superUser let him downoload, if user let him see only his projects
  def downloadFile(idProject: Int) = authorizedAction(parse.temporaryFile, NormalUser) { auth: Account => implicit rs =>
    try {
      ProjectRepository.downloadProject(idProject)
      Ok(textToJson("Download Complete"))
    }
    catch {
      case e: Exception =>
        Logger.debug("error at download: ")
        BadRequest(textToJson(e.getMessage + " download"))
    }
  }

  def sendContact = authorizedAction(parse.json, SuperUser) { auth: Account => implicit rs =>
    rs.body.validate[Contact].map {
      case create: Contact =>
        EmailSender.sendContact(create)
        Ok(textToJson("Problem sent"))
    }.recoverTotal {
      e =>
        BadRequest("Detected error: " + JsError.toFlatJson(e))
    }
  }
}
