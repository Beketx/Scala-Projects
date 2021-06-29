package week8.QuickstartExample


//#json-formats
import spray.json.DefaultJsonProtocol
import week8.QuickstartExample.UserRegistry.ActionPerformed

object JsonFormats  {
  // import the default encoders for primitive types (Int, String, Lists etc)
  import DefaultJsonProtocol._

  implicit val userJsonFormat = jsonFormat3(User)
  implicit val usersJsonFormat = jsonFormat1(Users)

  implicit val actionPerformedJsonFormat = jsonFormat1(ActionPerformed)
}
//#json-formats