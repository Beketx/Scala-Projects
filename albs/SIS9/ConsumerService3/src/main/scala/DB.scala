import java.util.concurrent.atomic.AtomicLong

import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.model._
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpResponse
import org.apache.kafka.clients.consumer.ConsumerRecord

import scala.concurrent.{ExecutionContext, Future}

class DB {

  private val offset = new AtomicLong

  def save(record: ConsumerRecord[Array[Byte], String]): Future[Done] = {
    println(s"DB.save: ${record.value}")
    offset.set(record.offset)
    Future.successful(Done)
  }

  def loadOffset(): Future[Long] =
    Future.successful(offset.get)

  def update(data: String)(implicit system: ActorSystem,  ex: ExecutionContext): Future[Done] = {
    println(s"DB.update: $data")
    val response: Future[HttpResponse] = Http().singleRequest(
      HttpRequest(method = HttpMethods.POST,
        uri = "http://localhost:8080/receive",
        entity = HttpEntity(ContentTypes.`application/json`,
          s"""{ "value": "$data" }""")))
    Future.successful(Done)
  }
}