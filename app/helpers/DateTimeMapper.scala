package helpers

import java.sql.Timestamp

import org.joda.time.DateTime

//import scala.slick.driver.PostgresDriver.simple._
import play.api.db.slick.Config.driver.simple._

trait DateTimeMapper {

  implicit def dateTime = {
    MappedColumnType.base[DateTime, Timestamp] (
      dt => new Timestamp(dt.getMillis),
      ts => new DateTime(ts.getTime)
    )
  }

}
