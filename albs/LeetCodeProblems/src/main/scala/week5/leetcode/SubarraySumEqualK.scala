package week5.leetcode

import scala.collection.mutable.ArrayBuffer

object SubarraySumEqualK {
  def subarraySum(nums: Array[Int], k: Int): Int = {
    var cnt: Int = 0
    val prefixSum = ArrayBuffer.fill[Int](nums.length + 1)(0)
    prefixSum(0) = 0
    for (i <- 1 to nums.length)
      prefixSum(i) = prefixSum(i-1) + nums(i-1)

    for (i <- 0 until nums.length) {
      for (j <- i + 1 to nums.length) {
        if (prefixSum(j) - prefixSum(i) == k)
          cnt += 1
      }
    }
    cnt
  }
}
