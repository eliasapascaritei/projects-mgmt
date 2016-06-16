import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  "Application" should {

    "send 404 on a bad request" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beNone
    }

    "crate new user" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beNone
    }
    "create new course" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beNone
    }
    "create new project" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beNone
    }
    "get a list of users" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beNone
    }
    "get project by ID" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beNone
    }
    "get all student courses" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beNone
    }
    "get all professor courses" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beNone
    }
    "send rproject request to professor" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beNone
    }
    "accept student request" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beNone
    }
    "send user email when he get work assigned" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beNone
    }
    "send email to all students in a course" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must equalTo(OK)
    }
    "update profile" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beNone
    }
    "delete coures" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beNone
    }
    "delete student" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beNone
    }
    "delete project" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beNone
    }
    "reject project" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beNone
    }

  }
}
