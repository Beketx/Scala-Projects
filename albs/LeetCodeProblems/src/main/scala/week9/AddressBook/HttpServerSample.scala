package week9.AddressBook

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors

import org.slf4j.{Logger, LoggerFactory}

import scala.util.Try

object HttpServerSample {

  def main(args: Array[String]): Unit = {

    implicit val log: Logger = LoggerFactory.getLogger(getClass)

    val rootBehavior = Behaviors.setup[Nothing] { context =>

      val contacts: Seq[Contact] = Seq(
        Contact("c84bb01e-9f74-46fe-b00f-703e119ecd6d", "Alik", "Akhmetov",
          "Tole bi", "+77775935555", "a_akhmetov@kbtu.kz"),
        Contact("ba967d86-ef54-4766-aed1-adb151d263c0", "FirstName", "LastName",
          "Custom Address", "Custom phone", "someemail@gmail.com")
      )

      val addressBookRepository = new InMemoryAddressBookRepository(contacts)(context.executionContext)
      val router = new MyRouter(addressBookRepository)(context.system, context.executionContext)

      val host = "0.0.0.0"
      val port = Try(System.getenv("PORT")).map(_.toInt).getOrElse(9000)

      Server.startHttpServer(router.route, host, port)(context.system, context.executionContext)
      Behaviors.empty
    }
    val system = ActorSystem[Nothing](rootBehavior, "AddressBookHttpServer")
  }
}