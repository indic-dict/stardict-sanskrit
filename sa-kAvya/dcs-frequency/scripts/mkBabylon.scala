// To run:
// PATH_TO_SANSKRITNLPJAVA=/home/vvasuki/sanskritnlpjava/target
// scala -classpath "$PATH_TO_SANSKRITNLPJAVA/sanskritnlp-1.0-SNAPSHOT/WEB-INF/lib/*:$PATH_TO_SANSKRITNLPJAVA/sanskritnlp-1.0-SNAPSHOT/WEB-INF/classes" stardict-sanskrit/sa-kAvya/dcs-frequency/scripts/mkBabylon.scala

import java.io._

import org.slf4j.LoggerFactory

import scala.io.Source
val log = LoggerFactory.getLogger("dcsfr")

val textFileStr = "/home/vvasuki/stardict-sanskrit/sa-kAvya/dcs-frequency/text2.json"

case class Text (
  Number:Int,
  TextID:Int,
  TextName: String,
  Authors: String,
  Era: String,
  Subject: String,
  DatingStart:Int,
  DatingEnd:Int,
  EstDateLesser:Int,
  EstDateGreater:Int,
  FIELD11: String,
  Completed: String,
  Source: String,
  TextorCommentary: String,
  SourceURL: String,
  TextDataURL: String,
  WikiLink: String,
  DateCommonlyAccepted: String,
  MeaningOfName: String,
  WikiSynopsis: String
)

def GetTexts = {
  import net.liftweb.json.DefaultFormats
  import net.liftweb.json._
  implicit val formats = DefaultFormats
  val src = Source.fromFile(textFileStr, "utf8")
  val jsonStr = src.getLines().mkString("\n")
  val textListJson = parse(jsonStr)
  for (textJason <- (textListJson \\ "Texts").children) {
    val text = textJason.extract[Text]
    log info text.toString
  }
}

GetTexts

val infileStr = "/home/vvasuki/stardict-sanskrit/sa-kAvya/dcs-frequency/dcs-frequency.csv"
val outfileStr = "/home/vvasuki/stardict-sanskrit/sa-kAvya/dcs-frequency/dcs-frequency.babylon"
val src = Source.fromFile(infileStr, "utf8")
val outFileObj = new File(outfileStr)
new File(outFileObj.getParent).mkdirs
val destination = new PrintWriter(outFileObj)

var failedLineCount = 0
src.getLines.foreach(inLine => {
  val entry = inLine.split(",")
  if(entry.length != 5) {
    log warn "Could not parse: " + entry.mkString(",")
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

