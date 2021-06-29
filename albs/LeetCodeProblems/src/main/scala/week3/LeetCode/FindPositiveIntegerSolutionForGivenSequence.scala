package week3.LeetCode

import scala.collection.mutable.ArrayBuffer

class FindPositiveIntegerSolutionForGivenSequence extends App {

  class CustomFunction {
    // Returns f(x, y) for any given positive integers x and y.
    // Note that f(x, y) is increasing with respect to both x and y.
    // i.e. f(x, y) < f(x + 1, y), f(x, y) < f(x, y + 1)
    def f(x: Int, y: Int): Int = x + y
  }

  def findSolution(customfunction: CustomFunction, z: Int) = {
    var res = ArrayBuffer[List[Int]]()
    for (x <- 1 to z) {
      for (y <- 1 to z) {
        if (customfunction.f(x, y) == z)
          res += List(x, y)
      }
    }
    res.toList
  }
}
