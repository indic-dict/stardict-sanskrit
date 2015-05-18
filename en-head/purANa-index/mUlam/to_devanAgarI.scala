import scala.io.Source
import java.io._
import sanskritnlp.transliteration.as
val infileStr = "/home/vvasuki/stardict-sanskrit/en-head/purANa-index/mUlam/purANa-index.tsv"
val outfileStr = "/home/vvasuki/stardict-sanskrit/sa-head/purANa-index-dev/mUlam/purANa-index-base.tsv"
val src = Source.fromFile(infileStr, "utf8")
val outFileObj = new File(outfileStr)
new File(outFileObj.getParent).mkdirs
val destination = new PrintWriter(outFileObj)

val asPattern = """\{%(.+?)%\}""".r
val asPatternUnmarked = """(\W)(\w+?\d\w*?)(\W)""".r
src.getLines.foreach(line => {
  var newLine = asPattern.replaceAllIn(line, _ match {
    case asPattern(as_str) => as.toDevanagari(as_str)})
  newLine = asPatternUnmarked.replaceAllIn(newLine, _ match {
    case asPatternUnmarked(fore, as_str, aft) => fore + as.toDevanagari(as_str) + aft })
  destination.println(newLine)
  // println(line)
  println(newLine)
})
destination.close()
println("")
