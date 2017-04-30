#!/bin/sh
PATH_TO_SANSKRITNLPJAVA=~/sanskritnlpjava
BABYLON_BINARY=`echo ~/stardict/tools/src/babylon`
echo scala -classpath "$PATH_TO_SANSKRITNLPJAVA/out/*" -e "stardict_sanskrit.tarProcessor.compressAllDicts(Seq(\"$1\"), \"$1\" + \"/all_dicts.tar.gz\")"
scala -classpath "$PATH_TO_SANSKRITNLPJAVA/out/*" -e "stardict_sanskrit.tarProcessor.compressAllDicts(Seq(\"$1\"), \"$1\" + \"/all_dicts.tar.gz\")"
