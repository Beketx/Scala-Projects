object MinimumDistanceBetweenBSTNodes{
  def minDiffInBST(root: TreeNode): Int = {
    var arr = Array[Int]()

    def main(node: TreeNode = root): Unit = {
      if (node != null) {
        main(node.left)
        arr :+= node.value
        main(node.right)
      }
    }

    main()
    (0 until arr.length - 1).map(i => math.abs(arr(i + 1) - arr(i))).min
  }


}



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
