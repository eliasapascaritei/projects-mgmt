package dao

import helpers.{DateTimeMapper, PostgresSupport}
import models.{UpdateUser, Account}
import org.joda.time.DateTime
import security.{NormalUser, Role}
import play.api.db.slick.Config.driver.simple._

object Accounts extends PostgresSupport with DateTimeMapper {

  implicit val permissionTypeMapper = MappedColumnType.base[Role, String] (
    p => Role.stringValueOf(p),
    s => Role.valueOf(s)
  )

  lazy val users = TableQuery[AccountTable]

  class AccountTable(tag: Tag) extends Table[Account](tag, "users") {
    def idUser = column[Int]("id_user", O.PrimaryKey, O.AutoInc)
    def idSpecialization = column[Int]("id_special", O.Nullable)
    def email = column[String]("email", O.NotNull)
    def lastName = column[String]("lastName", O.Nullable)
    def firstName = column[String]("firstName", O.Nullable)
    def role = column[Role]("role", O.NotNull)
    def password = column[String]("password", O.NotNull)

    def * = (idUser.?, idSpecialization.?, email, lastName, firstName, role,
      password) <> ((Account.apply _).tupled, Account.unapply)

    def emailUnique = index("profile_email_unique", email, unique = true)

    def ? = (idUser.?, idSpecialization.?, email.?, lastName.?, firstName.?, role.?, password.?)
  }

  def isTableEmpty = users.list.isEmpty

  def allUsers = users.list

  def saveUser(user: Account): Account = {
    user.idUser match {
      case None => {
        val id = (users returning users.map(_.idUser)) += user
        user.copy(idUser = Some(id))
      }
      case Some(id) => {
        val query = for {
          u <- users if u.idUser === id
        } yield u
        query.update(user)
        user
      }
    }
  }

  def findUserById(idUser: Int): Option[Account] = {
    val query = for(
      u <- users if u.idUser === idUser
    ) yield u

    query.list.headOption
  }

  def findUserById(ids: List[Int]): List[Account] = {
    val query = for(
      u <- users if u.idUser inSetBind ids
    ) yield u

    query.list
  }

  def findUserByEmail(email: String): Option[Account] = {
    val query = for {
      u <- users if u.email === email
    } yield u

    query.list.headOption
  }

  def deleteUser(idUser: Int) = {
    val query = for(
      u <- users if u.idUser === idUser
    ) yield u

    query.delete
  }

  def deleteUsers(ids: List[Int]) = {
    users.filter(_.idUser inSetBind ids).delete
  }

  def findUserIdByEmail(email: String): Option[Account] = {
    val query = for {
      u <- users if u.email === email
    } yield u

    query.list.headOption
  }

  def findUserBySepcialization(idSpecial: Int) = {
    val query = for {
      u <- users if u.idSpecialization === idSpecial && u.role === NormalUser.asInstanceOf[Role]
    } yield u

    query.list
  }

  def updateUserPassword(idUser: Int, password: String) = {
    val query = for {
      u <- users if u.idUser === idUser
    } yield u.password

    query.update(password)
  }

  def updateUser(user: UpdateUser) = {
    val query = for {
      u <- users if u.idUser === user.idUser
    } yield (u.firstName, u.lastName)

    query.update(user.firstName, user.lastName)
  }
}
