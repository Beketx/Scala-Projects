package week9.AddressBook

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.server.{Directives, Route}

import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import  io.circe.generic.auto._

import scala.concurrent.ExecutionContext

trait Router {
  def route: Route
}

class MyRouter(val addressBookRepository: InMemoryAddressBookRepository)
              (implicit system: ActorSystem[_],  ex:ExecutionContext) extends Router
  with Directives
  with ContactDirectives
  with ValidatorDirectives
  with HealthCheckRoute {

  def addressBook: Route = {
    pathPrefix("contacts") {
      concat(
        pathEndOrSingleSlash {
          concat(
            get {
              complete(addressBookRepository.all())
            },
            post {
              entity(as[CreateContact]) { createContact =>
                handleWithGeneric(addressBookRepository.create(createContact)) { contact =>
                  complete(contact)
                }
              }
            }
          )
        },
        path(("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}" +
          "-[a-f0-9]{4}-[a-f0-9]{12}").r) { id =>
          concat(
            get {
              validateExistenceWith(ContactExistenceValidator, addressBookRepository)(id) {
                handleWithGeneric(addressBookRepository.retrieve(id)) { result =>
                  complete(result)
                }
              }
            },
            patch {
              entity(as[UpdateContact]) { updateContact =>
                  handleWithGeneric(addressBookRepository.update(id, updateContact)) { contact =>
                    complete(contact)
                  }
              }
            },
            delete {
              handleWithGeneric(addressBookRepository.delete(id)) { result =>
                complete(result)
              }
            }
          )
        }
      )
    }
  }

  override def route: Route = {
    concat(
      healthCheck,
      addressBook
    )
  }
}