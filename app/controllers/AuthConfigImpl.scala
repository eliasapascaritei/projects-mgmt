package controllers

import helpers.TextToJson
import jp.t2v.lab.play2.auth._
import models.Account
import play.api.Logger
import play.api.Play.current
import play.api.cache.Cache
import play.api.mvc.Results._
import play.api.mvc._
import repository.AuthenticateRepository
import security._

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.classTag

trait AuthConfigImpl extends AuthConfig with TextToJson{
  /*a type that is used to identify a user*/
  type Id = String
  /*a type that represents a user in your application*/
  type User = Account
  /*a type that is defined by every action for authorization*/
  type Authority = Role
  /*a 'classTag' is used for retrieve an id from Cache API*/
  val idTag = classTag[Id]
  /*the session timeout in seconds*/
  val sessionTimeoutInSeconds = 60 * 60 * 24

  def resolveUser(token: Id)(implicit ctx: ExecutionContext) = Future.successful{
    Logger.debug("Try to resolve user token: "+token)

    val account: Option[Account] = Cache.getAs[Account](token)
    if(account.isDefined) {
      Logger.debug("Auth User from cache: "+account)
      account
    }
    else {
      Logger.debug("Try to resolve user using oauth: "+token)

      val user = AuthenticateRepository.findUser(token.toInt)

      Logger.debug("User from db: "+user)

      user match {
        case Some(account: Account) =>
          Cache.set(token, account)
          Logger.debug("Auth User from db: " + account.toString)
          Some(account)
        case _ =>
          Logger.debug("Not User in db: "+user.toString)
          throw new IllegalAccessError("no permission!")
      }
    }
  }

  def loginSucceeded(request: RequestHeader)(implicit ctx: ExecutionContext) = Future.successful{
    Redirect(routes.ApplicationController.dashboard())
  }


  //TODO remove user from cache
  def logoutSucceeded(request: RequestHeader)(implicit ctx: ExecutionContext) = Future.successful{
    Redirect(routes.ApplicationController.index()).withNewSession
  }


  def authenticationFailed(request: RequestHeader)(implicit ctx: ExecutionContext) = Future.successful {
    Unauthorized(textToJson("Authentication failed"))
  }

  def authorizationFailed(request: RequestHeader)(implicit ctx: ExecutionContext) = Future.successful{
    Forbidden(textToJson("no permission"))
  }

  def authorize(auth: User, authority: Authority)(implicit ctx: ExecutionContext) = Future.successful{
    (auth.role, authority) match {
      case (Admin, _) => true
      case (SuperUser, SuperUser) => true
      case (SuperUser, NormalUser) => true
      case (NormalUser, NormalUser) => true
      case _ => false
    }
  }

  override lazy val cookieSecureOption: Boolean = false
}
