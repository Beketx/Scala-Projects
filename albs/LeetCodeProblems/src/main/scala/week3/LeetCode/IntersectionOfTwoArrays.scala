package week3.LeetCode

class IntersectionOfTwoArrays extends App {
  def intersection(nums1: Array[Int], nums2: Array[Int]): Array[Int] = {
    nums1.filter(nums2.contains(_)).distinct
  }
}
