package week3.LeetCode

import scala.collection.mutable.ArrayBuffer

class BuildArrayWithStackOperations extends App {
  def buildArray(target: Array[Int], n: Int): List[String] = {
    var ans = ArrayBuffer[String]()

    ans ++= ArrayBuffer.fill[List[String]](target(0) - 1)(List("Push", "Pop")).flatten
    ans += "Push"

    for (i <- 1 until target.length) {
      ans ++= ArrayBuffer.fill[List[String]](target(i) - target(i - 1) - 1)(List("Push", "Pop")).flatten
      ans += "Push"
    }

    ans.toList
  }
}
