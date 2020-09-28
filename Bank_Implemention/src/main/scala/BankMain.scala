object BankMain {
  def main(args: Array[String]): Unit ={
    val bank = new Bank
    var option = 0
    var customer: Option[Customer] = None
    var account: Option[Account] = None
    while(option != 10) {
      println(menu)
      option = readInt()
      option match {
        case 1 => customer = Some(createCustomer(bank))
        case 2 => customer = selectCustomer(bank)
        case 3 =>
        case 4 =>
        case 5 =>
        case 6 =>
        case 7 =>
        case 8 =>
        case 9 =>
        case 10 =>
        case _ => println("THis is not valid option. Please select again")

      }
    }
  }
  private def createCustomer(bank: Bank): Customer = {
    println("What is the firstname of customer")
    var fname = readLine()
    println("What is the lastname of customer")
    var lname = readLine()
    println("What is the address of customer")
    var address = readAddress()
    bank.addCustomer(fname, lname, address)

  }
  private def readAddress(): Address = ???
  private def selectCustomer(bank: Bank): Option[Customer] = {
    println("Do you want to find customer by name or id? (name/id)")
    val option = readLine()
    if(option == "name"){
      println("What is the firstname of customer")
      var firstname = readLine()
      println("What is the lastname of customer")
      var lastname = readLine()
      bank.findCustomer(firstname, lastname)
    } else {
      println("What is the customers id")
      val id = readLine()
      bank.findCustomer(id)
    }
  }
  private val menu =
    """
Select one of the following options:

      """

}
