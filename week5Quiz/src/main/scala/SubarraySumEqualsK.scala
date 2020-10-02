object SubarraySumEqualsK {
  def subarraySum(nums: Array[Int], k: Int): Int = {

    var totals = 0
    for(i <- nums.indices) {
      var sum: Int = 0
      for(j <- i until nums.length) {
        sum = sum + nums(j)
        if(sum == k) {
          totals += 1
        }
      }
    }
    totals

  }
}
