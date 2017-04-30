#!/bin/sh
PATH_TO_SANSKRITNLPJAVA=~/sanskritnlpjava
BABYLON_BINARY=`echo ~/stardict/tools/src/babylon`
scala -classpath "$PATH_TO_SANSKRITNLPJAVA/out/*" -e "stardict_sanskrit.tarProcessor.compressAllDicts([\"$1\"], \"$1\" + \"/all_dicts.tar.gz\")"
