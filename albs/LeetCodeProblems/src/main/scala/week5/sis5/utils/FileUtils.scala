package week5.sis5.utils

import java.io._

import scala.io.Source
object FileUtils {

  def readFile(fileName: String): String = {
    val bufferedSource = Source.fromFile(fileName, "utf-8")
    try {
      bufferedSource.getLines().mkString("\n")
    } finally {
      bufferedSource.close()
    }
  }

  def writeFile(fileName: String, data: String): Unit = {
    val file = new File(fileName)
    val bw = new BufferedWriter(new FileWriter(file))
    try {
      bw.write(data)

    } finally {
      bw.close()
    }
  }
}
