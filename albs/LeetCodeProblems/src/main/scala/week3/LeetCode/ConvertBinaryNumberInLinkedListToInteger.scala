package week3.LeetCode

import scala.annotation.tailrec

object ConvertBinaryNumberInLinkedListToInteger {

  class ListNode(_x: Int = 0, _next: ListNode = null) {
    var next: ListNode = _next
    var x: Int = _x
  }

  def getDecimalValue(head: ListNode): Int = {
    @tailrec
    def find(node: ListNode, value: Int): Int = {
      if (node.next == null) (value << 1) + node.x
      else find(node.next, (value << 1) + node.x)
    }

    find(head, 0)
  }
}
