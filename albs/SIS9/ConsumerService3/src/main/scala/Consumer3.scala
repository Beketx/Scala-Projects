import akka.actor.ActorSystem
import akka.kafka.ConsumerMessage.CommittableOffsetBatch
import akka.kafka._
import akka.kafka.scaladsl.Consumer
import akka.stream.scaladsl.Sink
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer


object Consumer3 extends App {
  implicit val system = ActorSystem("Consumer1")
  implicit val ec = system.dispatcher

  val config = system.settings.config.getConfig("akka.kafka.consumer")
  val topic = system.settings.config.getString("akka.kafka.consumer.kafka-clients.topic")
  val bootstrapServers = system.settings.config.getString("akka.kafka.consumer.kafka-clients.server")

  val consumerSettings = ConsumerSettings(system, new StringDeserializer, new StringDeserializer)
    .withBootstrapServers(bootstrapServers)
    .withGroupId("consumer3")
    .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")


  val db = new DB

  val done =
    Consumer.committableSource(consumerSettings, Subscriptions.topics(topic))
      .mapAsync(1) { msg =>
        db.update(msg.record.value).map(_ => msg.committableOffset)
      }
      .batch(max = 20, first => CommittableOffsetBatch.empty.updated(first)) { (batch, elem) =>
        batch.updated(elem)
      }
      .mapAsync(3)(_.commitScaladsl())
      .runWith(Sink.ignore)
}
