object RangeSumofBST {
  def rangeSumBST(root: TreeNode, L: Int, R: Int): Int = {
    if (root == null) 0
    else if (root.value < L) rangeSumBST(root.right, L ,R)
    else if (root.value > R) rangeSumBST(root.left, L, R)
    else root.value + rangeSumBST(root.left, L, R) + rangeSumBST(root.right, L, R)
  }
}
