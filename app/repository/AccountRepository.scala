package repository

import dao.{AccountHashes, Accounts}
import helpers.{EmailSender, BCryptService}
import models.{UpdateUser, AccountHash, Account}
import org.joda.time.DateTime

object AccountRepository {

  /**
   * Create new user in DB
   * @param user: User to be created
   * @return
   */
  def saveUser(user: Account) = {
    val password: String = BCryptService.hashPassword(user.password)
    Accounts.saveUser(user.copy(password = password))
  }

  def createAccount(account: Account) = {
    val user = Accounts.saveUser(account.copy(password = "password"))
    val hash = AccountHashes.save(
      AccountHash(
        idHash = None,
        idUser = user.idUser.get,
        hash = BCryptService.hashEmail(user.email),
        date = new DateTime
      )
    )
    val serverUrl = play.Play.application.configuration.getString("server_url") + "/account/password/change/"
    EmailSender.sendCreateUserEmail(user.email, serverUrl + hash.hash)

    user
  }

  def findNormalUsers(idSpecial: Int): List[Account] = Accounts.findUserBySepcialization(idSpecial)

  def findUser(idUser: Int): Account = Accounts.findUserById(idUser).get

  def updateUser(user: UpdateUser) = {
    Accounts.updateUser(user)
  }
}
