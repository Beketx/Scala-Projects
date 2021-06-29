package week5.sis5.parsers

import scala.util.matching.Regex

trait RegexParser {
  def findFirstMatch(regex: Regex, input: String): String = {
//    FIXME: why trim does not work? why i have to set "\\u00A0"
    regex.findFirstMatchIn(input).map(_.group(1)) match {
      case None => ""
      case string: Option[String] => string.getOrElse("").trim()
    }
  }
}
