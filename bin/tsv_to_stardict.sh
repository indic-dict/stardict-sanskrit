#!/bin/sh
PATH_TO_SANSKRITNLPJAVA=~/sanskritnlpjava/target
STARDICT_SANSKRIT_SCALA=~/sanskritnlpjava/out/production/stardict_sanskrit_bin
scala -classpath "$STARDICT_SANSKRIT_SCALA:$PATH_TO_SANSKRITNLPJAVA/sanskritnlp-1.0-SNAPSHOT/WEB-INF/lib/*:$PATH_TO_SANSKRITNLPJAVA/sanskritnlp-1.0-SNAPSHOT/WEB-INF/classes" -e "stardict_sanskrit.batchProcessor.makeStardict(\"$1\".replace(\"DICTS=\", \"\"))"
