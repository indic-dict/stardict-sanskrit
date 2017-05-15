#!/bin/sh
PATH_TO_JARS=~/stardict-sanskrit
scala -classpath "$PATH_TO_JARS/bin/artifacts/*" -e "stardict_sanskrit.babylonProcessor.getDevanagariOptitrans(\"$1\".replace(\"DICTS=\", \"\"))"
