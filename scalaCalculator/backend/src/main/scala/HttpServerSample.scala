import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import org.slf4j.{Logger, LoggerFactory}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import scala.util.{Failure, Success}

object HttpServerSample {

  def main(args: Array[String]): Unit = {
    implicit val log: Logger = LoggerFactory.getLogger(getClass)
    implicit val system = ActorSystem(Behaviors.empty, "my-system")
    implicit val executionContext = system.executionContext

    val route = {
      concat(
        path("pong") {
          get {
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>ping</h1>"))
          }
        },
        path("ping") {
          get {
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>pong</h1>"))
          }
        }

      )
    }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    bindingFuture.onComplete {
      case Success(_)=> log.info("hello");
      case Failure(exception) => log.error(exception.toString)
    }
  }
}