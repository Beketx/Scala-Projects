object Problem7 {
  def kWeakestRows(mat: Array[Array[Int]], k: Int): Array[Int] = {

    val Weak = Array.ofDim[Int](mat.size)

    for (column <- 0 until mat(0).size; row <- 0 until mat.size) {
      Weak(row) += mat(row)(column)
    }

    Weak.zipWithIndex.sortBy(c => c._1).take(k).unzip._2
  }
}
