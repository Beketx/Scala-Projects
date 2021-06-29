package week9.AddressBook

import akka.http.scaladsl.model.{StatusCode, StatusCodes}

final case class ApiError private(statusCode: StatusCode, message: String)

object ApiError {
  private def apply(statusCode: StatusCode, message: String): ApiError = new ApiError(statusCode, message)

  val generic: ApiError = new ApiError(StatusCodes.InternalServerError,
    "Unknown error.")

  val emptyLastNameField: ApiError = new ApiError(StatusCodes.BadRequest,
    "Last Name field can not be empty.")

  def contactNotFound(id: String): ApiError =
    new ApiError(StatusCodes.NotFound, s"Contact with id '$id' is not found.")
}