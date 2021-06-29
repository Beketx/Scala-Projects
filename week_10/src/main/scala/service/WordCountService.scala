package service

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import node.Node.{ResultFromText, WordsCounterResult}

object WordCountService {

  final case class Ping(value: String, replyTo: ActorRef[WordsCounterResult])

  def apply(nodeName: String): Behavior[Ping] = Behaviors.setup { context =>

    Behaviors.receiveMessage {
      case Ping(value, replyTo) =>
        val x = counter(value)
        replyTo ! WordsCounterResult(nodeName, x)
        Behaviors.same
    }
  }

  def counter(textPart: String): Map[String, Int] ={

    val cnts = textPart.split(" ").foldLeft(Map.empty[String, Int]){
      (count, word) => count + (word -> (count.getOrElse(word, 0) + 1))
    }
    cnts
  }


}
