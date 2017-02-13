/**
  */

import java.io._

import scala.io.Source
val infileStr = "/home/vvasuki/stardict-sanskrit/sa-kAvya/dcs-frequency/dcs-frequency.txt"
val outfileStr = "/home/vvasuki/stardict-sanskrit/sa-kAvya/dcs-frequency/dcs-frequency.babylon"
val src = Source.fromFile(infileStr, "utf8")
val outFileObj = new File(outfileStr)
new File(outFileObj.getParent).mkdirs
val destination = new PrintWriter(outFileObj)

val log = LoggerFactory.getLogger("dcsfr")

var failedLineCount = 0
src.getLines.foreach(inLine => {
  val entry = inLine.split(",")
  if(entry.length != 6) {
    log warn "Could not parse: " + entry
    failedLineCount += 1
  } else {

  }
})
//outText = outText.replaceAll("\\{\\{outdent\\|", "")
//outText = outText.replaceAll("(left|right|center)[ =]+[']+.+?[']+", "")
//Range(1,10).foreach(_ => {
//  outText = outText.replaceAll("(?s)\\{\\{[^}]+?\\}\\}", "")
//})
//Range(1,10).foreach(_ => {
//  outText = outText.replaceAll("\n(?=[^'\n])", " ")
//  outText = outText.replaceAll("\n(?='[^'\n])", " ")
////  outText = outText.replaceAll("\n(?=''[^'\n])", " ")
//})
//outText = outText.replaceAll(" +", " ")

//entryLines.foreach(destination.println)
destination.close()
log warn "Failed on " + failedLineCount + " lines"
log info "Done!"

