import java.util
import scala.util.control.Breaks.break
import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors


class CalculatorClass {
  var ops =""
  var digits:Array[String] = Array()

  var res: Double = 0.0
  var exp = new StringBuilder("")
  var opBool = false

  var shifter = new ShiftLeft()

  def addExp(str:String){
    if(str!="="){
      if(!opBool){
        if(checkStrType(str)){
          opBool = true
          exp.addAll(str)
          //        add op after number
        }else {
          exp.addAll(str)
          //        add number
        }
      }else{
        if(checkStrType(str)){
          println("Error: we're waiting for number after operation, enter valid input")
        }else
        {
          exp.addAll(str)
          opBool=false
          //        add number after op
        }
      }
    }

  }
  def checkStrType(str:String):Boolean={
    if(str=="*"||str=="/"||str=="+"||str=="-"){
      return true
    }else{
      return false
    }
  }

  def shiftToLeft(array: Array[String], ind: Int, tempRes:Int): Array[String] ={
    array(ind) = tempRes.toString
    for(i <- ind+1 to array.length-2){
      array(i) = array(i+1)
    }
    return array
  }
  //    5 6 x 2 3 2 6
//      5 6 x 3 2 6
  def priority(): Unit ={

    for (i<- 0 to ops.length-1-shifter.dead) {

      if(ops(i)=='/'){
        var tempRes = caseOps(digits(i).toDouble,digits(i+1).toDouble,ops(i))
        digits = shifter.shiftToLeft(digits,i, tempRes)
        ops = shifter.shiftOps(ops,i)
//        println("/ tested" + tempRes)
        if(shifter.sizeOps==0){
          res = tempRes
        }else
        priority()

      }else
        if(ops(i)=='*') {
          var tempRes = caseOps(digits(i).toDouble, digits(i + 1).toDouble, ops(i))
          digits = shifter.shiftToLeft(digits, i, tempRes)
          ops = shifter.shiftOps(ops, i)
//          println("* tested" + tempRes)
          if(shifter.sizeOps==0){
            res = tempRes
          }else
            priority()

        }
  }


}
  def calculate(exp:String): Double ={
    ops = exp.replaceAll("\\d","")
    digits = exp.split("[+*-/]")
    res = digits(0).toDouble
    shifter.size = digits.length
    shifter.sizeOps = ops.length
    if(ops.contains('/')||ops.contains('*')){
//      println("test op")
      priority()
    }
//    5+6-2/2*3-2*6
//    5 6 2 2 3 2 6
    for (i<- 0 to shifter.sizeOps-1){

      if(i==shifter.size-1){
        return res
      }else{
        if(i==0){
          res = caseOps(digits(i).toDouble,digits(i+1).toDouble,ops(i))
        }else
          res = caseOps(res,digits(i+1).toDouble,ops(i))
      }
    }
    return res
  }
  def caseOps(left: Double,right: Double,op: Char):Double={
    var tempRes =
    op match{
            case '+' => left + right
            case '-' =>  left - right
            case '/' =>  left / right
            case '*' =>  left * right
          }
    return tempRes
  }

}
