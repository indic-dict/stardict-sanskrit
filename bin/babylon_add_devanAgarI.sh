#!/bin/sh
PATH_TO_SANSKRITNLPJAVA=/home/vvasuki/sanskritnlpjava/target
exec scala -classpath "$PATH_TO_SANSKRITNLPJAVA/sanskritnlp-1.0-SNAPSHOT/WEB-INF/lib/*:$PATH_TO_SANSKRITNLPJAVA/sanskritnlp-1.0-SNAPSHOT/WEB-INF/classes" "$0" "$@"
!#
println("hello")

import sys.process._
import java.io.File
import sanskritnlp.transliteration._
val babylon_files = new java.io.File( "." ).listFiles.filter(_.isDirectory).flatMap(_.listFiles).filter(_.getName.endsWith(".babylon")).map(_.getCanonicalPath)

babylon_files.foreach(file => {
  println(file)
  sanskritnlp.transliteration.dictTools.addTransliteratedHeadwords(file, ".babylon_final", "iast", "dev")
})
