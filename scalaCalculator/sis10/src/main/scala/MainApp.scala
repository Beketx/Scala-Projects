import scala.collection.MapView

object MainApp extends App{


  def counter(textPart: String): Map[String, Int] ={
//    val cnts = textPart.flatMap(line =&t; line.split("")).map(word =&gt; (word, 1)).reduceByKey(_+_) counts.collect

    val cnts = textPart.split(" ").foldLeft(Map.empty[String, Int]){
      (count, word) => count + (word -> (count.getOrElse(word, 0) + 1))
    }
    cnts
  }

  var globalWorkerDataCollection = Map[String, Int]()
  def mergeWorkersData(map: Map[String, Int]): Map[String, Int] ={
    val merged = globalWorkerDataCollection.toSeq ++ map.toSeq
    val grouped = merged.groupBy(_._1)
    val cleaned = grouped.mapValues(_.map(_._2).toList.sum)
    globalWorkerDataCollection = cleaned.toMap
    globalWorkerDataCollection
  }
//  val a = counter("asdasd aaa ab ab asdasd")
//  val b = counter("zzz aaa ab ab asdasd")
//  println(a)
//  println(b)
//  println(mergeWorkersData(a))
//  println(mergeWorkersData(b))
  var t = List[String]()
  t:+="asdasd"
  t:+="1asdasd"
  println(t)

}
