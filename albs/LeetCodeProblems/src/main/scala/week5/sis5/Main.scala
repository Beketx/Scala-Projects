package week5.sis5


import week5.sis5.models.Invoice
import week5.sis5.parsers.{InvoiceParser, SaleParser}
import week5.sis5.utils.FileUtils

class Main extends App {

  val fileName = "raw.txt"
  val fileContent = FileUtils.readFile(fileName)
// TODO: Parse another fields
  def createInvoice(): Invoice = {
    val sales = SaleParser.parseSales(fileContent)
    Invoice(filial = InvoiceParser.parseFilial(fileContent),
      bin = 3222,
      cashBox = InvoiceParser.parseCashBox(fileContent),
      serialNumber = 228,
      id = 1337,
      cashier = "Alik",
      fiscalSign = "AO Kazakhtelecom",
      issueTime = "12.02.2020",
      issueLocation = "Tole bi 59",
      operator = "SteamEngine",
      sales = sales,
      totalAmount = sales.foldLeft(0)((x, item) => x + item.total))
  }
//  println(InvoiceParser.parseFilial(fileContent))
//  println(InvoiceParser.parseBin(fileContent))
//  println(InvoiceParser.parseCashBox(fileContent))
//  println(InvoiceParser.parseSerialNumberID(fileContent))
//  println(InvoiceParser.parseInvoiceID(fileContent))
//  println(SaleParser.parseSales(fileContent))
    println(createInvoice().toJson)
    FileUtils.writeFile("output.txt", createInvoice().toJson)
}

