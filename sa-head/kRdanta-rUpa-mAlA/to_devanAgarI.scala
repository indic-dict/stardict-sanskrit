import scala.io.Source
import java.io._
import sanskritnlp.transliteration._
val infileStr = "/home/vvasuki/stardict-sanskrit/sa-head/kRdanta-rUpa-mAlA/mUlam/kRdanta-rUpa-mAlA.tsv"
val outfileStr = "/home/vvasuki/stardict-sanskrit/sa-head/kRdanta-rUpa-mAlA/kRdanta-rUpa-mAlA.tsv"
val src = Source.fromFile(infileStr, "utf8")
val outFileObj = new File(outfileStr)
new File(outFileObj.getParent).mkdirs
val destination = new PrintWriter(outFileObj)

src.getLines.foreach(line => {
  var newLine = harvardKyoto.toDevanagari(line)
  newLine = harvardKyoto.restoreEscapeSequences(newLine)
  destination.println(newLine)
  // println(line)
  println(newLine)
})
destination.close()
println("")

