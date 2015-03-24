#!/bin/sh
exec scala -classpath "~/sanskritnlpjava/target/sanskritnlp-1.0-SNAPSHOT.jar " "$0" "$@"
!#

import sanskritnlp.transliteration._;
// print "hello"
