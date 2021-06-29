package week5.sis5.parsers

import scala.util.matching.Regex

object InvoiceParser extends RegexParser {
  private val filial_pattern: Regex = "Филиал(\\S+)".r
  private val bin_pattern: Regex = "БИН(\\S+)".r
  private val cashBox_pattern: Regex = "Касса(\\S+)".r
  private val serialNumberID_pattern: Regex = "Порядковый номер чека(\\S+)".r
  private val invoiceID_pattern: Regex = "Чек №(\\S+)".r



  def parseFilial(input: String): String = {
    findFirstMatch(filial_pattern, input)
  }

  def parseBin(input: String): String = {
    findFirstMatch(bin_pattern, input)
  }

  def parseCashBox(input: String): String = {
    findFirstMatch(cashBox_pattern, input)
  }

  def parseSerialNumberID(input: String): String = {
    findFirstMatch(serialNumberID_pattern, input)
  }

  def parseInvoiceID(input: String): String = {
    findFirstMatch(invoiceID_pattern, input)
  }
}
