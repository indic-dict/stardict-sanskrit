#!/bin/sh
PATH_TO_SANSKRITNLPJAVA=~/sanskritnlpjava
BABYLON_BINARY=`echo ~/stardict/tools/src/babylon`
scala -classpath "$PATH_TO_SANSKRITNLPJAVA/out/*" -e "stardict_sanskrit.tarProcessor.makeTars(\"$1/tars\", \"$2\".replace(\"DICTS=\", \"\"))"
