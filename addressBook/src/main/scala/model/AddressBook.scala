package model

case class AddressBook(id: String, name: String, address:String, phoneNumber:String)
case class CreateAddressBook(name: String, address:String, phoneNumber:String)
case class UpdateAddressBook(name: Option[String], address:Option[String], phoneNumber: Option[String])

