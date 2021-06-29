package week4
import scala.collection.mutable

object KDiffPairsInArray {
  def findPairs(nums: Array[Int], k: Int): Int = {
    val sortedNums = nums.sorted
    val hashMap = mutable.HashSet[(Int, Int)]();
    for (i <- 0 until sortedNums.length)
      for (j <- i until sortedNums.length) {
        if (i != j && sortedNums(j) - sortedNums(i) == k) {
          hashMap += sortedNums(j) -> sortedNums(i);
        }
      }
    hashMap.size
  }
}