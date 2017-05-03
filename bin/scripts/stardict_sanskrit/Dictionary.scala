package stardict_sanskrit

import java.io.{File, PrintWriter}

import org.slf4j.LoggerFactory
import sanskritnlp.dictionary.{BabylonDictionary, babylonTools}
import sanskritnlp.transliteration.{iast, transliterator}
import sanskritnlp.vyAkaraNa.devanAgarI

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.sys.process._

class Dictionary(val name: String) {
  val log = LoggerFactory.getLogger(getClass.getName)
  var dirName: String = null
  var dirFile: File = null
  var babylonFile: Option[File] = None
  var babylonFinalFile: Option[File] = None
  var ifoFile: Option[File] = None
  var tarFile: Option[File] = None
  var dictFile: Option[File] = None
  var dictdzFile: Option[File] = None

  def this(dirFileIn: java.io.File ) = {
    this(dirFileIn.getName)
    dirFile = dirFileIn
    dirName = dirFile.getName.replaceAll(".*/", "")
    babylonFile = dirFile.listFiles.map(_.getCanonicalFile).filter(_.getName.matches(s".*/?${dirName}.babylon")).headOption
    babylonFinalFile = dirFile.listFiles.map(_.getCanonicalFile).filter(_.getName.matches(s".*/?${dirName}.babylon_final")).headOption

    if (getTarDirFile.exists) {
      tarFile = getTarDirFile.listFiles.map(_.getCanonicalFile).filter(_.getName.matches(s".*/?${dirName}.*.tar.gz")).headOption
    }
    ifoFile = dirFile.listFiles.map(_.getCanonicalFile).filter(_.getName.matches(s".*/?${dirName}.ifo")).headOption
    dictFile = dirFile.listFiles.map(_.getCanonicalFile).filter(_.getName.matches(s".*/?${dirName}.dict")).headOption
    dictdzFile = dirFile.listFiles.map(_.getCanonicalFile).filter(_.getName.matches(s".*/?${dirName}.dict.dz")).headOption
    log debug toString
  }


  def babylonFinalFileNewerThanBabylon(): Boolean = {
    babylonFinalFile.isDefined && (babylonFinalFile.get.lastModified > babylonFile.get.lastModified)
  }

  def tarFileNewerThanIfo(): Boolean = {
    tarFile.isDefined && (tarFile.get.lastModified > ifoFile.get.lastModified)
  }


  def getFinalBabylonFile(): File = {
    if (babylonFinalFile.isDefined) {
      babylonFinalFile.get
    } else if (babylonFile.isDefined) {
      babylonFile.get
    } else {
      null
    }
  }

  def ifoFileNewerThanBabylon(): Boolean = {
    val babFile = getFinalBabylonFile
    ifoFile.isDefined && (ifoFile.get.lastModified > babFile.lastModified)
  }

  def getBabylonOrIfoTimestampString(): String = {
    // Format: dhAtupATha-sa__2016-02-20_16-15-35.tar.gz
    val format = new java.text.SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
    if (getFinalBabylonFile != null) {
      format.format(getFinalBabylonFile.lastModified)
    } else {
      format.format(ifoFile.get.lastModified)
    }
  }

  def makeStardictFromBabylonFile(babylon_binary: String) = {
    val babFile = getFinalBabylonFile
    log info (f"Making stardict from: ${babFile.getCanonicalPath}")
    s"$babylon_binary ${babFile.getCanonicalPath}".!
    dictFile = dirFile.listFiles.map(_.getCanonicalFile).filter(_.getName.matches(s".*/?${dirName}.dict")).headOption
    if (dictFile.nonEmpty) {
      s"dictzip ${dictFile.get.getCanonicalPath}".!
    }
  }

  def getExpectedTarFileName(sizeMbString: String = "unk"): String = s"${dirName}__${getBabylonOrIfoTimestampString}__${sizeMbString}MB.tar.gz"
  def getTarDirFile = new File(dirFile.getParentFile.getCanonicalPath, "/tars")

  def tarFileMatchesBabylon(): Boolean = {
    tarFile.isDefined && tarFile.get.getName.matches(s".*/?${getExpectedTarFileName(sizeMbString = ".*")}")
  }

  def makeTar(filePatternToTar: String) = {
    if (tarFile.isDefined) {
      log info "Deleting " + tarFile.get.getAbsolutePath
      tarFile.get.delete()
    }
    val targetTarFile = new File(getTarDirFile.getCanonicalPath, getExpectedTarFileName())
    targetTarFile.getParentFile.mkdirs
    val filesToCompress = dirFile.listFiles.map(_.getCanonicalPath).filter(x => x.matches(filePatternToTar))
    val command = s"tar --transform s/.*\\///g -czf ${targetTarFile.getCanonicalPath} ${filesToCompress.mkString(" ")}"
    log info command
    command.!

    // Add size hint.
    val sizeMbString = (targetTarFile.length()/(1024*1024)).toLong.toString
    val fileWithSize = new File(getTarDirFile.getCanonicalPath, getExpectedTarFileName(sizeMbString = sizeMbString))
    val renameResult = targetTarFile.renameTo(fileWithSize)
    if (!renameResult) {
      log warn s"Renamed ${targetTarFile} to ${fileWithSize}: $renameResult"
    } else {
      log info s"Renamed ${targetTarFile} to ${fileWithSize}: $renameResult"
    }
  }

  override def toString: String =
    s"${dirFile.getName} with ${babylonFile} babylon, ${babylonFinalFile} babylonFinal, ${ifoFile} ifo, ${tarFile} tar "
}

trait BatchProcessor {
  val log = LoggerFactory.getLogger(getClass.getName)

  /**
    * Get a recursive listing of all files underneath the given directory.
    * from stackoverflow.com/questions/2637643/how-do-i-list-all-files-in-a-subdirectory-in-scala
    */
  def getRecursiveListOfFiles(dir: File): Array[File] = {
    val these = dir.listFiles
    these ++ these.filter(_.isDirectory).flatMap(getRecursiveListOfFiles)
  }

  def getMatchingDirectories (file_pattern: String = ".*", baseDir: String = "."): List[java.io.File] = {
    log info (s"file_pattern: ${file_pattern}")
    val baseDirFile = new File(baseDir)
    log info (s"Current directory: ${baseDirFile.getCanonicalPath}")

    log info baseDirFile.listFiles.filter(_.isDirectory).mkString("\n")
    val directories = baseDirFile.listFiles.filter(_.isDirectory).filter(x => x.getName.matches(s".*/?$file_pattern")).filterNot(_.getName.matches(".*/?tars"))
    log info (s"Got ${directories.length} directories")
    return directories.toList
  }

  def getMatchingDictionaries(file_pattern: String = ".*", baseDir: String = ".") = getMatchingDirectories(file_pattern, baseDir).map(new Dictionary(_))

}

object babylonProcessor extends BatchProcessor{
  override def getMatchingDictionaries(file_pattern: String, baseDir: String = "."): List[Dictionary] = {
    val dictionaries = super.getMatchingDictionaries(file_pattern, baseDir).filter(_.getFinalBabylonFile != null)
    log info (s"Got ${dictionaries.filter(_.babylonFinalFile.isDefined).length} babylon_final files")
    log info (s"Got ${dictionaries.filter(x => x.babylonFile.isDefined && !x.babylonFinalFile.isDefined).length}  dicts without babylon_final files but with babylon file.")
    return dictionaries
  }

  def fixHeadwordsInFinalFile(file_pattern: String = ".*", baseDir: String = ".", headwordTransformer: (Array[String]) => Array[String]) = {
    val files_to_ignore = Set("spokensanskrit.babylon")
    var dictionaries = getMatchingDictionaries(file_pattern, baseDir).filter(_.babylonFile.isDefined)
    log info (s"Got ${dictionaries.length} babylon files")
    var dictsToIgnore = dictionaries.filter(_.babylonFinalFileNewerThanBabylon())
    if (dictsToIgnore.nonEmpty) {
      log warn s"Ignoring these files, whose final babylon files seem updated:" + dictsToIgnore.mkString("\n")
    }
    dictionaries = dictionaries.filterNot(_.babylonFinalFileNewerThanBabylon)
    log info (s"Got ${dictionaries.length} babylon files")

    val babylon_files = dictionaries.map(_.babylonFile)

    babylon_files.map(_.get.getCanonicalPath).foreach(file => {
      if (files_to_ignore contains file) {
        log info (f"skipping: $file")
      } else {
        log info (f"Fixing headwords in: $file")
        sanskritnlp.dictionary.babylonTools.fixHeadwords(file, ".babylon_final", headwordTransformer)
      }
    })
    // sys.exit()
  }

  def addOptitrans(file_pattern: String = ".*", baseDir: String = ".") = {
    log info "=======================Adding optitrans headwords, making final babylon file."
    val headwordTransformer = (headwords_original:Array[String]) => headwords_original ++ headwords_original.map(
      x => transliterator.transliterate(x, "dev", "optitrans"))
    fixHeadwordsInFinalFile(file_pattern=file_pattern, baseDir=baseDir, headwordTransformer=headwordTransformer)
  }

  def stripNonOptitransHeadwords(file_pattern: String = ".*", baseDir: String = "."): Unit = {
    log info "=======================stripNonOptitransHeadwords, making final babylon file."
    val headwordTransformer = (headwords_original:Array[String]) => headwords_original.filterNot(iast.isEncoding(_))
    fixHeadwordsInFinalFile(file_pattern=file_pattern, baseDir=baseDir, headwordTransformer=headwordTransformer)
  }

  def getWordToDictsMapFromPaths(basePaths: Seq[String], wordPattern: String = "(\\p{IsDevanagari})+"): mutable.HashMap[String, ListBuffer[BabylonDictionary]] = {
    val babylonFiles = basePaths.flatMap(basePath => getRecursiveListOfFiles(new File(basePath))).
      filter(_.getName.matches(".*\\.babylon(_final)?"))
    log info s"Got ${babylonFiles.length} babylon files."
    val babylonFinalFiles = babylonFiles.filter(x => x.getName.matches(".*\\.babylon_final")) // || !new File(x.getParentFile.get + "_final").exists())
    val babylonDicts = babylonFinalFiles.map(x => {
      val dict = new BabylonDictionary(name_in = x.getName, head_language = "")
      dict.fromFile(x.getCanonicalPath)
      dict
    })
    log info s"Got ${babylonDicts.length} babylon files."

    val wordToDicts = babylonTools.mapWordToDicts(dictList=babylonDicts, headword_pattern=wordPattern)
    log info s"Got ${wordToDicts.size} words"
    return wordToDicts
  }

  def dumpWordToDictMap(basePaths: Seq[String], wordPattern: String = "(\\p{IsDevanagari})+", outFilePath: String) = {
    val words = getWordToDictsMapFromPaths(basePaths, wordPattern)
    log info s"Got ${words.size} words"
    log info s"Dumping to ${outFilePath} "
    val outFileObj = new File(outFilePath)
    new File(outFileObj.getParent).mkdirs
    val destination = new PrintWriter(outFileObj)
    words.keys.toList.sorted.foreach(word => {
      val dictNames = words.get(word).get.map(_.dict_name)
      destination.println(s"$word\t${dictNames.mkString(",")}")
    })
    destination.close()
  }

  def getDevanagariOptitransFromIast(file_pattern: String = ".*", baseDir: String = ".") = {
    log info "=======================Adding optitrans headwords, making final babylon file."
    val toDevanAgarIAndOptitrans = (headwords_original:Array[String]) => headwords_original.map(
      x => transliterator.transliterate(x, "iast", "dev")) ++ headwords_original.map(
      x => transliterator.transliterate(x, "iast", "optitrans"))
    fixHeadwordsInFinalFile(file_pattern=file_pattern, baseDir=baseDir, headwordTransformer=toDevanAgarIAndOptitrans)
  }

  def getDevanagariOptitransFromIastIfIndic(file_pattern: String = ".*", baseDir: String = ".", indicWordSet: mutable.HashSet[String] = mutable.HashSet[String]()) = {
    log info "=======================Adding optitrans headwords, making final babylon file."
    val indicWordSetDev = indicWordSet.filter(devanAgarI.isEncoding)
    def isIndic(word: String) = indicWordSetDev.contains(iast.fromDevanagari(word)) || iast.isEncoding(word)
    def transliterateIfIndic(x: String, destSchema: String) = if(isIndic(x)) {
      transliterator.transliterate(x, "iast", destSchema)
    } else {
      x
    }
    val toDevanAgarIAndOptitrans = (headwords_original:Array[String]) => headwords_original.map(
      x => transliterateIfIndic(x, "dev")) ++ headwords_original.map(x => transliterateIfIndic(x, "optitrans"))
    fixHeadwordsInFinalFile(file_pattern=file_pattern, baseDir=baseDir, headwordTransformer=toDevanAgarIAndOptitrans)
  }

  def makeStardict(file_pattern: String = ".*", babylon_binary: String) = {
    log info "=======================makeStardict"
    var dictionaries = getMatchingDictionaries(file_pattern)

    var dictsToIgnore = dictionaries.filter(_.ifoFileNewerThanBabylon())
    if (dictsToIgnore.nonEmpty) {
      log warn s"Ignoring these files, whose dict files seem updated: " + dictsToIgnore.mkString("\n")
    }
    dictionaries = dictionaries.filterNot(_.ifoFileNewerThanBabylon())
    dictionaries.foreach(_.makeStardictFromBabylonFile(babylon_binary))
  }

  def transliterateAllIndicToDevanagarI(inFilePath: String, outFilePath: String, sourceScheme:String, wordListFilePath: String ="/home/vvasuki/stardict-sanskrit/wordlists/words_sa_dev.txt") = {
    var wordSet = Source.fromFile(wordListFilePath, "utf8").getLines.map(_.split("\t").headOption.getOrElse("न")).map(transliterator.transliterate(_, transliterator.scriptDevanAgarI, sourceScheme)).toSet
    val outFileObj = new File(outFilePath)
    new File(outFileObj.getParent).mkdirs
    val destination = new PrintWriter(outFileObj)
    val inFile = Source.fromFile(inFilePath, "utf8").getLines().map(line => {
      val outStr = transliterator.transliterateWordsIfIndic(in_str = line, wordSet=wordSet, sourceScheme=sourceScheme, destScheme = transliterator.scriptDevanAgarI)
      destination.println(outStr)
    })
    destination.close()
  }

  def main(args: Array[String]): Unit = {
    val dictPattern = ".*"
    val workingDirInit = System.getProperty("user.dir")
    var workingDir = "/home/vvasuki/stardict-sanskrit/"
    System.setProperty("user.dir", workingDir)
//    dumpWordToDictMap(basePaths=List(workingDir), outFilePath=s"${workingDir}wordlists/words_sa_dev.txt")
    // stripNonOptitransHeadwords(dictPattern, workingDir)
    // getDevanagariOptitransFromIast(dictPattern, workingDir)
//    getDevanagariOptitransFromIastIfIndic(dictPattern, workingDir, getWordToDictsMapFromPaths(List("/home/vvasuki/stardict-pali/pali-head/").keys))
    // addOptitrans(dir)
    // makeStardict(dir, "/home/vvasuki/stardict/tools/src/babylon")
  }
}

object tarProcessor extends BatchProcessor {
  val filePatternToTar = ".*\\.ifo|.*\\.idx|.*\\.dz|.*\\.ifo|.*\\.syn|.*LICENSE\\.*"
  def writeTarsList(tarDestination: String, urlBase: String) = {
    val outFileObj = new File(tarDestination + "/tars.MD")
    outFileObj.getParentFile.mkdirs
    val destination = new PrintWriter(outFileObj)
    val urlBaseFinal = urlBase.replaceAll("/$", "")
    outFileObj.getParentFile.listFiles().map(_.getCanonicalFile).filter(_.getName.endsWith("tar.gz")).toList.sorted.foreach(x => {
      destination.println(s"${urlBaseFinal}/${x.getName.replaceAll(".*/", "")}")
    })
    destination.close()
  }

  def makeTars(urlBase: String, file_pattern: String = ".*") = {
    log info "=======================makeTars"
    // Get timestamp.
    var dictionaries = getMatchingDictionaries(file_pattern).filter(_.ifoFile.isDefined)
    log info (s"Got ${dictionaries.filter(_.tarFile.isDefined).length} tar files")
    log info (s"Got ${dictionaries.filter(x => x.ifoFile.isDefined && !x.tarFile.isDefined).length}  dicts without tarFile files but with ifo file.")
    if (file_pattern == ".*" && dictionaries.nonEmpty) {
      val tarDirFile = dictionaries.head.getTarDirFile
      val excessTarFiles = tarDirFile.listFiles.filterNot(x => {
        val name = x.getName
        name != "tars.MD" && dictionaries.filter(_.tarFile.isDefined).map(_.tarFile.get.getCanonicalPath).contains(x.getCanonicalPath)
      })
      log warn s"Removing ${excessTarFiles.length} excessTarFiles"
      excessTarFiles.foreach(_.delete())
      writeTarsList(dictionaries.head.getTarDirFile.getCanonicalPath, urlBase)
    }
    var dictsToIgnore = dictionaries.filter(_.tarFileNewerThanIfo())
    if (dictsToIgnore.nonEmpty) {
      log warn s"Ignoring these files, whose dict files seem updated: " + dictsToIgnore.mkString("\n")
    }
    dictionaries = dictionaries.filterNot(_.tarFileNewerThanIfo())


    log info(s"got ${dictionaries.length} dictionaries which need to be updated.")
    dictionaries.foreach(_.makeTar(filePatternToTar))

    if (dictionaries.nonEmpty) {
      writeTarsList(dictionaries.head.getTarDirFile.getCanonicalPath, urlBase)
    }
  }

  def compressAllDicts(basePaths: Seq[String], tarFilePath: String) = {
    val dictDirFiles = basePaths.flatMap(basePath => getRecursiveListOfFiles(new File(basePath))).
      filter(_.getName.matches(".*\\.ifo")).map(_.getParentFile)
    val targetTarFile = new File(tarFilePath)
    targetTarFile.getParentFile.mkdirs
    //
    val filesToCompress = dictDirFiles.flatMap(_.listFiles.map(_.getCanonicalPath).filter(x => x.matches(filePatternToTar)))
    val command = s"tar --transform s,${basePaths.map(_.replaceFirst("/", "")).mkString("|")},,g -czf ${targetTarFile.getCanonicalPath} ${filesToCompress.mkString(" ")}"
    log info command
    command.!
  }


  def getStats() = {
    val indexIndexorum = "https://raw.githubusercontent.com/sanskrit-coders/stardict-dictionary-updater/master/dictionaryIndices.md"
    val indexes = Source.fromURL(indexIndexorum).mkString.replaceAll("<|>","").split("\n")
    val counts = indexes.map(index => {
      // Example: https://raw.githubusercontent.com/sanskrit-coders/stardict-sanskrit/master/sa-head/en-entries/tars/tars.MD
      val indexShortName = index.replaceAll("https://raw.githubusercontent.com/sanskrit-coders/|master/|tars/tars.MD", "")
      val indexCount = Source.fromURL(index).mkString.replaceAll("<|>","").split("\n").length
      indexShortName -> indexCount
    })
    counts.sortBy(_._1).foreach(x => {
      println(f"${x._1}%-50s : ${x._2}")
    })
    println(f"${"Total"}%-50s : ${counts.toMap.values.sum}")
  }

  def main(args: Array[String]): Unit = {
    var workingDir = "/home/vvasuki/stardict-sanskrit/"
    compressAllDicts(List(workingDir), workingDir + "all_dicts.tar.gz")
    //    makeTars("https://github.com/sanskrit-coders/stardict-telugu/raw/master/en-head/tars", dir)
    //    getStats
  }


}
