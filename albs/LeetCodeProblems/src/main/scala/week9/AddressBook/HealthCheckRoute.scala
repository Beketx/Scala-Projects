package week9.AddressBook

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Directives, Route}

trait HealthCheckRoute {
  def healthCheck: Route = {
    Directives.concat(
      path("ping") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "pong"))
        }
      }
    )
  }
}