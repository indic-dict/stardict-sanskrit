package sanskritnlp.ocr

import org.slf4j.LoggerFactory
import sanskritnlp.transliteration.harvardKyoto

import scala.io.Source
import scala.util.matching.Regex

class GocrOutputIterator(ocrFileName: String) extends ocrOutputIterator {
  override val log = LoggerFactory.getLogger(this.getClass)

  val linesIter = Source.fromFile(ocrFileName, "utf8").getLines()

  // identify lines like:
  // {{{{{{/cns/oe-d/home/vvasuki/abhyankar/abhyankar_images-004.png}}}}}}
  val imageIdPattern = """\{\{\{\{\{\{.+-(\d+)\.png\}\}\}\}\}\}""".r

  override def hasNext: Boolean = linesIter.hasNext

  override def nextPage: String = {
    if(!hasNext) {
      return ""
    }
    if (pagesElapsed == 0) {
      linesIter.find(_.matches(imageIdPattern.toString()))
    }
    pagesElapsed = pagesElapsed + 1
    // This consumes the next page line in iter2 as well!
    val pageLines = linesIter.takeWhile(!_.matches(imageIdPattern.toString()))
    return pageLines.fold("")((a, b) => a + "\n" + b)
  }
}

object gocr {
  val log = LoggerFactory.getLogger(this.getClass)
  def main(args: Array[String]) {
    val gocrOut = new GocrOutputIterator("/home/vvasuki/sanskrit-ocr-r0/vaak/vyAkaraNam/abhyankar-grammar/abhyankar-grammar-gocr.txt")

    var page = ""
    while (gocrOut.hasNext && gocrOut.pagesElapsed < 10) {
      page = gocrOut.nextPage
      gocrOut.logPagesElapsed
      log info page
    }
  }
}
