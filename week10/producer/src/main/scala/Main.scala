import akka.actor.ActorSystem
import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.stream.scaladsl.Source
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration.DurationInt
import scala.util.Random

object Main {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("ActorSystem")
    implicit val ec: ExecutionContextExecutor = system.dispatcher
    val config = system.settings.config.getConfig("akka.kafka.producer")
    val producerSettings =
      ProducerSettings(config, new StringSerializer, new StringSerializer)
        .withBootstrapServers("192.168.1.237:9092")
    Source.repeat(1)
      .delay(2.seconds)
      .map(_ => Random.nextInt())
      .map(value => new ProducerRecord[String, String]("numbers", value.toString))
      .runWith(Producer.plainSink(producerSettings))
  }
}
