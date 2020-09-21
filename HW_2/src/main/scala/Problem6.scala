object Problem6 {
  def sumZero(n: Int): Array[Int] = {
    if (n == 1) return Array(0)
    val res = Array.ofDim[Int](n)
    for (i <- 0 until ((n / 2) * 2)) res(i) = if (i % 2 == 0) (i / 2) + 1
    else -((i / 2) + 1)
    if (n % 2 == 1) res(n - 1) = 0
    res
}
}
