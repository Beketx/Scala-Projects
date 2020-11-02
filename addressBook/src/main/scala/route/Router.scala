package route

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.server.{Directives, Route}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import directives.ValidatorDirectives
import io.circe.generic.auto._
import model.{CreateAddressBook, CreateTodo, UpdateAddressBook, UpdateTodo}
import validators.{CreateABookValidator}
import repo.{AddressBookRepository, TodoRepository}
import directives.TodoDirectives

import scala.concurrent.ExecutionContext

trait Router {
  def route: Route
}

class MyRouter(val addressBookRepository: AddressBookRepository)(implicit system: ActorSystem[_], ex:ExecutionContext)
  extends  Router
    with  Directives
    with HealthCheckRoute
    with ValidatorDirectives
    with TodoDirectives {
  def addressBook = {
    pathPrefix("books") {
      concat(
        pathEndOrSingleSlash {
          concat(
            get {
              complete(addressBookRepository.all())
            },
            post {
              entity(as[CreateAddressBook]) { createBook=>
                validateWith(CreateABookValidator)(createBook) {
                  handleWithGeneric(addressBookRepository.create(createBook)) { book =>
                    complete(book)
                  }
                }
              }
            }
          )
        },
        path(Segment) { id =>
          concat(
            get {
              complete(addressBookRepository.get(id))
            },
            put {
              entity(as[UpdateAddressBook]) { update =>
                complete(addressBookRepository.update(id, update))
              }
            },
            delete {
              complete(addressBookRepository.delete(id))
            }
          )
        }
      )
    }
  }

  override def route = {
    concat(
      healthCheck,
      addressBook
    )
  }

//  def todo = {
//    pathPrefix("todos") {
//      concat(
//        pathEndOrSingleSlash {
//          concat(
//            get {
//              complete(todoRepository.all())
//            },
//            post {
//              entity(as[CreateTodo]) { createTodo =>
//                validateWith(CreateTodoValidator)(createTodo) {
//                  handleWithGeneric(todoRepository.create(createTodo)) { todo =>
//                    complete(todo)
//                  }
//                }
//              }
//            }
//          )
//        },
//
//        path("done") {
//          get {
//            complete(todoRepository.done())
//          }
//        },
//        path("pending") {
//          get {
//            complete(todoRepository.pending())
//          }
//        },
//        path(Segment) { id =>
//          concat(
//          get {
//            complete(todoRepository.get(id))
//          },
//            put {
//              entity(as[UpdateTodo]) { update =>
//                complete(todoRepository.update(id, update))
//              }
//            },
//              delete {
//                complete(todoRepository.delete(id))
//              }
//          )
//        }
//      )
//    }
//  }


}




