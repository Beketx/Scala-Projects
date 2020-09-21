object Problem10 {
  def buildArray(target: Array[Int], n: Int): List[String] = {
    def go(l: List[Int], currInd: Int): List[String] = l match {
      case Nil => Nil
      case h :: tail if h == currInd => "Push" :: go(tail, currInd + 1)
      case other => "Push" :: "Pop" :: go(other, currInd + 1)
    }

    go(target.toList, 1)
  }
}
