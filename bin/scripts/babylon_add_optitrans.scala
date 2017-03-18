// Example invocation: bash ../bin/babylon_add_optitrans.sh DICTS=.*
import sys.process._
import java.io.File
import org.slf4j.LoggerFactory
import sanskritnlp.transliteration._

val log = LoggerFactory.getLogger("babylon_add_optitrans")

val files_to_ignore = Set("spokensanskrit.babylon")
var files_to_process = ".*"
log info args.mkString(",")
if (args.nonEmpty) {
  files_to_process = args(0).replace("DICTS=", "")
}
log info (s"files_to_process: ${files_to_process}")

val directories = new java.io.File( "." ).listFiles.filter(_.isDirectory).filter(x => x.getName.matches(files_to_process))
log info (s"Got ${directories.length} directories")
val babylon_files = directories.flatMap(_.listFiles).filter(_.getName.endsWith(".babylon")).filterNot(x => files_to_ignore.contains(x.getName)).map(_.getCanonicalPath)
log info (s"Got ${babylon_files.length} files")

sys.exit()

babylon_files.foreach(file => {
  if (files_to_ignore contains file) {
    log info (f"skipping $file")
  } else {
    log info (f"Adding headwords to: $file")
    sanskritnlp.dictionary.babylonTools.addTransliteratedHeadwords(file, ".babylon_final", "dev", "optitrans")
  }
})
