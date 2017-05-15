package sanskritnlp.ocr

import java.io.File

import org.slf4j.LoggerFactory

import scala.io.Source

/**
  * Created by vvasuki on 3/5/16.
  */
class SanskritOCROutputIterator(ocrDirName: String) extends ocrOutputIterator {
  override val log = LoggerFactory.getLogger(this.getClass)
  val pageFiles = new File(ocrDirName).listFiles.sorted
  override def hasNext: Boolean = pagesElapsed < pageFiles.length

  override def nextPage: String = {
    if(!hasNext) {
      return ""
    }
    pagesElapsed = pagesElapsed + 1
    val pageFile = pageFiles(pagesElapsed-1)
    val pageFileName = pageFile.getName
    log info pageFileName
    val pageLines = Source.fromFile(pageFiles(pagesElapsed-1), "utf8").getLines()
    return pageLines.fold("")((a, b) => a + "\n" + b)
  }
}

object sanskritocr {
  val log = LoggerFactory.getLogger(this.getClass)
  def main(args: Array[String]) {
    val ocrOut = new SanskritOCROutputIterator("/home/vvasuki/sanskrit-ocr-r0/vaak/vyAkaraNam/ganaratnamahodadhi/SanskritOCR/")

    var page = ""
    while (ocrOut.hasNext && ocrOut.pagesElapsed < 10) {
      page = ocrOut.nextPage
      ocrOut.logPagesElapsed
      log info page
    }
  }
}
