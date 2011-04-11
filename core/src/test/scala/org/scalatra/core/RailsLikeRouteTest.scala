package org.scalatra
package core

import org.scalatest.matchers.ShouldMatchers
import test.scalatest.ScalatraFunSuite

class RailsLikeRouteTestServlet extends servlet.ScalatraServlet {
  implicit override def string2RouteMatcher(path: String) =
    RailsPathPatternParser(path)

  // This syntax wouldn't work in Sinatra
  get("/:file(.:ext)") {
    List("file", "ext") foreach { param =>
      response.addHeader(param, params.getOrElse(param, ""))
    }
  }
}

class RailsLikeRouteTest extends ScalatraFunSuite with ShouldMatchers {
  addServlet(classOf[RailsLikeRouteTestServlet], "/*")

  test("matches without extension") {
    get("/foo") {
      header("file") should equal ("foo")
      header("ext") should equal ("")
    }
  }

  test("matches with extension") {
    get("/foo.xml") {
      header("file") should equal ("foo")
      header("ext") should equal ("xml")
    }
  }
}
