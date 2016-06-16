package dao

import helpers.{DateTimeMapper, PostgresSupport}
import models.AccountHash
import org.joda.time.DateTime
import play.api.db.slick.Config.driver.simple._

object AccountHashes extends PostgresSupport with DateTimeMapper {

  lazy val hashes = TableQuery[AccountHashTable]

  class AccountHashTable(tag: Tag) extends Table[AccountHash](tag, "account_hash") {
    def idHash = column[Int]("id_hash", O.PrimaryKey, O.AutoInc)
    def idUser = column[Int]("id_user", O.NotNull)
    def hash = column[String]("hash", O.NotNull)
    def date = column[DateTime]("date", O.NotNull)

    def * = (idHash.?, idUser, hash, date) <> (AccountHash.tupled, AccountHash.unapply _)
  }

  /**
   * insert a new hash in db
   * @param hash: AuthAccountHash
   * @return saved AuthAccountHash
   */
  def save(hash: AccountHash): AccountHash = {
    hash.idHash match {
      case None => {
        val id = (hashes returning hashes.map(_.idHash)) += hash
        hash.copy(idHash = Some(id))
      }
      case Some(id) => {
        val query = for {
          p <- hashes if p.idHash === id
        } yield p
        query.update(hash)
        hash
      }
    }
  }

  def findById(idHash: Int): AccountHash = {
    val query = for {
      h <- hashes if h.idHash === idHash
    } yield h

    query.list.head
  }

  def findByHash(hash: String) = {
    val query = for {
      h <- hashes if h.hash === hash
    } yield h.idUser

    query.list.head
  }


  /**
   * delete user hash and return it's user id
   * @param hash
   * @return user's hash id
   */
  def deleteHash(hash: String): Option[Int] = {
    val query = for {
      h <- hashes if h.hash === hash
    } yield h

    val result = query.list.headOption
    query.delete

    result match {
      case Some(h: AccountHash) =>
        Some(h.idUser)
      case None =>
        None
    }
  }

  /**
   * delete all entries of a user
   * @param idUser
   * @return
   */
  def delete(idUser: Int): Int = {
    val query = for {
      uh <- hashes if uh.idUser === idUser
    } yield uh

    query.delete
  }

  /**
   * delete all entries of a user
   * @param userIds
   * @return
   */
  def deleteMass(userIds: List[Int])(implicit session: Session): Int = {
    val query = for {
      uh <- hashes if uh.idUser inSetBind userIds
    } yield uh

    query.delete
  }
}