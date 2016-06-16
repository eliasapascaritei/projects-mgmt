package controllers

import jp.t2v.lab.play2.auth.{LoginLogout, OptionalAuthElement}
import play.api._
import play.api.mvc._
import security.{NormalUser, SuperUser, Admin}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

object ApplicationController extends Controller with OptionalAuthElement with LoginLogout with AuthConfigImpl{

  def dashboard = StackAction{ implicit request =>
    loggedIn match {
      case Some(user) => {

        Logger.debug("user for debug: " + user)


        user.role match {
          case Admin => Ok(views.html.admin())
          case SuperUser => Ok(views.html.superUser())
          case NormalUser => Ok(views.html.user())
        }
      }
      case _ => Redirect(routes.ApplicationController.index())
    }
  }

  def index = Action.async { implicit request =>
    request.session.get("oauth-token").map { token =>
      gotoLoginSucceeded(token)
    }.getOrElse {
      Future(Ok(views.html.login(None)))
    }
  }

  def loginView = Action {
    Ok(views.html.login(None))
  }

  def logout = AsyncStack { implicit request =>
    gotoLogoutSucceeded.map(_.flashing(
      "success" -> "You've been logged out"
    ))
  }
}