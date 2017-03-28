#!/bin/sh
PATH_TO_SANSKRITNLPJAVA=~/sanskritnlpjava
STARDICT_SANSKRIT_SCALA=~/sanskritnlpjava/out/production/stardict_sanskrit_bin
BABYLON_BINARY=`echo ~/stardict/tools/src/babylon`
scala -classpath "$STARDICT_SANSKRIT_SCALA:$PATH_TO_SANSKRITNLPJAVA/out/*" -e "stardict_sanskrit.batchProcessor.makeTars(\"$1\", \"$2\".replace(\"DICTS=\", \"\"))"
