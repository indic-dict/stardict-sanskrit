package sanskritnlp.dictionary

/*
Run this:
PATH_TO_SANSKRITNLPJAVA=/home/vvasuki/sanskritnlpjava/target
scala -classpath "$PATH_TO_SANSKRITNLPJAVA/sanskritnlp-1.0-SNAPSHOT/WEB-INF/lib/*:$PATH_TO_SANSKRITNLPJAVA/sanskritnlp-1.0-SNAPSHOT/WEB-INF/classes" sanskritnlp.transliteration.dictTools.sutraNumbersToDevanagari  nyasa/nyasa.babylon
 */*/

import java.io._

import org.slf4j.LoggerFactory
import sanskritnlp.transliteration._
import sanskritnlp.vyAkaraNa.devanAgarI

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.io.Source

/**
  * Created by vvasuki on 2/20/16.
  */
object babylonTools {

  val log = LoggerFactory.getLogger("babylonTools")
  def sutraNumbersToDevanagari(infileStr: String): Unit = {
    log info("Processing " + infileStr)
    val outfileStr = infileStr.replaceFirst(".babylon$", ".babylon_dev_sutra")
    val src = Source.fromFile(infileStr, "utf8")
    val outFileObj = new File(outfileStr)
    new File(outFileObj.getParent).mkdirs
    val destination = new PrintWriter(outFileObj)

    val suutraPattern = """(\d+\.\d+\.\d+)""".r
    src.getLines.foreach(line => {
      var newLine = suutraPattern.replaceAllIn(line, _ match {
        case suutraPattern(latin_str) => optitrans.toDevanagari(latin_str).get.replaceAll("।", ".")})
      destination.println(newLine)
      // println(line)
      // println(newLine)
    })
    destination.close()
    log info("Produced " + outfileStr)
  }

  def mapWordToDicts(dictList: Seq[BabylonDictionary], headword_pattern: String):  mutable.HashMap[String, ListBuffer[BabylonDictionary]] = {
    val wordToDicts = new mutable.HashMap[String, ListBuffer[BabylonDictionary]]()
    dictList.foreach(dictionary => {
      // dictionary.makeWordToLocationMap(headword_pattern = "\\p{IsDevanagari}+")
      dictionary.makeWordToMeaningsMap(headword_pattern)
      dictionary.getWords.foreach(word => {
        var dictList = wordToDicts.getOrElse(word, ListBuffer[BabylonDictionary]())
        dictList += dictionary
        wordToDicts += (word -> dictList)
      })
    })
    return wordToDicts
  }


  def fixHeadwords(infileStr: String, outputExt: String, headwordTransformer: (Array[String]) => Array[String]): Unit = {
    log info("Processing " + infileStr)
    val outfileStr = infileStr.replaceFirst("\\.[^.]+$", outputExt)
    log info("Will produce " + outfileStr)
    val outFileObj = new File(outfileStr)
    new File(outFileObj.getParent).mkdirs
    val destination = new PrintWriter(outFileObj)

    def headwordSorter(a: String, b: String): Boolean = {
      def checkDistinctProperty(fn: String=> Boolean): Boolean = fn(a) && !fn(b)
      def getScore(x: String): Float = {
        var score = x.length
        if(x.contains("_")) score += -1000
        if(devanAgarI.isEncoding(x)) score += 1000
        if(kannaDa.isEncoding(x)) score += 70
        if(telugu.isEncoding(x)) score += 69
        return score
      }
      if (getScore(a) == getScore(b)) {
        return a < b
      } else {
        return getScore(a) > getScore(b)
      }
    }

    def isHeadLine(x:String) = x.startsWith("#") || x.trim.isEmpty
    var src = Source.fromFile(infileStr, "utf8")
    src.getLines.takeWhile(isHeadLine(_)).foreach(destination.println)

    src = Source.fromFile(infileStr, "utf8")
    src.getLines.dropWhile(isHeadLine(_)).zipWithIndex.foreach( t => {
      val line = t._1
      val index = t._2
      try {
        if(index % 3 == 0) {
          val headwordsOriginal = line.split('|')
          val headwordsFixed = headwordTransformer(headwordsOriginal)
          // Sorting with sortwith is risky - can fail and produce no output line. Skipping that.
          destination.println(headwordsFixed.toSet.toList.sortWith(headwordSorter
          ).mkString("|"))
        } else {
          destination.println(line)
        }
      } catch {
        case ex: Exception => {
          log error ex.toString
          log error "line: " + t.toString()
        }
      }
    })
    destination.close()
    log info("Produced " + outfileStr)
  }

  def fixEntries(infileStr: String, outputExt: String, entryTransformer: String => String): Unit = {
    log info("Processing " + infileStr)
    val outfileStr = infileStr.replaceFirst("\\.[^.]+$", outputExt)
    log info("Will produce " + outfileStr)
    val outFileObj = new File(outfileStr)
    new File(outFileObj.getParent).mkdirs
    val destination = new PrintWriter(outFileObj)


    def isHeadLine(x:String) = x.startsWith("#") || x.trim.isEmpty
    var src = Source.fromFile(infileStr, "utf8")
    src.getLines.takeWhile(isHeadLine(_)).foreach(destination.println)

    src = Source.fromFile(infileStr, "utf8")
    src.getLines.dropWhile(isHeadLine(_)).zipWithIndex.foreach( t => {
      val line = t._1
      val index = t._2
      try {
        if((index + 1) % 2 == 0) {
          val entryOriginal = line
          val entryTransliterated = entryTransformer(entryOriginal)
          destination.println(entryTransliterated.toSet.toList.sorted.mkString("|"))
        } else {
          destination.println(line)
        }
      } catch {
        case ex: Exception => {
          log error ex.toString
          log error "line: " + t.toString()
        }
      }
    })
    destination.close()
    log info("Produced " + outfileStr)
  }

  def asToDevanagari(infileStr: String): Unit = {
    log info("Processing " + infileStr)
    val outfileStr = infileStr.replaceFirst("\\.babylon$", ".babylonv1")
    val src = Source.fromFile(infileStr, "utf8")
    val outFileObj = new File(outfileStr)
    new File(outFileObj.getParent).mkdirs
    val destination = new PrintWriter(outFileObj)

    val asPattern = """\{%(.+?)%\}""".r
    val asPatternUnmarked = """(\W)(\w+?\d\w*?)(\W)""".r
    src.getLines.foreach(line => {
      var newLine = asPattern.replaceAllIn(line, _ match {
        case asPattern(as_str) => as.toDevanagari(as_str).get})
      newLine = asPatternUnmarked.replaceAllIn(newLine, _ match {
        case asPatternUnmarked(fore, as_str, aft) => fore + as.toDevanagari(as_str) + aft })
      destination.println(newLine)
      // log info(line)
      log info(newLine)
    })
    destination.close()
    log info("Produced " + outfileStr)
  }

}
