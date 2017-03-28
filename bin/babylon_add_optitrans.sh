#!/bin/sh
# STARDICT_SANSKRIT_SCALA=`dirname $0`
PATH_TO_SANSKRITNLPJAVA=~/sanskritnlpjava
STARDICT_SANSKRIT_SCALA=~/sanskritnlpjava/out/production/stardict_sanskrit_bin
scala -classpath "$STARDICT_SANSKRIT_SCALA:$PATH_TO_SANSKRITNLPJAVA/out/*" -e "stardict_sanskrit.batchProcessor.addOptitrans(\"$1\".replace(\"DICTS=\", \"\"))"
