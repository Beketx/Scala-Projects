import java.io.{File, PrintWriter}
import io.circe.generic.auto._
import io.circe.syntax._

object Main extends App {

  def parsefunc(): Unit = {

    import scala.io.Source

    val lines = Source.fromFile("/Users/beket/Desktop/raw.txt").getLines.toList.drop(12).dropRight(17)

    case class Product(title: String, totalPrice: String, cnt: String, price: String)
    case class Check(products: Array[Product])

    var raw = 1
    var title: String = ""
    var cnt: String = ""
    var price: String = ""
    var totalPrice: String = ""

    var check = Array[Product]()

    lines.foreach(str => {
      if (str.trim.matches("[0-9]+[.]")) {
        raw = 0
      }

      if (raw == 0) {
        val product = Product(title, totalPrice, cnt, price)
        check = check :+ product
      } else if (raw == 1) {
        title = str;
      } else if (raw == 2) {
        cnt = str.substring(0, str.indexOf("x"))
        price = str.substring(str.indexOf("x") + 1)
      } else if (raw == 3) {
        totalPrice = str
      }

      println(raw + " >> " + str)

      raw = raw + 1;
    })

    val json = check.asJson.noSpaces
    println(json)

    val pw = new PrintWriter(new File("/Users/beket/Desktop/raw.txt"))
    pw.write(json)
    pw.close
  }

  parsefunc()

}
