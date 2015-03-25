#!/bin/sh
exec scala -classpath "/home/vvasuki/sanskritnlpjava/target/sanskritnlp-1.0-SNAPSHOT/WEB-INF/lib/*" "$0" "$@"
!#
println("hello");

import sanskritnlp.transliteration._

