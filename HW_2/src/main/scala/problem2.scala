object problem2 extends App {
  def binarySolve(): Unit = {
    class ListNode(_x: Int=0, _next: ListNode = null) {
      var next: ListNode = _next
      var x: Int = _x
    }

    def prepare(array: Array[Int]):ListNode = {
      var node: ListNode = null
      for (x <- array.reverse) {
        val curNode = new ListNode(x)
        curNode.next = node
        node = curNode
      }
      node
    }

    def traverse(head: ListNode, res: String): String = {
      if (head.next == null) return res + " " + head.x
      traverse(head.next, res + " " + head.x)
    }

    def getDecimalValue(head: ListNode): Int = {
      ???
    }


    def test1(): Unit = {
      val rowData = Array(1, 0, 1)
      val head = prepare(rowData)
      println(traverse(head, ""))
    }

    test1()
  }
}
