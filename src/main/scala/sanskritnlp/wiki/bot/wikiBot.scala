package sanskritnlp.wiki.bot

import net.sourceforge.jwbf.core.contentRep.SimpleArticle
import net.sourceforge.jwbf.mediawiki.actions.util.ApiException
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot
import org.slf4j.LoggerFactory
import sanskritnlp.app.sanskritNlp
import sanskritnlp.wiki.Section

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.io.StdIn
import scala.util.matching.Regex


trait wikiBot {
  val log = LoggerFactory.getLogger(this.getClass)
  val languageCode: String = null
  val wikiSiteName: String = null
  var bot: MediaWikiBot = null

  val userName = sanskritNlp.props.getProperty("WIKI_USER_NAME")
  var passwd = ""
  def getSandboxPage: String = s"$wikiSiteName:Sandbox"

  // Bot policy (differs in different wiki sites): https://en.wikipedia.org/wiki/Wikipedia:Bot_policy
  // see https://www.mediawiki.org/wiki/Manual:$wgRateLimits
  // But 60/8 results in rate limiting without botflag.
  // Observed edit rate with bot flag: 5 per second.
  val minGapBetweenEditsSec: Int = (math.ceil(60/720)).toInt

  def login = {
    bot = new MediaWikiBot(s"https://$languageCode.$wikiSiteName.org/w/")
    // न भेतव्यम् इति केन दीक्षितेनोक्तम्?
    if (passwd.isEmpty) {
      log info "Enter password"
      passwd = StdIn.readLine().trim
    }
    log info userName + ":" + passwd
    bot.login(userName, passwd)
  }

  var prevEditTime = System.currentTimeMillis / 1000
  @tailrec final def editArticle(article: SimpleArticle, text: String, summary: String, isMinor: Boolean = false, num_retries: Int = 3): Unit = {
    try{
      article.setText(text)
      article.setEditSummary(summary)

      // Deal with timeouts
      var nowTime = System.currentTimeMillis / 1000
      if (nowTime - prevEditTime < minGapBetweenEditsSec) {
        log info s"sleeping for $minGapBetweenEditsSec secs"
        Thread.sleep(minGapBetweenEditsSec * 1000)
      }

      prevEditTime = nowTime
      // Finally do the edit.
      bot writeContent article
      // log info article.getText()
    } catch {
      // To deal with java.lang.IllegalStateException: invalid status: HTTP/1.1 503 Service Unavailable.
      case e: IllegalStateException => {
        log.warn(e.getMessage)
        if (num_retries > 0) {
          editArticle(article = article, text = text, summary = summary, isMinor = isMinor, num_retries = num_retries - 1)
        }
      }
      // To deal with: net.sourceforge.jwbf.mediawiki.actions.util.ApiException: API ERROR CODE: badtoken VALUE: Invalid token
      case e: ApiException => {
        log.warn(e.getMessage)
        login
        if (num_retries > 0) {
          editArticle(article = article, text = text, summary = summary, isMinor = isMinor, num_retries = num_retries - 1)
        }
      }
    }
  }

  final def edit(title: String, text: String, summary: String, isMinor: Boolean = false): Unit = {
    val article = getArticle(title)
    editArticle(article = article, text = text, summary = summary, isMinor = isMinor)
  }

  @tailrec final def getArticle(title:String, lstTitlesVisited: ListBuffer[String] = ListBuffer()): SimpleArticle = {
    val article = bot.readData(title)
    log.info("Getting " + title)
    lstTitlesVisited += title
    val redirectPattern = "#[rR][eE][dD][iI][rR][eE][cC][tT]\\s+\\[\\[(.+?)\\]\\].*".r
    article.getText.trim match {
      case redirectPattern(newTitle) => {
        log.info("redirected to " + newTitle)
        if (lstTitlesVisited.contains(newTitle)) {
          log.error("cyclically redirected to " + newTitle)
          return null
        } else {
          return getArticle(newTitle, lstTitlesVisited)
        }
      }
      case _ => {
        return article
      }
    }
  }

  private def getArticleSection(title: String): (SimpleArticle, Section) = {
    val article = getArticle(title)
    val articleSection = new Section(article)
    (article, articleSection)
  }

  def replaceSectionText(article: SimpleArticle, sectionPath: String, text: String, summary: String, bAppend: Boolean = true, isMinor: Boolean = false) = {
    val articleSection = new Section(article)
    val section = articleSection.getOrCreateSection(sectionPath)
    section.headText = text
    editArticle(article = article, text = articleSection.toString, summary = summary, isMinor = isMinor)
  }

  def replaceRegex(article: SimpleArticle, regexMap: Map[String, String]) = {
    var replacementText = article.getText
    regexMap.keys.foreach(regex => {
      replacementText = replacementText.replaceAll(regex, regexMap(regex))
    })
    article.setText(replacementText)
  }

  def appendToSection(article: SimpleArticle, sectionPath: String, text: String, summary: String, bAppend: Boolean = true, isMinor: Boolean = false) = {
    val articleSection = new Section(article)

    val section = articleSection.getOrCreateSection(sectionPath)
    section.headText += text
    editArticle(article = article, text = articleSection.toString, summary = summary, isMinor = isMinor)
  }

  def deleteSection(article: SimpleArticle, sectionPath: String) = {
    val articleSection = new Section(article)

    if (articleSection.deleteSection(sectionPath)) {
      var textPostDeletion = articleSection.toString
      if (textPostDeletion matches "\\s*") {
        textPostDeletion = "[सद्यः पूरयिष्यते]"
      }
      editArticle(article = article, text = textPostDeletion, summary = s"अस्य भागस्य निष्कासनम् - $sectionPath")
    }
  }

  def testEditSection() = {
    replaceSectionText(article = getArticle(getSandboxPage + "2"), sectionPath = "/परीक्षाविभागः", text = "नूतनपाठः2", summary = "परीक्षाविभागयोगः")
  }

  def test() = {
    //Javadoc here: http://jwbf.sourceforge.net/doc/
    val article = bot.readData(getSandboxPage)
    log info "|" + article.getText() + "|"
    // bot.delete("Wiktionary:Sandbox")
  }

}
