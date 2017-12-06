#!/bin/sh
BABYLON_BINARY=`echo ~/stardict/tools/src/babylon`
outfile_name=$1/all_dicts_`basename $1`.tar.gz
PATH_TO_JARS=~/dict-tools
# scala -classpath "$PATH_TO_JARS/bin/artifacts/*" -e "stardict_sanskrit.tarProcessor.compressAllDicts(Seq(\"$1\"), \"$outfile_name\")"
java -cp "$PATH_TO_JARS/bin/artifacts/dict-tools.jar" stardict_sanskrit.commandInterface compressAllDicts $1 $outfile_name
