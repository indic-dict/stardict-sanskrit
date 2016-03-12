STARDICT_TOOLS_DIR=~/stardict/tools/src/
TABFILE=$(STARDICT_TOOLS_DIR)/tabfile
STARDICT_SANSKRIT_BIN=~/stardict-sanskrit/bin


ifndef dicts
	dicts="*"
endif

.PHONY: all tars decompile

all:stardict tars

stardict:
	bash ../bin/tsv_to_stardict.sh

tars:
	bash ../bin/make_tarballs.sh https://github.com/sanskrit-coders/stardict-sanskrit/raw/master/en-head
	
decompile:
	bash ./decompile_all.sh
