STARDICT_TOOLS_DIR=~/stardict/tools/src/
TABFILE=$(STARDICT_TOOLS_DIR)/tabfile
STARDICT_SANSKRIT_BIN=~/stardict-sanskrit/bin

.PHONY: all tars decompile

all:stardict tars

# make all DICTS=xyz
DICTS=.*

stardict:
	bash ../bin/tsv_to_stardict.sh DICTS=$(DICTS)

tars:
	bash ../bin/make_tarballs.sh https://github.com/sanskrit-coders/stardict-sanskrit/raw/master/en-head
	
decompile:
	# Note: cannot usefully decompile apte-bi and mw-bi
	bash ../bin/decompile_all.sh
