package sanskritnlp.wiki

// To new users: See sectionTest at the bottom for usage example, and all should be clear.

import net.sourceforge.jwbf.core.contentRep.SimpleArticle
import org.slf4j.LoggerFactory

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

class Section {
  val log = LoggerFactory.getLogger(this.getClass)

  // Use SetAttributes instead of setting these directly.
  var title = ""
  var levelText = ""

  var headText = ""
  var subSections = ListBuffer[Section]()

  def setAttributes(titleIn: String, levelTextIn: String, headTextIn: String = "") = {
    title = titleIn
    levelText = levelTextIn
  }

  @tailrec final def getParentForSection(section: Section): Section = {
    if (subSections.size == 0 || section.levelText.length <= subSections.last.levelText.length) {
      if (section.levelText.size > levelText.size) {
        return this
      } else {
        return null
      }
    } else {
      return subSections.last.getParentForSection(section)
    }
  }

  def addSection(currentSection: Section) = {
    assert(currentSection != this)
    val parentSection = getParentForSection(currentSection)
    if (parentSection == null) {
      log info (currentSection.toString)
      log error("Cant find parent!")
    }
    parentSection.subSections += currentSection
    // log info ("added section: " + currentSection)
  }

  override def toString: String = {
    var text = ""
    if(levelText.length > 0) {
      text = s"$levelText $title $levelText\n"
    }
    val subSectionText = subSections.map(_.toString).mkString("\n")
    text = s"$text$headText\n$subSectionText"
    return text.replaceAll("\\n\\n+", "\n\n")
  }

  // Stops when a higher level section is encountered.
  // Returns number of lines consumed.
  def parse(titleIn: String = "", levelTextIn: String = "", lines: Array[String]): Unit = {
    setAttributes(titleIn = titleIn, levelTextIn = levelTextIn)
    headText = ""

    /*
    A valid Mediawiki section is a new line of the following form: =+[^=]+=+\s+.
    Note: there can't be space in the beginning, nor non space characters in the end.
    A malformed section has unequal prefix and suffix =-strings - in that case, Mediawiki simply uses the least number of equals to make sense of it.
     */
    val sectionPattern = "(=+)([^=]+)(=+)\\s*".r
    var currentSection = this
    lines.foreach(line =>
      line match {
        case sectionPattern(levelTextPre, titleMatch, levelTextSuffix) => {
          // log info ("found section line!")
          if (currentSection != this) {
            addSection(currentSection)
          }

          currentSection = new Section
          if (levelTextPre.length <= levelTextSuffix.length) {
            currentSection.levelText = levelTextPre
          } else if (levelTextPre.length > levelTextSuffix.length) {
            currentSection.levelText = levelTextSuffix
          }
          currentSection.title = titleMatch.trim
          if (currentSection.levelText.length <= levelText.length) {
            return
          }
        }
        case _ => {
          currentSection.headText = currentSection.headText + s"$line\n"
          // log info ("added line: " + line)
        }
      }
    )
    if (currentSection != this) {
      addSection(currentSection)
    }
    return
  }

  def this(article: SimpleArticle) = {
    this()
    parse(lines = article.getText.split("\n"))
  }

  // returns true if a section was found and deleted.
  def deleteSection(sectionPath: String): Boolean = {
    val sectionList = sectionPath.split("/").filter(_ != "")
    assert(sectionList.length > 0)
    val headSectionList = subSections.filter(_.title == sectionList.head)
    return headSectionList.map(headSection => {
      if (sectionList.length > 1) {
        return headSection.deleteSection(sectionList.tail.mkString("/"))
      } else {
        subSections = subSections.filterNot(headSectionList contains _)
        return !headSectionList.isEmpty
      }
    }).fold(false)((a, b) => a|b)
  }

  def getOrCreateSection(sectionPath: String, defaultSection: Section = new Section): Section = {
    val sectionList = sectionPath.split("/").filter(_ != "")
    assert(sectionList.length > 0)
    val headSectionList = subSections.filter(_.title == sectionList.head)
    var headSection: Section = null
    if (headSectionList.length > 0) {
      headSection = headSectionList.head
    } else {
      headSection = defaultSection
      headSection.setAttributes(titleIn = sectionList.head, levelTextIn = levelText + "=")
      subSections += headSection
    }
    if (sectionList.tail.length > 0) {
      return headSection.getOrCreateSection(sectionList.tail.mkString("/"))
    } else {
      return headSection
    }
  }
}

object sectionTest {
  val log = LoggerFactory.getLogger(this.getClass)
  def main(args: Array[String]) {
    val wikiText =
      """
        |परीक्षार्थं सृष्टमिदम्।
        |== विभागः १ ==
        |असदफ़
        |nn== संस्कृतम् ==nn=== अक्षरम् ===
        |
        |=== उपविभागः १ ===
        |
        |आषफ़द
        |असदफ़सद
        |
        |== विभागः २ ==
        |असदफ़
        |
        |= विभागः ३ =
        |असदफ़
        |
        |=== उपविभागः ३.१ ===
        |आषफ़द
        |===== उपविभागः ३.2 ===
        |आषफ़द
        |""".stripMargin
    val article = new Section()
    article.parse(lines = wikiText.split("\n"))
    log.info (article.toString)

    val section = article.getOrCreateSection("/विभागः २/विभागः २.1/विभागः २.1.1")
    section.headText = "In " + section.title
    log.info (article.toString)

    assert(article.deleteSection(sectionPath = "/विभागः २/विभागः २.1/विभागः २.1.1"))
    assert(!article.deleteSection(sectionPath = "/विभागः २/विभागः २.1/विभागः २.1.234"))
    log.info (article.toString)
  }
}