import controllers.AuthConfigImpl
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.libs.json.Json

import play.api.test._
import play.api.test.Helpers._
import jp.t2v.lab.play2.auth.test.Helpers._



/**
 * add your integration spec here.
 * An integration test will fire up a whole play application in a real (or headless) browser
 */
@RunWith(classOf[JUnitRunner])
class IntegrationSpec extends Specification {

  /*object config extends AuthConfigImpl

  "Application" should {
    "create a course" in new WithApplication {
      val course = Json.obj(
        "idSpecialization" -> 1,
        "idUser" -> 1,
        "name" -> "PCLP",
        "year" -> 1
      )

      val postRequest = FakeRequest(
        method = "POST",
        uri = "/api/superuser/course",
        headers = FakeHeaders(
          Seq("Content-type"->Seq("application/json"))
        ),
        body = course
      ).withLoggedIn(config)("1")

      val Some(result) = route(postRequest)
      status(result) must equalTo(FORBIDDEN)
      contentAsString(result) must contain("Course created")
    }

    "should get all courses" in new WithApplication {
      val companies = route(
        FakeRequest(GET, "/api/superuser/course").withLoggedIn(config)("1")
      ).get

      status(companies) must equalTo(OK)
      contentType(companies) must beSome.which(_ == "application/json")
      contentAsString(companies) must contain("PCLP")
    }

    "should craete a new project" in new WithApplication {
      val project = Json.obj(
        "idUser" -> 1,
        "idCourse" -> 1,
        "name" -> "Biblioteca",
        "description" -> "This project does and does and does",
        "isTaken" -> false,
        "isAccepted" -> false
      )

      val postRequest = FakeRequest(
        method = "POST",
        uri = "/api/superuser/project",
        headers = FakeHeaders(
          Seq("Content-type"->Seq("application/json"))
        ),
        body = project
      ).withLoggedIn(config)("1")

      val Some(result) = route(postRequest)
      status(result) must equalTo(FORBIDDEN)
      contentAsString(result) must contain("Project created")
    }
  }*/

}
