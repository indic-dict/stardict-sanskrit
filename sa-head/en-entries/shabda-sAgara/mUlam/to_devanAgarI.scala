import scala.io.Source
import java.io._
import sanskritnlp.transliteration._

val infile = "/home/vvasuki/stardict-sanskrit/en-head/shabda-sAgara/mUlam/shs_orig_utf8_v1.tsv"
val outfile = "/home/vvasuki/stardict-sanskrit/sa-head/shabda-sAgara/shabda-sAgara.tsv"
val src = Source.fromFile(infile, "utf8")
val destination = new PrintWriter(new File(outfile))

val hkPattern = """\{#(.+?)#\}""".r
val numPattern = """(\d+?\.)""".r
src.getLines.foreach(line => {
  var newLine = hkPattern.replaceAllIn(line, _ match {
    case hkPattern(hk) => harvardKyoto.toDevanagari(hk)})

  newLine = numPattern.replaceAllIn(newLine, _ match {
    case numPattern(num) => "\\\\n" + num})
  destination.println(newLine)
  println(newLine)
})
destination.close()
println("")
