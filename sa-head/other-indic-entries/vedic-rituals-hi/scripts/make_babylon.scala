/**
  * Created by vvasuki on 6/27/16.
  * Unhandled cases:
  * भेदन (भिद् ल्युट्)
  */

import scala.io.Source
import java.io._

import sanskritnlp.transliteration._

import scala.util.matching.Regex

val infileStr = "/home/vvasuki/stardict-sanskrit/sa-head/vedic-rituals-hi/vedic-rituals-hi.txt"
val outfileStr = "/home/vvasuki/stardict-sanskrit/sa-head/vedic-rituals-hi/vedic-rituals-hi.babylon"
val src = Source.fromFile(infileStr, "utf8")
val outFileObj = new File(outfileStr)
new File(outFileObj.getParent).mkdirs
val destination = new PrintWriter(outFileObj)

var outEntries = src.getLines.map{ line => {
  // println(line)
  var output = line
  val pattern = "^(\\S+)\\s+(पु|वि|न|क्रि\\.वि|स्त्री)([ .])(.+)".r
  output = pattern.replaceAllIn(output, _ match { case pattern(key, c2, c3, meaning) => {
    f"######$key\n$key $c2$c3<br>$meaning"
  }})
  // println(output)
  output
}}.mkString(" ").split("######")

outEntries.foreach(entry => {
  destination.println(entry)
  destination.println("")
})
destination.close()
println("Done!")
