package week3.LeetCode

class TheKWeakestRowsInMatrix extends App {
  def kWeakestRows(mat: Array[Array[Int]], k: Int): Array[Int] = {
    mat.zipWithIndex.map(e => (e._1.sum, e._2)).sorted.unzip._2.take(k)
  }
}
