package week5.leetcode


import scala.math.min
object MinimumDistanceBetweenBSTNodes {
  //   Definition for a binary tree node.
  class TreeNode(_value: Int = 0, _left: TreeNode = null, _right: TreeNode = null) {
    var value: Int = _value
    var left: TreeNode = _left
    var right: TreeNode = _right
  }
  def minDiffInBST(root: TreeNode): Int = {
    var last = -1
    var mini = Int.MaxValue
    def inOrder(node: TreeNode): Unit = {
      if (node != null) {
        inOrder(node.left)
        if (last != -1)
          mini = min(mini, node.value - last)
        last = node.value
        inOrder(node.right)
      }
    }
    inOrder(root)
    mini
  }
}


//object MinimumDistanceBetweenBSTNodes {
//  //   Definition for a binary tree node.
//  class TreeNode(_value: Int = 0, _left: TreeNode = null, _right: TreeNode = null) {
//    var value: Int = _value
//    var left: TreeNode = _left
//    var right: TreeNode = _right
//  }
//  def minDiffInBST(root: TreeNode): Int = {
//    def inOrder(node: TreeNode, last: Int, mini: Int) = {
//      node match {
//        case Some(_) => {
//
//        }
//        case None => 0
//      }
//    }
//    inOrder(root, 0, Int.MaxValue)
//  }
//}

