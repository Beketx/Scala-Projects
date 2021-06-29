package week9.AddressBook

trait Validator[T] {
  def validate(t: T): Option[ApiError]
}

trait ExistenceValidator[T] {
  def validate(t: T, addressBookRepository: InMemoryAddressBookRepository): Option[ApiError]
}

object CreateContactValidator extends Validator[CreateContact] {
  def validate(createContact: CreateContact): Option[ApiError] =
    if (createContact.lastName.isEmpty)
      Some(ApiError.emptyLastNameField)
    else
      None
}

object ContactExistenceValidator extends ExistenceValidator[String] {
  def validate(id: String, addressBookRepository: InMemoryAddressBookRepository): Option[ApiError] = {
    val contact = addressBookRepository.get_contacts().find(_.id == id)
    if (contact.isEmpty)
      Some(ApiError.contactNotFound(id))
    else
      None
  }
}