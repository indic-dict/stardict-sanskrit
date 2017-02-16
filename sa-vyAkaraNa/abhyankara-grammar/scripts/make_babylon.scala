/**
  */

import java.io._

import org.slf4j.LoggerFactory

import scala.io.Source

val log = LoggerFactory.getLogger("abhyankarabot")

log info "Doing nothing. Test run. exiting."
exit()

val infileStr = "/home/vvasuki/stardict-sanskrit/sa-vyAkaraNa/abhyankara-grammar/abhyankara-grammar.txt"
val outfileStr = "/home/vvasuki/stardict-sanskrit/sa-vyAkaraNa/abhyankara-grammar/abhyankara-grammar.babylon"
val src = Source.fromFile(infileStr, "utf8")
val outFileObj = new File(outfileStr)
new File(outFileObj.getParent).mkdirs
val destination = new PrintWriter(outFileObj)



var outText = src.getLines.mkString("\n")
outText = outText.replaceAll("<noinclude>.+</noinclude>", "")
outText = outText.replaceAll("\\{\\{outdent\\|", "")
outText = outText.replaceAll("(left|right|center)[ =]+[']+.+?[']+", "")
Range(1,10).foreach(_ => {
  outText = outText.replaceAll("(?s)\\{\\{[^}]+?\\}\\}", "")
})
Range(1,10).foreach(_ => {
  outText = outText.replaceAll("\n(?=[^'\n])", " ")
  outText = outText.replaceAll("\n(?='[^'\n])", " ")
//  outText = outText.replaceAll("\n(?=''[^'\n])", " ")
})
outText = outText.replaceAll(" +", " ")

val entryLines = outText.split("\n").map(_.trim).filter(_.nonEmpty)
//entryLines.foreach(destination.println)
//destination.close()
//exit

var failedLineCount = 0
entryLines.foreach(entryLine => {
  if (entryLine.split("'''").length != 3) {
    log warn "Could not parse: " + entryLine
    failedLineCount += 1
  } else {
    val entries = entryLine.split("'''")
    destination.println(entries(1).trim)
    destination.println(entries(2).trim)
    destination.println("")
  }
})
destination.close()
log warn "Failed on " + failedLineCount + " lines"
log info "Done!"

