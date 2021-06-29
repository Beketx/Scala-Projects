package week5.sis5.models

case class Sale(itemName: String, itemPrice: Int, amount: Int, total: Int) {
  override def toString: String = s"$itemName, $itemPrice, $amount: $total"
}
