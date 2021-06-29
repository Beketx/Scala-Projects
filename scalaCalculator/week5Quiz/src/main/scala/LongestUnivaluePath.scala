object LongestUnivaluePath {
  def longestUnivaluePath(root: TreeNode): Int = {
    var maxUnivaluePath = 0


    def longestUnivaluePathStart(root: TreeNode): Int = {

      if (root == null) {
        return 0
      }

      var leftLength = longestUnivaluePathStart(root.left)
      var rightLength = longestUnivaluePathStart(root.right)

      if (root.left != null && root.value == root.left.value) {
        leftLength += 1
      } else {
        leftLength = 0
      }

      if (root.right != null && root.value == root.right.value) {
        rightLength += 1
      } else {
        rightLength = 0
      }

      maxUnivaluePath = Math.max(maxUnivaluePath, leftLength + rightLength)

      return Math.max(leftLength, rightLength)
    }

    longestUnivaluePathStart(root)
    maxUnivaluePath
  }
}
