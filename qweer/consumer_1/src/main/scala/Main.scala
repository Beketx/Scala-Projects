import akka.actor.ActorSystem
import akka.kafka.scaladsl.{Consumer, Producer}
import akka.kafka.{ConsumerSettings, ProducerSettings, Subscriptions}
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}

import scala.concurrent.ExecutionContextExecutor

object Main {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("ActorSystem")
    implicit val ec: ExecutionContextExecutor = system.dispatcher
    val producerConfig = system.settings.config.getConfig("akka.kafka.producer")
    val consumerConfig = system.settings.config.getConfig("akka.kafka.consumer")

    val consumerSettings =
      ConsumerSettings(consumerConfig, new StringDeserializer, new StringDeserializer)
        .withBootstrapServers("192.168.1.237:9092")
        .withGroupId("group1")
        .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    val producerSettings =
      ProducerSettings(producerConfig, new StringSerializer, new StringSerializer)
        .withBootstrapServers("192.168.1.237:9092")
    val db = OffsetStore
    db.loadOffset().map { fromOffset =>
      Consumer.plainSource(consumerSettings,
        Subscriptions.assignmentWithOffset(
          new TopicPartition("numbers", 0) -> fromOffset))
        .mapAsync(1)(db.businessLogicAndStoreOffset)
        .filter {
          case Some(_) => true
          case None => false
        }
        .map { option =>
          option.get
        }
        .runWith(Producer.plainSink(producerSettings))
    }
  }
}
