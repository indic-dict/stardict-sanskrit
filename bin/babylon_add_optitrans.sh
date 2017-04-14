#!/bin/sh
# STARDICT_SANSKRIT_SCALA=`dirname $0`
PATH_TO_SANSKRITNLPJAVA=~/sanskritnlpjava
scala -classpath "$PATH_TO_SANSKRITNLPJAVA/out/*" -e "stardict_sanskrit.batchProcessor.addOptitrans(\"$1\".replace(\"DICTS=\", \"\"))"
