package directives

import akka.actor.FSM.Failure
import akka.http.scaladsl.server.{Directive1, Directives}
import validators.ApiError

import scala.concurrent.Future
import scala.util.Success

trait TodoDirectives extends Directives {

  def handle[T](f: Future[T])(e: Throwable => ApiError): Directive1[T] = onComplete(f) flatMap {
    case Success(t) =>
      provide(t)
//    case Failure(exception) =>
//      println()
//      complete(ApiError.invalidPhoneNumber)
  }

  def handleWithGeneric[T](f: Future[T]): Directive1[T] =
    handle[T](f)(
//      case Exception("abc") => ApiError.invalidPhoneNumber
//      case exception => ApiError.invalidPhoneNumber
      _ => ApiError.generic

    )

}
