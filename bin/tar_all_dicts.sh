#!/bin/sh
PATH_TO_SANSKRITNLPJAVA=~/sanskritnlpjava
BABYLON_BINARY=`echo ~/stardict/tools/src/babylon`
outfile_name=$1/all_dicts_`basename $1`.tar.gz
echo scala -classpath "$PATH_TO_SANSKRITNLPJAVA/out/*" -e "stardict_sanskrit.tarProcessor.compressAllDicts(Seq(\"$1\"), \"$outfile_name\")"
scala -classpath "$PATH_TO_SANSKRITNLPJAVA/out/*" -e "stardict_sanskrit.tarProcessor.compressAllDicts(Seq(\"$1\"), \"$outfile_name\")"
