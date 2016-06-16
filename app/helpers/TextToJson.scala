package helpers

import play.api.libs.json.Json._
import play.api.libs.json._

case class TextsToJson (
  message: String
)

trait TextToJson {
  implicit val messageJson = Json.format[TextsToJson]

  def textToJson(message: String ) = {
    toJson(new TextsToJson(message))
  }
}
