import scala.io.Source
import java.io._
import sanskritnlp.transliteration._
val infileStr = "/home/vvasuki/stardict-sanskrit/sa-head/kalpadruma-sa/mUlam/kalpadruma-sa-v2.tsv"
val outfileStr = "/home/vvasuki/stardict-sanskrit/sa-head/kalpadruma-sa/mUlam/kalpadruma-sa-v2-dev.tsv"
val src = Source.fromFile(infileStr, "utf8")
val outFileObj = new File(outfileStr)
new File(outFileObj.getParent).mkdirs
val destination = new PrintWriter(outFileObj)

src.getLines.foreach(line => {
  var newLine = slp.toDevanagari(line)
  newLine = slp.restoreEscapeSequences(newLine)
  destination.println(newLine)
  // println(line)
  // println(newLine)
})
destination.close()
println("")
