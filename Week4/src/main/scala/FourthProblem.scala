object FourthProblem {
  def findPairs(nums: Array[Int], num: Int): Int =
    if (num < 0)
      0
    else if (num == 0)
      nums.groupBy(identity).count(_._2.length > 1)
    else {
      val s = nums.toSet
      s.count(n => s.contains(n + num))
    }
}
