package week3.LeetCode

class DecompressRunLengthEncodedList extends App {
  def decompressRLElist(nums: Array[Int]): Array[Int] = {
    var res = Array[Int]()
    for (i <- nums.indices by 2)
      res ++= Array.fill[Int](nums(i))(nums(i + 1))

    res
  }

  decompressRLElist(Array(1, 1, 2, 3))
}
