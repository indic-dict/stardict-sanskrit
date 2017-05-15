#!/bin/sh
PATH_TO_SANSKRITNLPJAVA=~/sanskritnlpjava
BABYLON_BINARY=`echo ~/stardict/tools/src/babylon`
PATH_TO_JARS=~/stardict-sanskrit
scala -classpath "$PATH_TO_JARS/bin/artifacts/*" -e "stardict_sanskrit.tarProcessor.makeTars(\"$1/tars\", \"$2\".replace(\"DICTS=\", \"\"))"
