/**
  */

import java.io._

import scala.io.Source
val infileStr = "/home/vvasuki/stardict-sanskrit/sa-vyAkaraNa/abhyankara-grammar/abhyankara-grammar.txt"
val outfileStr = "/home/vvasuki/stardict-sanskrit/sa-vyAkaraNa/abhyankara-grammar/abhyankara-grammar.babylon"
val src = Source.fromFile(infileStr, "utf8")
val outFileObj = new File(outfileStr)
new File(outFileObj.getParent).mkdirs
val destination = new PrintWriter(outFileObj)

var outEntries = src.getLines.map{ line => {
  // println(line)
  var output = line
  var pattern = "^\\{\\{.+\\}\\}".r
  output = output.replaceAll(pattern.toString(), "")
//  output = pattern.replaceAllIn(output, _ match { case pattern(key, c2, c3, meaning) => {
//    f"######$key\n$key $c2$c3<br>$meaning"
//  }})
  // println(output)
  output
}}.mkString("\n").split("######")

outEntries.foreach(entry => {
  destination.println(entry)
  destination.println("")
})
destination.close()
println("Done!")
