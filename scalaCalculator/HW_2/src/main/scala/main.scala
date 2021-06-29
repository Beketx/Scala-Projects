object main extends App {
  def kidsWithCandies(candies: Array[Int], extraCandies: Int): Array[Boolean] = {
    val res = Array.ofDim[Boolean](candies.length)
    val max = candies.reduce(_ max _)
    for((n,i) <- candies.zipWithIndex)
      res(i) = (extraCandies + n) >= max
    res
  }
  def test1 (){
    val candies: Array[Int] = Array[Int](2,3,5,1,3)
    val extraCandies: Int = 3
    print(kidsWithCandies(candies, extraCandies).foreach(n => println(n)))
  }
  def test2 (){
    val candies: Array[Int] = Array[Int](4,2,1,1,2)
    val extraCandies: Int = 1
    print(kidsWithCandies(candies, extraCandies).foreach(n => println(n)) + "\n")
  }
  def test3  (){
    val candies: Array[Int] = Array[Int](12,10,12)
    val extraCandies: Int = 10
    print(kidsWithCandies(candies, extraCandies).foreach(n => println(n)) + "\n")
  }
  test1()
  test2()
  test3()

}
