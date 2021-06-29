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



