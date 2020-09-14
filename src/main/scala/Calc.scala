object Calc {
  def apply(args: Seq[String]): Double ={
    val stack: ArrayStack[String] = ArrayStack[Double]()
    for(arg <- args; if arg.nonEmpty) arg match {
      case "+" => stack.push(stack.pop() + stack.pop())
      case "*" => stack.push(stack.pop() * stack.pop())
      case "-" => val tmp = stack.pop() stack.push(stack.pop - tmp)
      case "/" => val tmp = stack.pop() stack.push(stack.pop / tmp)

      case x => stack.push(x.toDouble)
    }
    stack.pop()
  }
}
