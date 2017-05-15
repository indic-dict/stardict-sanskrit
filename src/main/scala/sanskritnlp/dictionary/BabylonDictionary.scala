package sanskritnlp.dictionary

import java.io.{File, PrintWriter}

import org.slf4j.LoggerFactory

import scala.collection.mutable.ListBuffer
import scala.collection.immutable.HashMap
import scala.collection.mutable
import scala.io.{BufferedSource, Source}

class BabylonDictionary(name_in: String, source_in: String = "", head_language: String) {
  var wordToLocations: HashMap[String, ListBuffer[Int]] = new HashMap[String, ListBuffer[Int]]
  var wordToMeanings = new HashMap[String, ListBuffer[String]]
  val log = LoggerFactory.getLogger(this.getClass)

  val dict_name = name_in
  val source = source_in

  var words_taken = 0

  var fileLocation = ""
  var linesIter: Iterator[String] = null
  var src: Source = null

  def fromFile(infileStr: String) = {
    // log info s"Reading $infileStr for $dict_name"
    fileLocation = infileStr
    words_taken = 0
    src = Source.fromFile(infileStr, "utf8")
    def isHeadLine(x:String) = x.startsWith("#") || x.trim.isEmpty
    linesIter = src.getLines.dropWhile(isHeadLine)
  }

  def hasNext(): Boolean = {
    return linesIter.hasNext
  }

  //  java.nio.charset.MalformedInputException: Input length = 1 implies bad character in file.
  def next(): (Array[String], String) = {
    words_taken = words_taken + 1
    val headwords = linesIter.next().split('|')
//    log debug(headwords.mkString("|"))
//    log debug(linesIter.hasNext.toString)
    val entry = linesIter.next
//    log debug(entry)
    val returnTuple = (headwords, entry)
    assert(!linesIter.hasNext || linesIter.next() == "")
    return returnTuple
  }

  def take(entriesToSkip: Int) = {
    while(hasNext() && words_taken < entriesToSkip) {
      next()
    }
  }

  def makeWordToLocationMap(headword_pattern: String = ".+") = {
    log info s"Making wordToLocationMap for $dict_name"
    fromFile(fileLocation)
    while (hasNext()) {
      val (headwords, meaning) = next()
      // log.info(s"word_index : $word_index")
      val filtered_headwords = headwords.filter(_ matches headword_pattern)
      filtered_headwords.foreach(word => {
        val locus_list = wordToLocations.getOrElse(word, ListBuffer[Int]())
        locus_list += words_taken
        wordToLocations += (word -> locus_list)
      })
    }
    fromFile(fileLocation)
  }

  def getMeaningAtIndex(locus: Int): String = {
//    log info(s"locus: $locus")
//    log info(s"words_taken: $words_taken")
    fromFile(fileLocation)
    take(locus - 1)
    val (_, meaning_line) = next()
    return meaning_line
  }

  def getMeanings(word: String): ListBuffer[String] = {
    if (wordToMeanings.size == 0) {
      if (wordToLocations.size == 0) {
        makeWordToLocationMap()
      }
      val definition_locus_list = wordToLocations.getOrElse(word, ListBuffer[Int]())
      return definition_locus_list.map(getMeaningAtIndex(_))
    } else {
      return wordToMeanings.getOrElse(word, null)
    }
  }

  // Assumes that you've called makeWordToMeaningsMap.
  def getWords: List[String] = {
    if (wordToMeanings.size == 0) {
      return wordToLocations.keys.toList.sorted
    } else {
      return wordToMeanings.keys.toList.sorted
    }

  }

  def makeWordToMeaningsMap(headword_pattern: String = ".+") = {
    log info s"Making wordToMeanings for $dict_name"
    while (hasNext()) {
      val (headwords, meaning) = next()
      // log.info(s"word_index : $word_index")
      val filtered_headwords = headwords.filter(_ matches headword_pattern)
      filtered_headwords.foreach(word => {
        val meaningList = wordToMeanings.getOrElse(word, ListBuffer[String]())
        meaningList += meaning
        wordToMeanings += (word -> meaningList)
      })
    }
  }

}

object babylonDictTest {
  val log = LoggerFactory.getLogger(this.getClass)

  def kalpadruma_test: Unit = {
    val kalpadruma = new BabylonDictionary(name_in = "कल्पद्रुमः", source_in = "http://www.sanskrit-lexicon.uni-koeln.de/scans/csldoc/contrib/index.html", head_language = "sa")
    kalpadruma.fromFile(infileStr = "/home/vvasuki/stardict-sanskrit/sa-head/kalpadruma-sa/kalpadruma-sa.babylon_final")
    log info kalpadruma.getMeanings("इ").mkString("\n\n")
    log info kalpadruma.getMeanings("अ").mkString("\n\n")
    log info kalpadruma.getMeanings("उ").mkString("\n\n")
    log info kalpadruma.getMeanings("इ").mkString("\n\n")
    log info kalpadruma.getMeanings("अ").mkString("\n\n")
  }
  def main(args: Array[String]) {

  }
}