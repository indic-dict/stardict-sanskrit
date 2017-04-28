#!/bin/bash
# STARDICT_SANSKRIT_SCALA=`dirname $0`
PATH_TO_SANSKRITNLPJAVA=~/sanskritnlpjava
scala -classpath "$PATH_TO_SANSKRITNLPJAVA/out/*" -e "stardict_sanskrit.babylonProcessor.addOptitrans(\"$1\".replace(\"DICTS=\", \"\"))"