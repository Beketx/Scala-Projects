package week9.AddressBook

case class Contact(id: String,
                   firstName: String,
                   lastName: String,
                   address: String,
                   phone: String,
                   email: String)

case class CreateContact(firstName: String,
                         lastName: String,
                         address: String,
                         phone: String,
                         email: String)

case class UpdateContact(firstName: Option[String],
                         lastName: Option[String],
                         address: Option[String],
                         phone: Option[String],
                         email: Option[String])