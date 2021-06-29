package week9.AddressBook

import java.util.UUID

import scala.concurrent.{Future, ExecutionContext}

trait AddressBookRepository {
  def all(): Future[Seq[Contact]]
  def retrieve(id: String): Future[Contact]
  def create(createContact: CreateContact): Future[Contact]
  def update(id: String, updateContact: UpdateContact): Future[Contact]
  def delete(id: String): Future[Contact]
}

class InMemoryAddressBookRepository(contact: Seq[Contact] = Seq.empty)
                                   (implicit ex: ExecutionContext) extends AddressBookRepository {
  private var contacts: Vector[Contact] = contact.toVector

  def get_contacts(): Vector[Contact] = contacts

  override def all(): Future[Seq[Contact]] = Future.successful{
    contacts
  }

  override def retrieve(id: String): Future[Contact] = Future.successful {
    contacts.find(_.id == id).get
  }

  override def create(createContact: CreateContact): Future[Contact] = Future.successful {
    val contact = Contact(
      id = UUID.randomUUID().toString,
      firstName = createContact.firstName,
      lastName = createContact.lastName,
      address = createContact.address,
      phone = createContact.phone,
      email = createContact.email
    )
    contacts = contacts :+ contact
    contact
  }

  override def update(id: String, updateContact: UpdateContact): Future[Contact] = Future.successful {
    val contact = contacts.find(_.id == id).get
    contacts = contacts.filter(_ != contact)
    var firstName = contact.firstName
    var lastName = contact.lastName
    var address = contact.address
    var phone = contact.phone
    var email = contact.email
    if (updateContact.firstName.isDefined)
      firstName = updateContact.firstName.get
    if (updateContact.lastName.isDefined)
      lastName = updateContact.lastName.get
    if (updateContact.address.isDefined)
      address = updateContact.address.get
    if (updateContact.phone.isDefined)
      phone = updateContact.phone.get
    if (updateContact.email.isDefined)
      email = updateContact.email.get
    val updated = Contact(id, firstName, lastName, address, phone, email)
    contacts = contacts :+ updated
    updated
  }

  override def delete(id: String): Future[Contact] = Future.successful {
    val contact = contacts.find(_.id == id).get
    contacts = contacts.filter(_ != contact)
    contact
  }
}