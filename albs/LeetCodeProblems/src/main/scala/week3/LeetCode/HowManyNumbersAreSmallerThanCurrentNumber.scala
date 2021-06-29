package week3.LeetCode

object HowManyNumbersAreSmallerThanCurrentNumber {
  def smallerNumbersThanCurrent(nums: Array[Int]): Array[Int] = {
    nums.map(x => {
      var cnt = 0
      for (y <- nums) {
        if (y < x)
          cnt += 1
      }
      cnt
    })
  }
}
