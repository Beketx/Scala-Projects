package week5.leetcode

import scala.math.max
object LongestUnivaluePath {
//   Definition for a binary tree node.
   class TreeNode(_value: Int = 0, _left: TreeNode = null, _right: TreeNode = null) {
      var value: Int = _value
      var left: TreeNode = _left
      var right: TreeNode = _right
    }
  def longestUnivaluePath(root: TreeNode): Int = {
    var maxi = 0

    def find_max_depth(node: TreeNode): Int = {
      if (node != null) {
        var left: Int = find_max_depth(node.left)
        var right: Int = find_max_depth(node.right)

        if (node.left != null && node.left.value == node.value)
          left += 1
        else
          left = 0

        if (node.right != null && node.right.value == node.value)
          right += 1
        else
          right = 0

        maxi = max(maxi, left + right)
        max(left, right)
      }
      else {
        0
      }
    }

    find_max_depth(root)
    maxi
  }
}
