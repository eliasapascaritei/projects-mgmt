package helpers

import org.joda.time.DateTime
import org.mindrot.jbcrypt.BCrypt
import play.api.libs.Codecs._

object BCryptService {

  def hashPassword(pass: String): String = {
    BCrypt.hashpw(pass, BCrypt.gensalt())
  }

  def hashPassword(pass: String, getSaltRound: Int): String = {
    BCrypt.hashpw(pass, BCrypt.gensalt(getSaltRound))
  }

  def checkPassword(pass: String, hash: String): Boolean = {
    BCrypt.checkpw(pass, hash)
  }

  def hashString(email: String): String = {
    val hash = email + DateTime.now()
    md5(hash.getBytes)
  }

  def hashEmail(email: String): String = {
    md5(email.getBytes)
  }
}
