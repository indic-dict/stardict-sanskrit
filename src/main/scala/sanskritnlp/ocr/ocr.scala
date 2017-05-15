package sanskritnlp.ocr

import org.slf4j.LoggerFactory

/**
  * Created by vvasuki on 3/5/16.
  */
trait ocrOutputIterator {
  val log = LoggerFactory.getLogger(this.getClass)
  var pagesElapsed = 0

  def hasNext: Boolean;

  def logPagesElapsed = {
    log info s"Pages elapsed: $pagesElapsed"
  }

  def skipNPages(numToSkip: Int) = {
    logPagesElapsed
    Range(1, numToSkip + 1).foreach(x =>
      if (hasNext) {
        nextPage
      }
    )
    logPagesElapsed
  }

  def nextPage: String;

}
