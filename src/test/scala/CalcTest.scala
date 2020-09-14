
import scala
//import org.junit._
//import org.junit.Assert._
import org.scalatest.Assertions._
class CalcTest {
  @Test def firstTest: Unit = {
    assert(5, Calc("2 3 +".split(" ")), 0.0)
    assert(6, Calc("2 3 +".split(" ")), 0.0)
    assert(6, Calc("9 3 -".split(" ")), 0.0)
    assert(3, Calc("9 3 /".split(" ")), 0.0)
  }
}
