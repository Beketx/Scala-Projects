package week5.leetcode

import scala.collection.mutable.ArrayBuffer

object Tribonacci {
  def tribonacci(n: Int): Int = {
    val dp = ArrayBuffer.fill[Int](40)(0)
    dp(0) = 0
    dp(1) = 1
    dp(2) = 1
    for (i <- 3 to n)
      dp(i) = dp(i-1) + dp(i-2) + dp(i-3)
    dp(n)

  }
}
