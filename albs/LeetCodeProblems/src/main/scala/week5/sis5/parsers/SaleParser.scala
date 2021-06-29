package week5.sis5.parsers

import week5.sis5.models.Sale

import scala.collection.mutable.ArrayBuffer
import scala.util.matching.Regex
object SaleParser extends RegexParser {
  private val salePattern: Regex = "(^[0-9]+\\.)".r


  def parseSales(input: String) = {
    var sales: ArrayBuffer[Sale] = new ArrayBuffer[Sale]()
    val splitedInput = input.split("\n")
    for (lineIndex <- 0 until splitedInput.length) {
      if (findFirstMatch(salePattern, splitedInput(lineIndex)) != "") {
//        TODO: NUTSHEELLL!?
        val amountString: String = splitedInput(lineIndex + 2).split("x")(0)
        val amount = amountString.slice(0, amountString.indexOf(",")).toInt

        val priceString: String = splitedInput(lineIndex + 2).split("x")(1).replaceAll("\\u00A0", "")
        val price = priceString.slice(0, priceString.indexOf(",")).toInt

        val totalPriceString = splitedInput(lineIndex + 5)
        val totalPriceWithNoBreakSpace = totalPriceString.slice(0, totalPriceString.indexOf(","))
        val totalPrice = totalPriceWithNoBreakSpace.replaceAll("\\u00A0", "").toInt

        sales += Sale(splitedInput(lineIndex+1), price, amount, totalPrice)
      }
    }
    sales.toList
  }
}
