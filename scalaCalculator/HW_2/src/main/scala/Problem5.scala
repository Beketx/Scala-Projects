object Problem5 {
  def decompressRLElist(nums: Array[Int]): Array[Int] = {
    (for (i <- Range(0,nums.length / 2);
          j <- 1 to nums(i * 2)) yield nums(i*2+1)).toArray
  }
}
