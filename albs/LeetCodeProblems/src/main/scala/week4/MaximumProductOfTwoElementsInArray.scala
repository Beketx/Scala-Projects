package week4

object MaximumProductOfTwoElementsInArray {
  def maxProduct(nums: Array[Int]): Int = {
    nums.sorted.slice(nums.length - 2, nums.length).reduce((a, b) => (a - 1) * (b - 1))
  }
}
