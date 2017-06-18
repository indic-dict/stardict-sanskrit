#!/bin/sh
PATH_TO_SANSKRITNLPJAVA=~/sanskritnlpjava
BABYLON_BINARY=`echo ~/stardict/tools/src/babylon`
PATH_TO_JARS=~/dict-tools
scala -classpath "$PATH_TO_JARS/bin/artifacts/*" -e "stardict_sanskrit.babylonProcessor.makeStardict(\"$1\".replace(\"DICTS=\", \"\"), \"$BABYLON_BINARY\")"
