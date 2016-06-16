package security

import play.api.libs.json._

sealed trait Role
case object Admin extends Role
case object SuperUser extends Role
case object NormalUser extends Role

object Role {

  implicit val roleFormat: Format[Role] = new Format[Role] {
    override def reads(j: JsValue) = j match {
      case JsString(s) => JsSuccess(valueOf(s))
      case _ => JsError("error.expected.jsString")
    }

    override def writes(o: Role): JsValue = {
      Json.toJson(stringValueOf(o))
    }
  }

  def valueOf(value: String): Role = value match {
    case "admin" => Admin
    case "superuser" => SuperUser
    case "normaluser" => NormalUser
    case _ =>  throw new IllegalArgumentException()
  }

  def stringValueOf(value: Role): String = value match {
    case Admin => "admin"
    case SuperUser => "superuser"
    case NormalUser => "normaluser"
    case _ => throw new IllegalArgumentException()
  }

}