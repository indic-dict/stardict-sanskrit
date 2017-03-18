
import sys.process._
import java.io.File
import sanskritnlp.transliteration._
val babylon_files = new java.io.File( "." ).listFiles.filter(_.isDirectory).flatMap(_.listFiles).filter(_.getName.endsWith(".babylon")).map(_.getCanonicalPath)

babylon_files.foreach(file => {
  println(file)
  sanskritnlp.dictionary.babylonTools.addTransliteratedHeadwords(file, ".babylon_final", "iast", "dev")
})
