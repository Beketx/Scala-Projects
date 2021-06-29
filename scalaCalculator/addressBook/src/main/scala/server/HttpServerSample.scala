package server

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import model.{AddressBook, Todo}
import org.slf4j.{Logger, LoggerFactory}
import route.MyRouter
import repo.{InMemoryAddressBookRepository, InMemoryTodoRepository}

import scala.util.Try

object HttpServerSample {

  def main(args: Array[String]): Unit = {

    implicit val log: Logger = LoggerFactory.getLogger(getClass)

    val rootBehavior = Behaviors.setup[Nothing] { context =>

      val books: Seq[AddressBook] = Seq(
        AddressBook("1", "someName1", "address1", "87775557788"),
        AddressBook("2", "someName2", "address2", "87775557789"),
      )

      val addressBookRepository = new InMemoryAddressBookRepository(books)(context.executionContext)
      val router = new MyRouter(addressBookRepository)(context.system, context.executionContext)

      val host = "0.0.0.0"
      val port = Try(System.getenv("PORT")).map(_.toInt).getOrElse(9000)

      Server.startHttpServer(router.route, host, port)(context.system, context.executionContext)
      Behaviors.empty
    }
    val system = ActorSystem[Nothing](rootBehavior, "HelloAkkaHttpServer")
  }
}
