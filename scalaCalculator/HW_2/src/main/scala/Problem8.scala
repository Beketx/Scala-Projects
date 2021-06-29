object Problem8 {

  def findSolution(customfunction: Nothing, z: Int): Nothing = {
    val res: List[Int] = List[Int]()
    var x = 1
    var y = 1000
    while ( {
      x <= 1000 && y > 0
    }) {
      val v = customfunction(x, y)
      if (v > z)  y -= 1
      else if (v < z) x += 1
      else res.tolist({
        x += 1; x - 1
      }, {
        y -= 1; y + 1
      })
    }
    res
  }
}
