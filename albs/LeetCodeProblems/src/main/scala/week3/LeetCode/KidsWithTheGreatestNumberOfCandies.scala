package week3.LeetCode

object KidsWithTheGreatestNumberOfCandies {
  def kidsWithCandies(candies: Array[Int], extraCandies: Int): Array[Boolean] = {
    val maxCandy = candies.max
    for (candy <- candies) yield candy + extraCandies >= maxCandy
  }
}
