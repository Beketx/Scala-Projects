object Problem3 {
  def smallerNumbersThanCurrent(nums: Array[Int]): Array[Int] = {
    nums.map(p => nums.count(q => q < p))
  }
}
