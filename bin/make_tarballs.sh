#!/bin/sh
PATH_TO_SANSKRITNLPJAVA=~/sanskritnlpjava
BABYLON_BINARY=`echo ~/stardict/tools/src/babylon`
PATH_TO_JARS=~/dict-tools
# scala -classpath "$PATH_TO_JARS/bin/artifacts/*" -e "stardict_sanskrit.tarProcessor.makeTars(\"$1\", \"$2\".replace(\"DICTS=\", \"\"))"
java -cp "$PATH_TO_JARS/bin/artifacts/dict-tools.jar" stardict_sanskrit.commandInterface makeTars $1 $2
