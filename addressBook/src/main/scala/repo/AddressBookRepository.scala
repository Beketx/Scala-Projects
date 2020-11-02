package repo

import java.util.UUID

import model.{AddressBook, CreateAddressBook, UpdateAddressBook}

import scala.concurrent.{ExecutionContext, Future}
trait AddressBookRepository {
  def all(): Future[Seq[AddressBook]]

  def create(createAddressBook:CreateAddressBook): Future[AddressBook]

  def createManually(addressBook: AddressBook): Future[Option[AddressBook]]

  def get(id: String): Future[Option[AddressBook]]

  def update(id: String, update: UpdateAddressBook): Future[Option[AddressBook]]

  def delete(id: String): Future[Unit]

}

class InMemoryAddressBookRepository(book:Seq[AddressBook] = Seq.empty)(implicit  ex:ExecutionContext) extends AddressBookRepository {
  private var books: Vector[AddressBook] = book.toVector

  override def all(): Future[Seq[AddressBook]] = Future.successful(books)

  override def create(createAddressBook: CreateAddressBook): Future[AddressBook] = {
//    if(createAddressBook.phoneNumber.contains("a")){
//      Future.failed(
//        new Exception("abc")
//      )
//    }else
    Future.successful {
    val aBook = AddressBook(
      id = UUID.randomUUID().toString,
      name = createAddressBook.name,
      address = createAddressBook.address,
      phoneNumber = createAddressBook.phoneNumber
    )
    books = books :+ aBook
    aBook
  }
  }

  override def createManually(createAddressBook: AddressBook): Future[Option[AddressBook]] = Future.successful {
    val aBook = AddressBook(
      id = createAddressBook.id,
      name = createAddressBook.name,
      address = createAddressBook.address,
      phoneNumber = createAddressBook.phoneNumber
    )
    books = books :+ aBook
    Some(aBook)
  }

  override def get(id: String): Future[Option[AddressBook]] = Future{
    books.find(_.id == id)
  }

  override def update(id: String, update: UpdateAddressBook): Future[Option[AddressBook]] = {
    def updateEntity(bookSelected: AddressBook): AddressBook = {
      val name = update.name.getOrElse(bookSelected.name)
      val address = update.address.getOrElse(bookSelected.address)
      val phoneNumber = update.phoneNumber.getOrElse(bookSelected.phoneNumber)
      AddressBook(id, name, address, phoneNumber)
    }
    get(id).flatMap { abook =>
      abook match {
        case None => Future { None }
        case Some(book) =>
          val updatedAddressBook = updateEntity(book)
          delete(id).flatMap { _ =>
            createManually(updatedAddressBook).map(_ => Some(updatedAddressBook))
          }
      }
    }
  }

  override def delete(id: String): Future[Unit] = Future{
    books = books.filterNot(_.id == id)
  }
}
