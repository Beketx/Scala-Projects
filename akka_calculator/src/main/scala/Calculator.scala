import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object Calculator{
  final case class Expression(exp: String, replyTo: ActorRef[ExpressionResult])
  final case class ExpressionResult(result: String, expression: String, from: ActorRef[Expression])
  var calc = new CalculatorClass()

  def apply(): Behavior[Expression] = Behaviors.receive { (context, message) =>
    println(s"Your request: ${message.exp}")
    val answer = calc.calculate(message.exp).toString
    message.replyTo ! ExpressionResult(answer, message.exp, context.self)
    Behaviors.same
  }
}