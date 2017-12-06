#!/bin/sh
PATH_TO_SANSKRITNLPJAVA=~/sanskritnlpjava
BABYLON_BINARY=`echo ~/stardict/tools/src/babylon`
PATH_TO_JARS=~/dict-tools
#scala -classpath "$PATH_TO_JARS/bin/artifacts/*" -e "stardict_sanskrit.tarProcessor.writeTarsList(\"$1\", \"$2\")"

java -cp "$PATH_TO_JARS/bin/artifacts/dict-tools.jar" stardict_sanskrit.commandInterface writeTarsList $1 $2
