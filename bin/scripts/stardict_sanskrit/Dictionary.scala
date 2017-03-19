package stardict_sanskrit

import java.io.File

import org.slf4j.LoggerFactory

class Dictionary(val name: String) {
  val log = LoggerFactory.getLogger(getClass.getName)
  var dirName: String = null
  var dirFile: File = null
  var babylonFile: Option[File] = None
  var babylonFinalFile: Option[File] = None
  var ifoFile: Option[File] = None
  var tarFile: Option[File] = None

  def this(dirFileIn: java.io.File ) = {
    this(dirFileIn.getName)
    dirFile = dirFileIn
    dirName = dirFile.getName.replaceAll(".*/", "")
    babylonFile = dirFile.listFiles.filter(_.getName.matches(s".*/?${dirName}.babylon")).headOption
    babylonFinalFile = dirFile.listFiles.filter(_.getName.matches(s".*/?${dirName}.babylon_final")).headOption
    tarFile = dirFile.getParentFile.listFiles.filter(_.getName.matches(s".*/?tars")).headOption.get.listFiles.
      filter(_.getName.matches(s".*/?${dirName}.*.tar.gz")).headOption
    ifoFile = dirFile.listFiles.filter(_.getName.equals(s"${dirName}.ifo")).headOption
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
    log warn s"Ignoring these files, which have not been modified: " +
      dictionaries.filter(x => x.tarFile.isDefined && (x.tarFile.get.lastModified > x.babylonFile.get.lastModified)).mkString("\n")
    dictionaries = dictionaries.filterNot(x => x.tarFile.isDefined && (x.tarFile.get.lastModified > x.babylonFile.get.lastModified))
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

  def addDevanagari(file_pattern: String = ".*") = {
    val directories = getMatchingDirectories(file_pattern)
    log error "Not implemented"
    throw new Error()
  }
}