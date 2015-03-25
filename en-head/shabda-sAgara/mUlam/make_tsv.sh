#!/bin/sh
PATH_TO_SANSKRITNLPJAVA=/home/vvasuki/sanskritnlpjava/target
exec scala -classpath "$PATH_TO_SANSKRITNLPJAVA/sanskritnlp-1.0-SNAPSHOT/WEB-INF/lib/*:$PATH_TO_SANSKRITNLPJAVA/sanskritnlp-1.0-SNAPSHOT/WEB-INF/classes" "$0" "$@"
!#
println("hello");

import sanskritnlp.transliteration._

