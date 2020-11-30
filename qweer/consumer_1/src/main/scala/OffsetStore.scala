import java.util.concurrent.atomic.AtomicLong

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.ProducerRecord

import scala.concurrent.Future

object OffsetStore {
  private val offset = new AtomicLong

  def businessLogicAndStoreOffset(record: ConsumerRecord[String, String]): Future[Option[ProducerRecord[String, String]]] =
  {
    val value = record.value().toInt
    offset.set(record.offset)
    if (value > 0) {
      Future.successful(Some(new ProducerRecord("consumer_2", s"${value % 1000}")))
    } else Future.successful(None)
  }

  def loadOffset(): Future[Long] =
    Future.successful(offset.get)
}
