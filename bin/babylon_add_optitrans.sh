#!/bin/bash
# STARDICT_SANSKRIT_SCALA=`dirname $0`
PATH_TO_JARS=~/dict-tools
scala -classpath "$PATH_TO_JARS/bin/artifacts/*" -e "stardict_sanskrit.babylonProcessor.addOptitrans(\"$1\".replace(\"DICTS=\", \"\"))"