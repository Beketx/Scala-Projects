import akka.actor.ActorSystem
import akka.kafka._
import akka.kafka.scaladsl.Consumer.DrainingControl
import akka.kafka.scaladsl.{Consumer, Producer}
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}


object Consumer1 extends App {
  implicit val system = ActorSystem("Consumer1")
  implicit val ec = system.dispatcher

  val config = system.settings.config.getConfig("akka.kafka.consumer")
  val topic = system.settings.config.getString("akka.kafka.consumer.kafka-clients.topic")
  val bootstrapServers = system.settings.config.getString("akka.kafka.consumer.kafka-clients.server")

  val consumerSettings = ConsumerSettings(system, new StringDeserializer, new StringDeserializer)
    .withBootstrapServers(bootstrapServers)
    .withGroupId("consumer1")
    .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  val producerSettings = ProducerSettings(config, new StringSerializer, new StringSerializer)
    .withBootstrapServers(bootstrapServers)

  val targetTopic = "final-consumer-topic"

  val committerSettings = CommitterSettings(system)

  val f = (value: String) => (value.toInt * 2).toString


  val done =
    Consumer.committableSource(consumerSettings, Subscriptions.topics(topic))
      .map { msg =>
        println(s"number: ${msg.record.value}")
        ProducerMessage.single(
          new ProducerRecord(targetTopic, msg.record.key, f(msg.record.value)),
          msg.committableOffset
        )
      }
      .toMat(Producer.committableSink(producerSettings, committerSettings))(DrainingControl.apply)
      .run()
}
