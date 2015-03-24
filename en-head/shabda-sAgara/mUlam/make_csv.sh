#!/bin/sh
exec scala -classpath "~/sanskritnlpjava/target/sanskritnlp-1.0-SNAPSHOT.jar:~/sanskritnlpjava/target/sanskritnlp-1.0-SNAPSHOT/WEB-INF/lib/*" "$0" "$@"
!#
println("hello");

import sanskritnlp.transliteration._

