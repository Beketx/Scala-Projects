package week5.sis5.models
import io.circe.generic.auto._
import io.circe.syntax._

case class Invoice(filial: String,
              bin: BigInt,
              cashBox: String,
              serialNumber: Int,
              id: BigInt,
              cashier: String,
              fiscalSign: String,
              issueTime: String,
              issueLocation: String,
              operator: String,
              sales: List[Sale],
              totalAmount: BigInt) {


  override def toString: String = s"$id: $calculateTotalAmount"

  private def calculateTotalAmount: BigInt = {
    sales.foldLeft(0)((x, item) => x + item.total)
  }

  def toJson: String = {
    this.asJson.noSpaces
  }
}
