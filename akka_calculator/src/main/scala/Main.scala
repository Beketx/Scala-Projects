import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object Main {

  final case class ReadFromInput(expression: String)

  def apply(): Behavior[ReadFromInput] =
    Behaviors.setup { context =>
      val calculator = context.spawn(Calculator(), "greeter")

      Behaviors.receiveMessage { message =>
        val replyTo = context.spawn(CalculatorBot(), message.expression)
        calculator ! Calculator.Expression(message.expression, replyTo)
        Behaviors.same
      }
    }

  def main(args: Array[String]): Unit = {
    val system: ActorSystem[ReadFromInput] =
      ActorSystem(Main(), "mainCalc")

    system ! ReadFromInput("3+2-5*2")
    //    system ! ReadFromInput("6/2")
  }
}

//object Main extends App{
//  var calc = new Calculator()
//    println("input like \n" +
//      "5 \n" +
//      "3 \n" +
//      "+ \n" +
//      "3 \n" +
//      "/ \n" +
//      "2 \n" +
//      "or like 53+3/2*4-6\n" +
//      "then add newline with = \n")
//    var op = ""
//    while ( op!="=" ){
//      op = scala.io.StdIn.readLine()
//      calc.addExp(op)
//    }
//    print(calc.exp.toString() + " = ")
//    print(calc.calculate(calc.exp.toString()))
//
//
//
//
//
//
////  var ops =""
////  var exp =""
////  exp = scala.io.StdIn.readLine()
////
////  var digits:Array[String] = Array()
////
////  digits = exp.split("[+*-/]")
////  ops = exp.replaceAll("\\d","")
////
////  var ind = ops.indexOf('/')
////  println(ind)
////  var size = 0
////  var sizeOps = 0
////  def shiftToLeft(array: Array[String], ind: Int, tempRes:Int): Array[String] ={
////    array(ind) = tempRes.toString
////    for(i <- ind+1 to array.length-2){
////      array(i) = array(i+1)
////    }
////    size = array.length-1
////    return array
////  }
////  def shiftOps(array: String, ind: Int):String ={
////    val myString = new StringBuilder(array)
////    for(i <- ind to array.length-2){
////
////      myString.setCharAt(i, array.charAt(i+1))
////    }
////    sizeOps = array.length-1
////    return myString.toString()
////  }
//////  +-/-+
////  var a = shiftToLeft(digits,ind, 555)
////  for(j <- 0 to size-1){
////    println(a(j))
////  }
////  var b = shiftOps(ops,ind)
////  for(j <- 0 to sizeOps-1){
////    println(b(j))
////  }
//}

