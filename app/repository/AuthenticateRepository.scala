package repository

import controllers.AuthController._
import dao.{AccountHashes, Accounts}
import helpers.BCryptService
import models.Account
import play.api.Logger
import play.api.libs.json.Json

import scala.concurrent.{Await, Future}

object AuthenticateRepository {

  /**
   * authenticate users
   * @param email email by who user will be identified
   * @param password user's password
   * @return user's id
   */
  def authenticateUser(email: String, password: String): Int = {
    Accounts.findUserIdByEmail(email) match {
      case Some(user: Account) =>
        if(BCryptService.checkPassword(password, user.password))
          user.idUser.get
        else
          throw new IllegalArgumentException("User or email incorrect")
      case None =>
        throw new IllegalArgumentException("No account with that email")
    }
  }

  /**
   * find user by his id
   * @param idUser user's id
   * @return user if the id is present in DB, or None if the is missing
   */
  def findUser(idUser: Int): Option[Account] = Accounts.findUserById(idUser)

  def changePassword(hash: String, password: String) = {
    //delete hash and get user's id
    AccountHashes.deleteHash(hash) match {
      case Some(idUser: Int) =>
        //update user password
        Accounts.updateUserPassword(idUser, BCryptService.hashPassword(password))
      case None =>
        throw new IllegalArgumentException("Verify Email link is invalid or has expired!")
    }
  }
}
