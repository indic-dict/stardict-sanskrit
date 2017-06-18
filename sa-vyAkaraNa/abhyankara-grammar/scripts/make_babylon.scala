/**
  */

import java.io._

import org.slf4j.LoggerFactory

import scala.io.Source

val log = LoggerFactory.getLogger("abhyankarabot")

val infileStr = "/home/vvasuki/stardict-sanskrit/sa-vyAkaraNa/abhyankara-grammar/abhyankara-grammar.txt"
val outfileStr = "/home/vvasuki/stardict-sanskrit/sa-vyAkaraNa/abhyankara-grammar/abhyankara-grammar.babylon"
val src = Source.fromFile(infileStr, "utf8")
val outFileObj = new File(outfileStr)
new File(outFileObj.getParent).mkdirs
val destination = new PrintWriter(outFileObj)



var outText = src.getLines.mkString("\n")
Range(1,10).foreach(_ => {
  // If a line does not start with ''', it is not a separate entry - unless there is an error in the wiki markup.
  // So, such a line can be merged with the previous line.
  outText = outText.replaceAll("\n(?=[^'\n])", " ")
  outText = outText.replaceAll("\n(?='[^'\n])", " ")
  //  outText = outText.replaceAll("\n(?=''[^'\n])", " ")
})
outText = outText.replaceAll(" +", " ")

outText = outText.replaceAll("<noinclude>.+</noinclude>", "")
outText = outText.replaceAll("</?poem>", "")
outText = outText.replaceAll("\\{\\{outdent\\|", "")
outText = outText.replaceAll("(left|right|center)[ =]+[']+.+?[']+", "")
Range(1,10).foreach(_ => {
  // Replace stuff like {{rule}}
  outText = outText.replaceAll("(?s)\\{\\{[^}]+?\\}\\}", "")
})

val entryLines = outText.split("\n").map(_.trim).filter(_.nonEmpty)
//entryLines.foreach(destination.println)
//destination.close()
//exit

var failedLineCount = 0
entryLines.foreach(entryLine => {
  if (entryLine.split("'''").length < 3) {
    log warn "Could not parse: " + entryLine
    failedLineCount += 1
  } else {
    val entries = entryLine.split("'''")
    var headwords = List(entries(1).trim)
    var mainEntry = entries.mkString(" ").trim

    // handle lines like '''मध्यमा''' or '''मध्यमवृत्ति''' See मध्यम (2).
    if(entries(2).trim == "or") {
      headwords = headwords :+ entries(3)
    }
    destination.println(headwords.mkString("|"))
    destination.println(mainEntry)
    destination.println("")

  }
})
destination.close()
log warn "Failed on " + failedLineCount + " lines"
log info "Done!"

