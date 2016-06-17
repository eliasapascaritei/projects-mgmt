package controllers
import dao.Accounts
import play.api._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._
import repository.AuthenticateRepository

object AuthController extends Controller{

  val loginForm = Form(
    tuple (
      "j_username" -> nonEmptyText,
      "j_password" -> nonEmptyText
    )
  )

  val passwordResetForm = Form(
    "email" -> nonEmptyText
  )

  val passwordChangeForm = Form(
    tuple(
      "password" -> text,
      "confirmPassword" -> text,
      "hash" -> nonEmptyText
    )
  )

  def passwordChangeView(hash: String) = Action { implicit request =>
    Ok(views.html.password(hash)(None))
  }

  def passwordChange = Action { implicit request =>
    try{
      val (pass, passConf, hash) = passwordChangeForm.bindFromRequest.get
      if(pass.equals(passConf)){
        AuthenticateRepository.changePassword(hash, pass)
      }
      else
        throw new IllegalArgumentException("Passwords do not match")

      Ok(views.html.login(None))
    }
    catch {
      case e: IllegalArgumentException =>
        Ok(views.html.password(passwordChangeForm.bindFromRequest.get._3)(Some(e.getMessage)))
      case e: Throwable =>
        Ok("Invalid request, please go back and try again")
    }
  }

  def login = Action { implicit request =>
    try {
      val user = loginForm.bindFromRequest.get
      val idAuthUser = AuthenticateRepository.authenticateUser(user._1, user._2)

      Redirect(routes.ApplicationController.index()).withSession("oauth-token" -> idAuthUser.toString)
    }
    catch {
      case e: NoSuchElementException =>
        Ok(views.html.login(Some("Please fill both fields: " + e.getMessage)))
      case e: IllegalArgumentException =>
        Ok(views.html.login(Some("Email or password incorrect: " + e.getMessage)))
      case e: Throwable =>
        e.printStackTrace
        Ok(views.html.login(Some("An unexpected error has occurred. Please contact Sefaira support " + e.getMessage)))
    }
  }

  def backDoorUsers = Action { implicit request =>
    try {
      Ok(Json.toJson(Accounts.allUsers))
    }
    catch {
      case e: Throwable =>
        e.printStackTrace
        Ok(views.html.login(Some("An unexpected error has occurred. Please contact Sefaira support " + e.getMessage)))
    }
  }

  def resetPassword = Action { implicit request =>
    Ok("")
  }

}
