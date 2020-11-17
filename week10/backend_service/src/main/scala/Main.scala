import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorSystem, Behavior}
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.codec.DerivedAsObjectCodec.deriveCodec
import server.Server

import scala.concurrent.ExecutionContext
import scala.util.Try

object Main {
  def main(args: Array[String]): Unit = {
    val rootBehavior: Behavior[Nothing] = Behaviors.setup[Nothing] {context =>
      implicit val system: ActorSystem[_] = context.system
      implicit val ec: ExecutionContext = context.executionContext

      val routes = {
        path("api") {
          post {
            entity(as[Vector[String]]) { list =>
              list.foreach(system.log.info(_))
              complete(list.head)
            }
          }
        }
      }
      val host = "0.0.0.0"
      val port = Try(System.getenv("PORT")).map(_.toInt).getOrElse(9000)
      Server.startHttpServer(routes, host, port)
      Behaviors.empty
    }
    val system = ActorSystem[Nothing](rootBehavior, "mainSystem")
  }
}
