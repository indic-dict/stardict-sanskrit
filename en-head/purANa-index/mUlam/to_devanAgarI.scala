import scala.io.Source
import java.io._
import sanskritnlp.transliteration.as
val infileStr = "/home/vvasuki/stardict-sanskrit/en-head/purANa-index/mUlam/purANa-index.tsv"
val outfileStr = "/home/vvasuki/stardict-sanskrit/sa-head/purANa-index/purANa-index-base.tsv"
val src = Source.fromFile(infileStr, "utf8")
val outFileObj = new File(outfileStr)
new File(outFileObj.getParent).mkdirs
val destination = new PrintWriter(outFileObj)

val pattern = """\{%(.+?)%\}""".r
src.getLines.foreach(line => {
  val newLine = pattern.replaceAllIn(line, _ match {
    case pattern(as_str) => as.toDevanagari(as_str)})
  // println(line)
  destination.println(newLine)
})
println("")
