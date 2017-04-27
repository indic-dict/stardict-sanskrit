#!/bin/sh
PATH_TO_SANSKRITNLPJAVA=~/sanskritnlpjava
BABYLON_BINARY=`echo ~/stardict/tools/src/babylon`
scala -classpath "$PATH_TO_SANSKRITNLPJAVA/out/*" -e "stardict_sanskrit.babylonProcessor.makeStardict(\"$1\".replace(\"DICTS=\", \"\"), \"$BABYLON_BINARY\")"
