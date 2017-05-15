#!/bin/sh
PATH_TO_SANSKRITNLPJAVA=~/sanskritnlpjava
BABYLON_BINARY=`echo ~/stardict/tools/src/babylon`
PATH_TO_JARS=~/stardict-sanskrit
echo scala -classpath "$PATH_TO_JARS/bin/artifacts/*" -e "stardict_sanskrit.batchProcessor.writeTarsList(\"$1\", \"$2\")"
scala -classpath "$PATH_TO_JARS/bin/artifacts/stardict-sanskrit.jar" -e "stardict_sanskrit.tarProcessor.writeTarsList(\"$1\", \"$2\")"

