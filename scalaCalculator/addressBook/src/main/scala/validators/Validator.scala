package validators

import model.{CreateAddressBook, CreateTodo}

trait Validator[T] {
  def validate(t: T): Option[ApiError]
}

object CreateABookValidator extends Validator[CreateAddressBook] {
  def validate(createAddressBook: CreateAddressBook): Option[ApiError] =
    if (createAddressBook.name.isEmpty)
      Some(ApiError.emptyNameField)
    else {
      if(isOrdinary(createAddressBook.phoneNumber)){
        Some(ApiError.invalidPhoneNumber)
      }else
      None
    }

  val ordinary=(('a' to 'z') ++ ('A' to 'Z')).toSet
  def isOrdinary(s: String)=s.forall(ordinary.contains(_))
}

