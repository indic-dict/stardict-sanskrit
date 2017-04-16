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
	bash ../bin/make_tarballs.sh https://github.com/sanskrit-coders/stardict-sanskrit/raw/master/en-head DICTS=$(DICTS)
	
decompile:
	# Note: cannot usefully decompile apte-bi and mw-bi
	bash ../bin/decompile_all.sh

mkfile_path := $(abspath $(lastword $(MAKEFILE_LIST)))
current_dir := $(dir $(mkfile_path))
# Let the URL not end with /.
URL=error
tarlist:
	bash ../bin/update_tars_md.sh $(current_dir)tars $(URL)


