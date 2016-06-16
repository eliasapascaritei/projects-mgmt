import contract.{ProjectUser, CourseProject}
import play.api.libs.json.Json


package object models {
  implicit val accountJson = Json.format[Account]
  implicit val courseJson = Json.format[Course]
  implicit val specialJson = Json.format[Specialization]
  implicit val projectJson = Json.format[Project]
  implicit val projectUserJson = Json.format[ProjectUser]
  implicit val courseProjectJson = Json.format[CourseProject]
  implicit val userProjectJson = Json.format[UserProject]
  implicit val createAccountJson = Json.format[CreateAccount]
  implicit val contactJson = Json.format[Contact]
  implicit val updateUserJson = Json.format[UpdateUser]

}