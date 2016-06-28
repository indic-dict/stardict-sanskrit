#!/bin/sh
PATH_TO_SANSKRITNLPJAVA=/home/vvasuki/sanskritnlpjava/target
exec scala -classpath "$PATH_TO_SANSKRITNLPJAVA/sanskritnlp-1.0-SNAPSHOT/WEB-INF/lib/*:$PATH_TO_SANSKRITNLPJAVA/sanskritnlp-1.0-SNAPSHOT/WEB-INF/classes" "$0" "$@"
!#
println("hello")

import sys.process._
import java.io.File
import sanskritnlp.transliteration._
val files_to_ignore = Set("spokensanskrit.babylon")
val babylon_files = new java.io.File( "." ).listFiles.filter(_.isDirectory).flatMap(_.listFiles).filter(_.getName.endsWith(".babylon")).filterNot(x => files_to_ignore.contains(x.getName)).map(_.getCanonicalPath)

babylon_files.foreach(file => {
  if (files_to_ignore contains file) {
	  println(f"skipping $file")
  } else {
	  println(f"Adding headwords to: $file")
		sanskritnlp.dictionary.dictTools.addTransliteratedHeadwords(file, ".babylon_final", "dev", "optitrans")
  }
})
