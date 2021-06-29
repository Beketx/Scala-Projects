import scala.io.StdIn.readLine

object CalculatorInteractive extends App {
  def output_expression(): Unit = {
    println("Output: " + process_expression().toString)
  }

  def process_expression(): Int = {
    Calculator.calculate(read_expression())
  }

  def read_expression(): String = {
    println("Enter your math expression: ")
    readLine()
  }

  output_expression()
}
