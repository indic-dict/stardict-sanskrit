#!/bin/sh
PATH_TO_SANSKRITNLPJAVA=~/sanskritnlpjava/target
STARDICT_SANSKRIT_SCALA=~/sanskritnlpjava/out/production/stardict_sanskrit_bin
BABYLON_BINARY=`echo ~/stardict/tools/src/babylon`
scala -classpath "$STARDICT_SANSKRIT_SCALA:$PATH_TO_SANSKRITNLPJAVA/sanskritnlp-1.0-SNAPSHOT/WEB-INF/lib/*:$PATH_TO_SANSKRITNLPJAVA/sanskritnlp-1.0-SNAPSHOT/WEB-INF/classes" -e "stardict_sanskrit.batchProcessor.makeTars(\"$1\", \"$2\".replace(\"DICTS=\", \"\"))"
