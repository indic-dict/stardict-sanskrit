STARDICT_TOOLS_DIR=~/stardict/tools/src/
STARDICT_SANSKRIT_BIN=~/stardict-sanskrit/bin

dicts=*

.PHONY: all stardict tars

all:final_babylon stardict tars

final_babylon:
	bash ../bin/babylon_add_optitrans.sh *

stardict:
	bash ../bin/tsv_to_stardict.sh
	
tars:
	bash ../bin/make_tarballs.sh https://github.com/sanskrit-coders/stardict-sanskrit/raw/master/sa-vyAkaraNa

