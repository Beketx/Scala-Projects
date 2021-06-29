package week5.leetcode

object RangeSumBST {
//   Definition for a binary tree node.
  class TreeNode(_value: Int = 0, _left: TreeNode = null, _right: TreeNode = null) {
    var value: Int = _value
    var left: TreeNode = _left
    var right: TreeNode = _right
  }

  def rangeSumBST(root: TreeNode, L: Int, R: Int): Int = {
    var cur: Int = 0


    def dfs(node: TreeNode): Int = {
      if (node != null) {
        if (L <= node.value && node.value <= R)
          cur = cur + node.value
        if (L < node.value)
          dfs(node.left)
        if (R > node.value)
          dfs(node.right)
        cur
      } else {
        cur
      }

    }

    dfs(root)
  }
}
