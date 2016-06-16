package helpers
import play.api.Play.current
import slick.driver.PostgresDriver.simple._
import play.api.db.slick.Config.driver.simple._
//import com.typesafe.config.ConfigFactory

trait PostgresSupport {
  implicit lazy val session: Session = play.api.db.slick.DB("default").createSession()
}
