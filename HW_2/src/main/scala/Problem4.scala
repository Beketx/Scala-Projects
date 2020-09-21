object Problem4 {
  def repeatedNTimes(A: Array[Int]): Int = {

    def count(setOf: Set[Int], remain: Array[Int]): Int =
      if (setOf.contains(remain.head)) return remain.head
      else count(setOf + remain.head, remain.tail)

    count(Set.empty, A)
  }
}
