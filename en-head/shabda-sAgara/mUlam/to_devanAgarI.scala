import scala.io.Source
import java.io._
import sanskritnlp.transliteration._

val infile = "/home/vvasuki/stardict-sanskrit/en-head/shabda-sAgara/mUlam/shs_orig_utf8_v1.tsv"
val outfile = "/home/vvasuki/stardict-sanskrit/sa-head/shabda-sAgara/shabda-sAgara.tsv"
val src = Source.fromFile(infile, "utf8")
val destination = new PrintWriter(new File(outfile))

val pattern = """\{#(.+?)#\}""".r
src.getLines.foreach(line => {
  val newLine = pattern.replaceAllIn(line, _ match {
    case pattern(hk) => harvardKyoto.toDevanagari(hk)})
  // println(line)
  destination.println(newLine)
})
