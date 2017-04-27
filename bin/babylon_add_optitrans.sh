#!/bin/sh
# STARDICT_SANSKRIT_SCALA=`dirname $0`
PATH_TO_SANSKRITNLPJAVA=~/sanskritnlpjava
scala -classpath "$PATH_TO_SANSKRITNLPJAVA/out/*" "$0" "$@"
!#

stardict_sanskrit.babylonProcessor.addOptitrans(\"$1\".replace(\"DICTS=\", \"\"))
