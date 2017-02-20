// To run:
// PATH_TO_SANSKRITNLPJAVA=/home/vvasuki/sanskritnlpjava/target
// scala -classpath "$PATH_TO_SANSKRITNLPJAVA/sanskritnlp-1.0-SNAPSHOT/WEB-INF/lib/*:$PATH_TO_SANSKRITNLPJAVA/sanskritnlp-1.0-SNAPSHOT/WEB-INF/classes" stardict-sanskrit/sa-kAvya/dcs-frequency/scripts/mkBabylon.scala

import java.io._

import org.slf4j.LoggerFactory
import sanskritnlp.transliteration._

import scala.collection.mutable
import scala.io.Source
val log = LoggerFactory.getLogger("Chandas")

