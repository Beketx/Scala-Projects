import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, ResponseEntity}

class HtmlToResponse (val filename: String = "index.html", val someContent: List[(String, String)] = List(("",""))) {
  import scala.io.Source
  private val template = Source.fromResource(filename)
  override def toString: String = template.mkString + getFileName()
  def getFileName(): String ={
    var b= new StringBuilder
    for(name <- someContent){
      b++= name._1 + " TIME UPLOADED: " + name._2 + "\n"
      b++= "\n"
    }
    b.toString()
  }
  def entity: ResponseEntity = HttpEntity(ContentTypes.`text/html(UTF-8)`, toString)
  def response: HttpResponse = HttpResponse(entity = entity)
}