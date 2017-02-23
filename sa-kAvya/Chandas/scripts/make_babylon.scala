// To run:
// PATH_TO_SANSKRITNLPJAVA=/home/vvasuki/sanskritnlpjava/target
// scala -classpath "$PATH_TO_SANSKRITNLPJAVA/sanskritnlp-1.0-SNAPSHOT/WEB-INF/lib/*:$PATH_TO_SANSKRITNLPJAVA/sanskritnlp-1.0-SNAPSHOT/WEB-INF/classes" stardict-sanskrit/sa-kAvya/dcs-frequency/scripts/mkBabylon.scala

import java.io._

import org.slf4j.LoggerFactory
import sanskritnlp.transliteration._

import scala.collection.mutable
import scala.io.Source
val log = LoggerFactory.getLogger("Chandas")

def addFieldIfNotEmpty(fieldName: String, separator: String = " - ", fieldValue: String): String = {
  // log debug s"<BR><BR>${fieldName} - ${fieldValue}"
  if (fieldValue.nonEmpty) {
    return s"<BR><BR>${fieldName}$separator${fieldValue}"
  } else {
    return s""
  }
}

val samaChando_gaNAH = List(
  "",
  "",
  "",
  "",
  "",
  "",
  "उष्णिक्",
  "अनुष्टुभ्",
  "बृहती",
  "वीरज/ पङ्क्तिः",
  "त्रिष्टुभ्",
  "जगती",
  "अतिजगती",
  "शक्वरी",
  "अतिशक्वरी",
  "अष्टिः",
  "अत्यष्टिः",
  "धृतिः",
  "अतिधृतिः",
  "कृतिः",
  "प्रकृतिः",
  "आकृतिः",
  "विकृतिः",
  "संकृतिः",
  "अतिकृतिः",
  "उत्कृतिः"
)

val outfileStr = "/home/vvasuki/stardict-sanskrit/sa-kAvya/Chandas/Chandas.babylon"
val outFileObj = new File(outfileStr)
new File(outFileObj.getParent).mkdirs
val destination = new PrintWriter(outFileObj)

val samaFileStr = "/home/vvasuki/stardict-sanskrit/sa-kAvya/Chandas/mUlam/Chandas छन्दः - सम.tsv"
var src = Source.fromFile(samaFileStr, "utf8")
var failedLineCount = 0
var ignoredLineCount = 0
src.getLines.drop(1).foreach(inLine => {
  var entry = new mutable.ListBuffer[String]()
  entry ++= inLine.split("\t")
  if (entry.length < 4) {
    log warn "Could not parse: " + entry.mkString(",") + s" of length ${entry.length}"
    failedLineCount = failedLineCount + 1
  } else {
    while(entry.length != 16) {
      // log warn "Could not parse: " + entry.mkString(",") + s" of length ${entry.length}"
      entry += ""
    }
    val headwords = entry(0).split("\\|").map(_.trim()).mkString("|")
    var dict_entry = s"सम-वृत्तम्, अक्षराणि- ${entry(1)}, पादेऽक्षराणि- ${entry(2)}"
    dict_entry += addFieldIfNotEmpty(fieldName = "मात्राः", fieldValue = entry(7))
    dict_entry += addFieldIfNotEmpty(fieldName = "सङ्ख्याजातिः", fieldValue = samaChando_gaNAH(entry(2).toInt - 1))
    dict_entry += addFieldIfNotEmpty(fieldName = "जातयः", fieldValue = entry(12))
    dict_entry += addFieldIfNotEmpty(fieldName = "मात्रा-विन्यासः", separator = "<BR>", fieldValue = entry(3))
    dict_entry += addFieldIfNotEmpty(fieldName = "यतिः", fieldValue = entry(6))
    dict_entry += addFieldIfNotEmpty(fieldName = "लक्षणम्", fieldValue = entry(8))
    dict_entry += addFieldIfNotEmpty(fieldName = "लक्षण-मूलम्", fieldValue = entry(9))
    dict_entry += addFieldIfNotEmpty(fieldName = "गणविन्यासः", fieldValue = entry(13))
    dict_entry += addFieldIfNotEmpty(fieldName = "गानम्", fieldValue = entry(14))
    dict_entry += addFieldIfNotEmpty(fieldName = "उदाहरणम्", fieldValue = entry(10))
    dict_entry += addFieldIfNotEmpty(fieldName = "टिप्पणिः", fieldValue = entry(11))
    dict_entry += addFieldIfNotEmpty(fieldName = "भाव-रसौचित्यम्", cfieldValue = entry(15))
    dict_entry += addFieldIfNotEmpty(fieldName = "प्रसिद्धिः", fieldValue = entry(4))
    dict_entry += addFieldIfNotEmpty(fieldName = "स्मरण-सुलभता", fieldValue = entry(5))
    destination.println(headwords)
    destination.println(dict_entry)
    destination.println()
  }
})
src.close()

val ardhasamaFileStr = "/home/vvasuki/stardict-sanskrit/sa-kAvya/Chandas/mUlam/Chandas छन्दः - अर्धसम.tsv"
src = Source.fromFile(ardhasamaFileStr, "utf8")
src.getLines.drop(1).foreach(inLine => {
  var entry = new mutable.ListBuffer[String]()
  entry ++= inLine.split("\t")
  if (entry.length < 4) {
    log warn "Could not parse: " + entry.mkString(",") + s" of length ${entry.length}"
    failedLineCount = failedLineCount + 1
  } else {
    while(entry.length != 13) {
      // log warn "Could not parse: " + entry.mkString(",") + s" of length ${entry.length}"
      entry += ""
    }
    val headwords = entry(0).split("\\|").map(_.trim()).mkString("|")
    var dict_entry = s"सम-वृत्तम्, अक्षराणि- ${entry(1)}, पादेऽक्षराणि- ${entry(2)}"
    dict_entry += addFieldIfNotEmpty(fieldName = "मात्राः", fieldValue = entry(7))
    dict_entry += addFieldIfNotEmpty(fieldName = "सङ्ख्याजातिः", fieldValue = samaChando_gaNAH(entry(2).toInt - 1))
    dict_entry += addFieldIfNotEmpty(fieldName = "जातयः", fieldValue = entry(12))
    dict_entry += addFieldIfNotEmpty(fieldName = "मात्रा-विन्यासः", separator = "<BR>", fieldValue = entry(3))
    dict_entry += addFieldIfNotEmpty(fieldName = "यतिः", fieldValue = entry(6))
    dict_entry += addFieldIfNotEmpty(fieldName = "लक्षणम्", fieldValue = entry(8))
    dict_entry += addFieldIfNotEmpty(fieldName = "लक्षण-मूलम्", fieldValue = entry(9))
    dict_entry += addFieldIfNotEmpty(fieldName = "गणविन्यासः", fieldValue = entry(13))
    dict_entry += addFieldIfNotEmpty(fieldName = "गानम्", fieldValue = entry(14))
    dict_entry += addFieldIfNotEmpty(fieldName = "उदाहरणम्", fieldValue = entry(10))
    dict_entry += addFieldIfNotEmpty(fieldName = "टिप्पणिः", fieldValue = entry(11))
    dict_entry += addFieldIfNotEmpty(fieldName = "भाव-रसौचित्यम्", fieldValue = entry(15))
    dict_entry += addFieldIfNotEmpty(fieldName = "प्रसिद्धिः", fieldValue = entry(4))
    dict_entry += addFieldIfNotEmpty(fieldName = "स्मरण-सुलभता", fieldValue = entry(5))
    destination.println(headwords)
    destination.println(dict_entry)
    destination.println()
  }
})
src.close()

destination.close()
log warn "Failed on " + failedLineCount + " lines"
log warn "Ignored " + ignoredLineCount + " lines"
log info "Done!"
