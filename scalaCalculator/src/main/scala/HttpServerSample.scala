import java.io.File

import HttpServerSample.getClass
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.Multipart.BodyPart
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.scaladsl.FileIO
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}


object  MyServer{
   def startHttpServer(routes: Route)(implicit system: ActorSystem[_],  ex:ExecutionContext): Unit = {
    // Akka HTTP still needs a classic ActorSystem to start
     val futureBinding = Http().newServerAt("localhost", 8080).bind(routes)
    futureBinding.onComplete {
      case Success(binding) =>
        val address = binding.localAddress
        system.log.info("Server online at http://{}:{}/", address.getHostString, address.getPort)
      case Failure(ex) =>
        system.log.error("Failed to bind HTTP endpoint, terminating system", ex)
        system.terminate()
    }
  }
}

trait  Router {
  def route:Route
}
class MyRouter(val todoRepository: TodoRepository)(implicit system: ActorSystem[_],  ex:ExecutionContext) extends  Router with  Directives{

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  implicit val logger: Logger = LoggerFactory.getLogger(getClass)

  case class Video(file: File, title: String, author: String)
  object db {
    def create(video: Video): Future[Unit] = Future.successful(())
  }

  var listOfFiles = List[(String,String)]()
  def getUploadDate(path: String): String ={
    import java.nio.file._;
    val uploadedDate
    = Files.getLastModifiedTime(Paths.get(path))
    uploadedDate.toString
  }
  val uploadVideo = {
    concat(
      path("index") {
        complete((new HtmlToResponse).response)
      },
      path("get") {
        complete((new HtmlToResponse("files.html", listOfFiles)).response)
      },
    path("video") {
      entity(as[Multipart.FormData]) { formData =>
        // collect all parts of the multipart as it arrives into a map
        val allPartsF: Future[Map[String, Any]] = formData.parts.mapAsync[(String, Any)](1) {

          case b: BodyPart if b.name == "file" =>
            // stream into a file as the chunks of it arrives and return a future
            // file to where it got stored
            val file = File.createTempFile("upload", "tmp")
            logger.info("name " + b.name + " - " + file.getName)

            b.entity.dataBytes.runWith(FileIO.toPath(file.toPath)).map(_ =>
              (b.name -> file))
          case b: BodyPart =>
            // collect form field values
            b.toStrict(2.seconds).map(strict =>
              (b.name -> strict.entity.data.utf8String)
            )

        }.runFold(Map.empty[String, Any])((map, tuple) => map + tuple)

        val done = allPartsF.map { allParts =>
          // You would have some better validation/unmarshalling here
          db.create(Video(
            file = allParts("file").asInstanceOf[File],
            title = allParts("title").asInstanceOf[String],
            author = allParts("author").asInstanceOf[String]))
          logger.info("filename " + allParts("file") + listOfFiles.size)
        }

        // when processing have finished create a response for the user
        onSuccess(allPartsF) { allParts =>
          complete {
            val path = allParts("file").toString()
            listOfFiles::=(path,getUploadDate(path))
            "UPLOADED!"
          }
        }
      }
    }    )

  }


  override def route = {
    concat(
      path("ping") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "pong2"))
        }
      },
      pathPrefix("todos") {
        pathEndOrSingleSlash {
          get {
            complete(todoRepository.all())
            //complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
          }
        }
      },
      path("hello") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
        }
      })
  }
}


object HttpServerSample {

  def main(args: Array[String]): Unit = {

    implicit val log: Logger = LoggerFactory.getLogger(getClass)

    val rootBehavior = Behaviors.setup[Nothing] { context =>

      val todos:Seq[Todo] = Seq(
        Todo("1","title1","description1",true),
        Todo("2","title2","description2",false)
      )

      val todoRepository = new InMemoryTodoRepository(todos)(context.executionContext)
      val router = new MyRouter(todoRepository)(context.system, context.executionContext)

      MyServer.startHttpServer(router.uploadVideo)(context.system, context.executionContext)
      Behaviors.empty
    }
    val system = ActorSystem[Nothing](rootBehavior, "HelloAkkaHttpServer")
  }

//   def main(args: Array[String]): Unit = {
//     var listOfFiles = List[(String,String)]()
//     listOfFiles::=("test", "a")
//     listOfFiles::=("test2", "b")
//
//     for (name <- listOfFiles){
//       println(name._1 + " "+name._2)
//     }
//
//   }

}
