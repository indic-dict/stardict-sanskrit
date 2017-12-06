#!/bin/bash
# STARDICT_SANSKRIT_SCALA=`dirname $0`
PATH_TO_JARS=~/dict-tools
java -cp "$PATH_TO_JARS/bin/artifacts/dict-tools.jar" stardict_sanskrit.commandInterface addOptitrans $1
