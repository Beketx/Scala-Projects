package html

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, ResponseEntity}

class HtmlToResponse(val filename: String = "index.html") {
  import scala.io.Source
  private val template = Source.fromResource(filename)
  override def toString: String = template.mkString

  def entity: ResponseEntity = HttpEntity(ContentTypes.`text/html(UTF-8)`, toString)
  def response: HttpResponse = HttpResponse(entity = entity)
}