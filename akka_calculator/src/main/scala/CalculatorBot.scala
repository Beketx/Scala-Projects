
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object CalculatorBot {
  def apply(): Behavior[Calculator.ExpressionResult] = {
    bot()
  }

  private def bot(): Behavior[Calculator.ExpressionResult] =
    Behaviors.receive { (context, message) =>
      println("Result from bot: " + message.expression + " = " + message.result )
      Behaviors.stopped
    }
}


