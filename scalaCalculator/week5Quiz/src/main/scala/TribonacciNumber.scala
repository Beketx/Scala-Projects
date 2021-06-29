object TribonacciNumber {
  def tribonacci(n: Int): Int = {
    if (n == 0) return 0
    if (n == 1 || n == 2) return 1
    var t0 = 0
    var t1 = 1
    var t2 = 1
    var i = 3
    while (i <= n) {
      t0 = t0 + t1 + t2
      t1 = t1 + t2 + t0
      t2 = t2 + t0 + t1
      i += 3
    }
    if (i == n + 1) return t2
    if (i == n + 2) return t1
    return t0
  }
}
