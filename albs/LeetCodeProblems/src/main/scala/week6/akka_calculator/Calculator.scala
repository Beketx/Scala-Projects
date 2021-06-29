package week6.akka_calculator

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

import scala.collection.mutable

object Calculator {
  final case class Expression(body: String, replyTo: ActorRef[ExpressionResult])
  final case class ExpressionResult(result: String, expression: String, from: ActorRef[Expression])

  def apply(): Behavior[Expression] = Behaviors.receive { (context, message) =>
    println(s"Got expression: ${message.body}")
    message.replyTo ! ExpressionResult(calculate(message.body).toString, message.body, context.self)
    Behaviors.same
  }

  def calculate(input: String): Int = {
    val stack = mutable.Stack[Int]()
    var sign = '+'

    def identify_current_character(index: Int) = {
      input(index) match {
        case c: Char if isDigit(c) =>
          var next_index = index
          while (next_index < input.length && isDigit(input(next_index))) next_index += 1
          val num = input.substring(index, next_index).toInt
          (num, next_index)
        case c: Char =>
          (c, index + 1)
      }
    }

    def sum_up_all_numbers() = {
      stack.sum
    }

    def parse_expression(): Unit = {
      var current_index = 0
      while (current_index < input.length) {
        val (current_character, next_index) = identify_current_character(current_index)
        current_character match {
          case t: Int if sign == '/' =>
            stack.push(stack.pop() / t)
          case t: Int if sign == '*' =>
            stack.push(t * stack.pop())
          case t: Int if sign == '-' =>
            stack.push(-t)
          case t: Int if sign == '+' =>
            stack.push(t)
          case t: Char =>
            sign = t
        }
        current_index = next_index
      }
    }

    parse_expression()

    sum_up_all_numbers()
  }

  def isDigit(c: Char): Boolean = (c >= '0' && c <= '9')
}
