




//object MinimumDistanceBetweenBSTNodes {
//  def minDiffInBST(root: TreeNode): Int = {
//    var res = Math.max(root)
//    var pre = null
//    if (root.left != null) minDiffInBST(root.left)
//    if (pre != null) res = Math.min(res, root.val - pre)
//    pre = root.val
//    if (root.right != null) minDiffInBST(root.right)
//    res
//  }
//}
