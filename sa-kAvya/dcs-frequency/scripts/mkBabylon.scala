// To run:
// PATH_TO_SANSKRITNLPJAVA=/home/vvasuki/sanskritnlpjava/target
// scala -classpath "$PATH_TO_SANSKRITNLPJAVA/sanskritnlp-1.0-SNAPSHOT/WEB-INF/lib/*:$PATH_TO_SANSKRITNLPJAVA/sanskritnlp-1.0-SNAPSHOT/WEB-INF/classes" stardict-sanskrit/sa-kAvya/dcs-frequency/scripts/mkBabylon.scala

import java.io._

import org.slf4j.LoggerFactory

import sanskritnlp.transliteration._
import scala.collection.mutable
import scala.io.Source
val log = LoggerFactory.getLogger("dcsfr")

val textFileStr = "/home/vvasuki/stardict-sanskrit/sa-kAvya/dcs-frequency/text2.json"

// Example data:
//{
//  "Number":1,
//  "TextID (DCS)":15,
//  "Text Name":"Abhidharmakośa",
//  "Author(s)":"Vasubandhu",
//  "Era":"classical",
//  "Subject":"Buddhist",
//  "Dating Start":400,
//  "Dating End":500,
//  "Est Date Lesser":400,
//  "Est Date Greater":500,
//  "FIELD11":"OK",
//  "Completed":"",
//  "Source":"DCS",
//  "Text or Commentary":"Text",
//  "Source URL":"http://kjc-fs-cluster.kjc.uni-heidelberg.de/dcs/index.php?contents=texte&IDTextDisplay=15",
//  "Text Data URL":"https://goo.gl/kv94Ki",
//  "Wiki Link":"https://en.wikipedia.org/wiki/Abhidharma-kosa",
//  "Date Commonly Accepted":"",
//  "Meaning of Name":"Treasury of Supreme Truth",
//  "Wiki Synopsis":"Abhidharma-kośa (Sanskrit; Tibetan: chos mngon pa'i mdzod; English: Treasury of Abhidharma) is a key text on the abhidharma written in Sanskrit verse by Vasubandhu, in the 4th or 5th century. It summarizes the Sarvāstivādin tenets in eight chapters with a total of around 600 verses. The text was widely respected, and used by schools of Mahayana Buddhism in India, Tibet, and the Far East."
//},

case class Text (
  Number:Int,
  TextID:String,
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

val texts = mutable.HashMap[String, Text]()

def GetTexts = {
  import net.liftweb.json.{DefaultFormats, _}
  implicit val formats = DefaultFormats
  val src = Source.fromFile(textFileStr, "utf8")
  val jsonStr = src.getLines().mkString("\n")
  val textListJson = parse(jsonStr)
  // Fails: val texts = textListJson.extract[List[Text]]
  for (textJason <- (textListJson \ "Texts").children) {
    log info textJason.toString
    val tname = textJason \ "Text Name"
//    log info tname.toString
    val text = Text(
      (textJason \ "Number").extract[Int],
      (textJason \ "TextID (DCS)").extract[Int].toString,
      (textJason \ "Text Name").extract[String],
      (textJason \ "Author(s)").extract[String],
      (textJason \ "Era").extract[String],
      (textJason \ "Subject").extract[String],
      (textJason \ "Dating Start").extract[Int],
      (textJason \ "Dating End").extract[Int],
      (textJason \ "Est Date Lesser").extract[Int],
      (textJason \ "Est Date Greater").extract[Int],
      (textJason \ "FIELD11").extract[String],
      (textJason \ "Completed").extract[String],
      (textJason \ "Source").extract[String],
      (textJason \ "Text or Commentary").extract[String],
      (textJason \ "Source URL").extract[String],
      (textJason \ "Text Data URL").extract[String],
      (textJason \ "Wiki Link").extract[String],
      (textJason \ "Date Commonly Accepted").extract[String],
      (textJason \ "Meaning of Name").extract[String],
      (textJason \ "Wiki Synopsis").extract[String]
    )
    texts(text.TextID) = text
  }
  log info f"Gathered ${texts.size}%d texts"
}

GetTexts

val infileStr = "/home/vvasuki/stardict-sanskrit/sa-kAvya/dcs-frequency/dcs-frequency.csv"
val outfileStr = "/home/vvasuki/stardict-sanskrit/sa-kAvya/dcs-frequency/dcs-frequency.babylon"
val src = Source.fromFile(infileStr, "utf8")
val outFileObj = new File(outfileStr)
new File(outFileObj.getParent).mkdirs
val destination = new PrintWriter(outFileObj)

var failedLineCount = 0
var ignoredLineCount = 0
src.getLines.foreach(inLine => {
  val entry = inLine.split(",")
  if(entry.length != 5) {
    log warn "Could not parse: " + entry.mkString(",")
    failedLineCount += 1
  } else if(entry(4) == "0") {
    log warn "Ignoring: " + entry.mkString(",")
    ignoredLineCount += 1
  } else {
    val word = entry(0)
    val grammar_hint = entry(1) + " " + entry(2)
    //    log debug (word)
    //    log debug entry(3).split(";").mkString(" ")
    val textOccurances = entry(3).split(";").map(_.split(":")).map(x => Tuple2(texts(x(0)), x(1).toInt))
    val totalFrequency = entry(4)
    var occuranceString = f"<BR>===================<BR>कृति-दृष्ट्या-<BR>"
    occuranceString += textOccurances.sortBy(-_._2).map(x =>iast.toDevanagari( x._1.TextName).get + " - " + x._2).mkString("<BR>")
    occuranceString += f"<BR>===================<BR>कर्तृ-दृष्ट्या-<BR>"
    occuranceString += textOccurances.map(x => Tuple2(iast.toDevanagari(x._1.Authors).get, x._2)).groupBy(_._1).mapValues(x => (x(0)._1, x.map(_._2).sum)).values.toList.sortBy(-_._2).map(x => x._1 + " - " + x._2).mkString("<BR>")
    occuranceString += f"<BR>===================<BR>विषय-दृष्ट्या-<BR>"
    occuranceString += textOccurances.map(x => Tuple2(x._1.Subject, x._2)).groupBy(_._1).mapValues(x => (x(0)._1, x.map(_._2).sum)).values.toList.sortBy(-_._2).map(x => x._1 + " - " + x._2).mkString("<BR>")
    val dict_entry = f"$word%s<BR>$grammar_hint%s<BR>$occuranceString%s<BR><BR>Total: $totalFrequency%s<BR>"

    destination.println(iast.toDevanagari(word).get)
    destination.println(dict_entry)
    destination.println()
  }
})

//entryLines.foreach(destination.println)
destination.close()
log warn "Failed on " + failedLineCount + " lines"
log warn "Ignored " + ignoredLineCount + " lines"
log info "Done!"

