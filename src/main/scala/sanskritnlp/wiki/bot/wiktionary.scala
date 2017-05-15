package sanskritnlp.wiki.bot

import net.sourceforge.jwbf.core.contentRep.SimpleArticle
import org.slf4j.LoggerFactory
import sanskritnlp.dictionary.{BabylonDictionary, babylonTools}
import sanskritnlp.wiki.Section

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

trait wiktionary extends wikiBot {
  override val log = LoggerFactory.getLogger(this.getClass)
  override val wikiSiteName = "wiktionary"

  def replaceBadText(headwords: Array[String], regexMap: Map[String, String]) = {
    log.info(headwords.mkString(","))
    headwords.foreach(head => {
      replaceRegex(getArticle(head), regexMap)
    })
  }

  def addDictionaryMeaning(head: String, dictionary: BabylonDictionary) = {
    val dict_source = dictionary.source
    log.info(s"Adding $head")
    val (sectionPath, text) = getWikiEntry(dictionary, head)
    replaceSectionText(article = getArticle(head), sectionPath = sectionPath, summary = "अर्थनिवेशः", text = text)
  }

  // Used dictionaryUsed's name instead of dictionary's name?
  def fixDictNameMixup(dictionary: BabylonDictionary, dictionaryUsed: BabylonDictionary, headword_pattern: String = "\\p{IsDevanagari}+") = {
    dictionaryUsed.makeWordToLocationMap(headword_pattern)
    dictionary.makeWordToLocationMap(headword_pattern)
    dictionary.getWords.foreach(word => {
      addDictionaryMeaning(word, dictionary)
      deleteSection(article = getArticle(word), getSectionPath(dictionaryUsed))
      val meanings: ListBuffer[String] = dictionaryUsed.getMeanings(word)
      // log info meanings.mkString(", ")
      if ( meanings != null && meanings.size > 0) {
        log info s"found $word in ${dictionaryUsed.dict_name}"
        addDictionaryMeaning(word, dictionaryUsed)
      }
    })
  }

  def fixWikiError(dictList: List[BabylonDictionary], start_index: Int = 1, end_index: Int = -1, headword_pattern: String) = {
    val wordToDicts = babylonTools.mapWordToDicts(dictList, headword_pattern)
    var word_index = start_index - 1
    // use drop to skip n items.
    wordToDicts.keys.toList.sorted.drop(word_index).take(end_index - start_index + 1).foreach(word => {
      word_index = word_index + 1
      deleteSection(article = getArticle(word), sectionPath = s"/प्रकाशितकोशों से अर्थ/")
    })
  }

  def setWordMeanings(word: String, wordToDicts: mutable.HashMap[String, ListBuffer[BabylonDictionary]]) = {
    val article = getArticle(word)
    replaceRegex(article, Map({"=हिन्दी=" -> "={{हिन्दी}}="}))
    val articleSection = new Section(article)
    val dictionaries = wordToDicts.getOrElse(word, null)
    dictionaries.foreach(dictionary => {
      val (sectionPath, text) = getWikiEntry(dictionary, word)
      val section = articleSection.getOrCreateSection(sectionPath)
      section.headText = text
    })
    // log info articleSection.toString()
    editArticle(article = article, text = articleSection.toString, summary = s"(re)add ${dictionaries.mkString(", ")}")
  }

  def uploadFromBabylonDictsCombined(dictList: List[BabylonDictionary], start_index: Int = 1, end_index: Int = 1000000, headword_pattern: String) = {
    val wordToDicts = babylonTools.mapWordToDicts(dictList, headword_pattern)
    var word_index = start_index - 1
    // use drop to skip n items.
    wordToDicts.keys.toList.sorted.drop(word_index).take(end_index - start_index + 1).foreach(word => {
      word_index = word_index + 1
      log info s"$word (index: $word_index of $end_index >= ${wordToDicts.size}) is present in " +
        s"${wordToDicts.getOrElse(word, ListBuffer[BabylonDictionary]()).map(_.dict_name).mkString(", ")}"
      setWordMeanings(word, wordToDicts)
    })
  }

  def getWikiEntry(dictionary: BabylonDictionary, word: String): (String, String) = null
  def getSectionPath(dictionary: BabylonDictionary): (String) = null
}


object sa_wiktionary extends wiktionary {
  override val languageCode = "sa"
  override val log = LoggerFactory.getLogger(this.getClass)

  /*
  In sa-head:
  val kalpadruma = new BabylonDictionary(name_in = "कल्पद्रुमः", source_in = "http://www.sanskrit-lexicon.uni-koeln.de/scans/csldoc/contrib/index.html", head_language= "sa")
  kalpadruma.fromFile(infileStr = "/home/vvasuki/stardict-sanskrit/sa-head/kalpadruma-sa/kalpadruma-sa.babylon_final")
  val amara = new BabylonDictionary(name_in = "अमरकोशः", source_in = "http://github.com/sanskrit-coders/stardict-sanskrit/tree/master/sa-head/amara-onto", head_language= "sa")
  amara.fromFile(infileStr = "/home/vvasuki/stardict-sanskrit/sa-head/amara-onto/amara-onto.babylon_final")
  val vAcas = new BabylonDictionary(name_in = "वाचस्पत्यम्", source_in = "http://www.sanskrit-lexicon.uni-koeln.de/scans/csldoc/contrib/index.html", head_language= "sa")
  vAcas.fromFile(infileStr = "/home/vvasuki/stardict-sanskrit/sa-head/vAchaspatyam-sa/vAchaspatyam-sa.babylon_final")
  val mw = new BabylonDictionary(name_in = "Monier-Williams", source_in = "http://www.sanskrit-lexicon.uni-koeln.de/scans/csldoc/contrib/index.html", head_language= "sa")
  mw.fromFile(infileStr = "/home/vvasuki/stardict-sanskrit/sa-head/mw-sa/mw-sa.babylon_final")
  val apte = new BabylonDictionary(name_in = "Apte", source_in = "http://www.sanskrit-lexicon.uni-koeln.de/scans/csldoc/contrib/index.html", head_language= "sa")
  apte.fromFile(infileStr = "/home/vvasuki/stardict-sanskrit/sa-head/apte-sa/apte-sa.babylon_final")
  val shabdasAgara = new BabylonDictionary(name_in = "शब्दसागरः", source_in = "http://www.sanskrit-lexicon.uni-koeln.de/scans/csldoc/contrib/index.html", head_language= "sa")
  shabdasAgara.fromFile(infileStr = "/home/vvasuki/stardict-sanskrit/sa-head/shabda-sAgara/shabda-sAgara.babylon_final")
  val AkhyAtachandrikA = new BabylonDictionary(name_in = "आख्यातचन्द्रिका", source_in = "http://github.com/sanskrit-coders/stardict-sanskrit/tree/master/sa-head/AkhyAtachandrikA", head_language= "sa")
  AkhyAtachandrikA.fromFile(infileStr = "/home/vvasuki/stardict-sanskrit/sa-sanskritnlp.vyAkaraNa.vyAkaraNa/AkhyAtachandrikA/AkhyAtachandrikA.babylon_final")

  These don't yet exist fully:
  dcs frequency
  kRdanta-rUpa-mAlA

  In sa-kAvya:
  val purANa_index = new BabylonDictionary(name_in = "Purana index", source_in = "http://www.sanskrit-lexicon.uni-koeln.de/scans/csldoc/contrib/indtml", head_language= "sa")
  purANa_index.fromFile(infileStr = "/home/vvasuki/stardict-sanskrit/sa-kAvya/purANa-index-dev/purANa-index-dev.babylon_final")
  val purANa_encyclopedia = new BabylonDictionary(name_in = "Purana Encyclopedia", source_in = "http://www.sanskrit-lexicon.uni-koeln.de/scans/csldoc/contrib/index.html", head_language= "sa")
  purANa_encyclopedia.fromFile(infileStr = "/home/vvasuki/stardict-sanskrit/sa-kAvya/purANa-encyclopedia/PuraNa_encyclopedia.babylon")
  val mahabharata_cultural_index = new BabylonDictionary(name_in = "Mahabharata Cultural Index", source_in = "http://www.sanskrit-lexicon.uni-koeln.de/scans/csldoc/contrib/index.html", head_language= "sa")
  mahabharata_cultural_index.fromFile(infileStr = "/home/vvasuki/stardict-sanskrit/sa-kAvya/mahabharata_cultural_index/mahabharata_cultural_index.babylon")
  val vedic_index = new BabylonDictionary(name_in = "Vedic Index of Names and Subjects", source_in = "http://www.sanskrit-lexicon.uni-koeln.de/scans/csldoc/contrib/index.html", head_language= "sa")
  vedic_index.fromFile(infileStr = "/home/vvasuki/stardict-sanskrit/sa-kAvya/vedic_index/vedic_index.wiki")

  To upload:
  Chandas
  rv-padasvara

  These don't yet exist fully:
  mbh name index

  In sa-sanskritnlp.vyAkaraNa.vyAkaraNa:
  To upload:
  Declensions
  Conjugations
  kShIratarangiNI
  mAdhavIya-vRtti
  dhAtupradIpa
  jnu-tiNanta

  These don't yet exist fully:
  abhyankara grammar
  dhaval-tiNanta
  dhAturUpamAlA
  bRhad-dhAtu-kusumAkara
  */

  val vedic_rituals = new BabylonDictionary(name_in = "Vedic Rituals Hindi", source_in = "http://github.com/sanskrit-coders/stardict-sanskrit/tree/master/sa-head/vedic-rituals-hi", head_language= "sa")
  vedic_rituals.fromFile(infileStr = "/home/vvasuki/stardict-sanskrit//sa-head/vedic-rituals-hi/vedic-rituals-hi.babylon")

  /*
  val computer_shrIkAnta = new BabylonDictionary(name_in = "आङ्ग्लसंस्कृतसङ्गणककोशः श्रीकान्तस्य", source_in = "http://github.com/sanskrit-coders/stardict-sanskrit/tree/master/en-head/computer-shrIkAnta", head_language= "en")
  computer_shrIkAnta.fromFile(infileStr = "/home/vvasuki/stardict-sanskrit/en-head/computer-shrIkAnta/computer-shrIkAnta.babylon")

  These don't yet exist:
  val mw_eng = new BabylonDictionary(name_in = "Monier Williams (en-sa)", source_in = "http://www.sanskrit-lexicon.uni-koeln.de/scans/csldoc/contrib/index.html", head_language= "en")
  mw_eng.fromFile(infileStr = "/home/vvasuki/stardict-sanskrit/en-head/mw-bi-itrans-dev/mw-bi-itrans-dev.babylon_final")
  val apte_eng = new BabylonDictionary(name_in = "Apte (en-sa)", source_in = "http://github.com/sanskrit-coders/stardict-sanskrit/tree/master/sa-head/AkhyAtachandrikA", head_language= "sa")
  apte_eng.fromFile(infileStr = "/home/vvasuki/stardict-sanskrit/en-head/apte-bi/apte-bi.babylon_final")
  */


  override def getSectionPath(dictionary: BabylonDictionary): (String) = s"/यन्त्रोपारोपितकोशांशः/${dictionary.dict_name}"
  override def getWikiEntry(dictionary: BabylonDictionary, word: String): (String, String) = {
    val head_text = s"{{फलकम्:यन्त्रशोधितकोशार्थः|कोशमूलम् = ${dictionary.source}}}"
    val sectionPath = getSectionPath(dictionary)
    val category_name = sectionPath.split('/').filterNot(_ == "").mkString("-")
    var tail_text = s"[[वर्गः: $category_name]]"
    if (List("आङ्ग्लसंस्कृतसङ्गणककोशः श्रीकान्तस्य").contains(dictionary.dict_name)) {
      tail_text = tail_text + "[[वर्गः:आङ्गलपदानि]]"
    } else {
      tail_text = tail_text + "[[वर्गः:संस्कतशब्दाः]]"
    }
    val meanings = dictionary.getMeanings(word).mkString("\n\n")
    val text = s"$head_text\n\n$meanings\n\n$tail_text".replaceAll("\\{@|@\\}", "'''")
    return (sectionPath, text)
  }

  def main(args: Array[String]): Unit = {
    passwd = ""
    login
    // uploadFromBabylonDictsCombined(dictList = List(computer_shrIkAnta), headword_pattern = "[a-z]+")
    uploadFromBabylonDictsCombined(dictList = List(vedic_rituals), headword_pattern = "\\p{IsDevanagari}+")
    // fixDictNameMixup(dictionary = AkhyAtachandrikA, dictionaryUsed = shabdasAgara, headword_pattern = "\\p{IsDevanagari}+")
  }
}

object hi_wiktionary extends wiktionary {
  override val languageCode = "hi"
  override val log = LoggerFactory.getLogger(this.getClass)

  val shabdasAgara = new BabylonDictionary(name_in = "शब्दसागर", head_language= "hi")
  shabdasAgara.fromFile(infileStr = "/home/vvasuki/stardict-hindi/hi-head/hi-shabdasAgar/hi-shabdasagar.babylon_final")


  override def getSectionPath(dictionary: BabylonDictionary): (String) = s"/{{हिन्दी}}/प्रकाशितकोशों से अर्थ/${dictionary.dict_name}"
  override def getWikiEntry(dictionary: BabylonDictionary, word: String): (String, String) = {
    val sectionPath = getSectionPath(dictionary)
    val category_name = sectionPath.split('/').filterNot(_ == "").mkString("-").replaceAll("[{}]", "")
    val tail_text = s"[[श्रेणी: $category_name]]"
    val meanings = dictionary.getMeanings(word).mkString("\n\n")
    val text = s"$meanings\n\n$tail_text".replaceAll("\\{@|@\\}", "'''")
    return (sectionPath, text)
  }

  def main(args: Array[String]): Unit = {
    passwd = ""
    login
    // fixWikiError(dictList = List(shabdasAgara), headword_pattern = "\\p{IsDevanagari}+", end_index = 5)
    uploadFromBabylonDictsCombined(dictList = List(shabdasAgara), start_index=142149, headword_pattern = "\\p{IsDevanagari}+")
    // setWordMeanings(word="आ", mapWordToDicts(dictList = List(shabdasAgara), headword_pattern = "\\p{IsDevanagari}+"))
    // fixDictNameMixup(dictionary = AkhyAtachandrikA, dictionaryUsed = shabdasAgara, headword_pattern = "\\p{IsDevanagari}+")

  }
}