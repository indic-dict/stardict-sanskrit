#!/bin/sh
exec scala -classpath "/home/vvasuki/sanskritnlpjava/target/sanskritnlp-1.0-SNAPSHOT.jar" "$0" "$@"
!#

import sanskritnlp.transliteration._;
println("hello")

