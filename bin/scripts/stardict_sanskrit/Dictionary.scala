package stardict_sanskrit

import java.io.File

import org.slf4j.LoggerFactory
import sys.process._

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

  def babylonFinalFileNewerThanBabylon(): Boolean = {
    babylonFinalFile.isDefined && (babylonFinalFile.get.lastModified > babylonFile.get.lastModified)
  }

  def ifoFileNewerThanBabylon(): Boolean = {
    if (babylonFinalFile.isDefined) {
      ifoFile.isDefined && (ifoFile.get.lastModified > babylonFinalFile.get.lastModified)
    } else {
      ifoFile.isDefined && (ifoFile.get.lastModified > babylonFile.get.lastModified)
    }
  }

  def this(dirFileIn: java.io.File ) = {
    this(dirFileIn.getName)
    dirFile = dirFileIn
    dirName = dirFile.getName.replaceAll(".*/", "")
    babylonFile = dirFile.listFiles.filter(_.getName.matches(s".*/?${dirName}.babylon")).headOption
    babylonFinalFile = dirFile.listFiles.filter(_.getName.matches(s".*/?${dirName}.babylon_final")).headOption
    tarFile = dirFile.getParentFile.listFiles.filter(_.getName.matches(s".*/?tars")).headOption.get.listFiles.
      filter(_.getName.matches(s".*/?${dirName}.*.tar.gz")).headOption
    ifoFile = dirFile.listFiles.filter(_.getName.equals(s"${dirName}.ifo")).headOption
    dictFile = dirFile.listFiles.filter(_.getName.equals(s"${dirName}.dict")).headOption
    dictdzFile = dirFile.listFiles.filter(_.getName.equals(s"${dirName}.dict.dz")).headOption
    log debug toString
  }

  override def toString: String =
    s"${dirFile.getName} with ${babylonFile} babylon, ${babylonFinalFile} babylonFinal, ${ifoFile} ifo, ${tarFile} tar "
}

object batchProcessor {
  val log = LoggerFactory.getLogger(getClass.getName)

  def getMatchingDirectories (file_pattern: String = ".*"): List[java.io.File] = {
    log info (s"file_pattern: ${file_pattern}")

    val directories = new java.io.File( "." ).listFiles.filter(_.isDirectory).filter(x => x.getName.matches(file_pattern))
    log info (s"Got ${directories.length} directories")
    return directories.toList
  }

  def addOptitrans(file_pattern: String = ".*") = {
    val files_to_ignore = Set("spokensanskrit.babylon")
    val directories = getMatchingDirectories(file_pattern)
    var dictionaries = directories.map(new Dictionary(_)).filter(_.babylonFile.isDefined)
    log info (s"Got ${dictionaries.length} babylon files")
    log warn s"Ignoring these files, whose final babylon files seem updated: " +
      dictionaries.filter(_.babylonFinalFileNewerThanBabylon).mkString("\n")
    dictionaries = dictionaries.filterNot(_.babylonFinalFileNewerThanBabylon)
    log info (s"Got ${dictionaries.length} babylon files")

    val babylon_files = dictionaries.map(_.babylonFile)

    babylon_files.map(_.get.getCanonicalPath).foreach(file => {
      if (files_to_ignore contains file) {
        log info (f"skipping: $file")
      } else {
        log info (f"Adding headwords to: $file")
        sanskritnlp.dictionary.babylonTools.addTransliteratedHeadwords(file, ".babylon_final", "dev", "optitrans")
      }
    })
    // sys.exit()
  }

  def makeStardict(file_pattern: String = ".*") = {
    val directories = getMatchingDirectories(file_pattern)
    val dictionaries = directories.map(new Dictionary(_))

    def makeStardictFromBabylonFile(filename: String) = {
      log info (f"Making stardict from: $filename")
      s"~/stardict/tools/src/babylon $filename".!
    }

    var dictionaries_with_final_babylon = dictionaries.filter(_.babylonFinalFile.isDefined)
    log info (s"Got ${dictionaries_with_final_babylon.length} babylon_final files")
    log warn s"Ignoring these files, whose dict files seem updated: " +
      dictionaries_with_final_babylon.filter(_.ifoFileNewerThanBabylon()).mkString("\n")
    dictionaries_with_final_babylon = dictionaries_with_final_babylon.filterNot(_.ifoFileNewerThanBabylon())
    val babylon_files = dictionaries_with_final_babylon.map(_.babylonFinalFile)

    babylon_files.map(_.get.getCanonicalPath).foreach(file => {
      makeStardictFromBabylonFile(file)
    })

    var dictionaries_without_final_babylon = dictionaries.filter(x => x.babylonFile.isDefined && !x.babylonFinalFile.isDefined)
    log info (s"Got ${dictionaries_without_final_babylon.length} dicts without babylon_final files but with babylon file.")
    log warn s"Ignoring these files, whose dict files seem updated: " +
      dictionaries_without_final_babylon.filter(x => x.ifoFile.isDefined && (x.ifoFile.get.lastModified > x.babylonFile.get.lastModified)).mkString("\n")
    dictionaries_without_final_babylon = dictionaries_without_final_babylon.filterNot(x => x.ifoFile.isDefined && (x.ifoFile.get.lastModified > x.babylonFile.get.lastModified))
    dictionaries_without_final_babylon.map(_.babylonFile)

    babylon_files.map(_.get.getCanonicalPath).foreach(file => {
      makeStardictFromBabylonFile(file)
    })
  }

  def makeTars(urlBase: String, file_pattern: String = ".*") = {
    // Get timestamp.

  }

  def addDevanagari(file_pattern: String = ".*") = {
    val directories = getMatchingDirectories(file_pattern)
    log error "Not implemented"
    throw new Error()
  }
}