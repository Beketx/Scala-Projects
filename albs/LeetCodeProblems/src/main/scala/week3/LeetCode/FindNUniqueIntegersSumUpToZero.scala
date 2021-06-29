package week3.LeetCode

import scala.collection.mutable.ArrayBuffer

class FindNUniqueIntegersSumUpToZero {
  def sumZero(n: Int): Array[Int] = {
    val total = ((n - 1) * n) / 2
    val ans = ArrayBuffer.fill[Int](n)(0)
    ans(0) = -total
    for (i <- 1 until ans.length)
      ans(i) = i
    ans.toArray
  }
}
