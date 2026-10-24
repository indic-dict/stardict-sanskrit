STARDICT_TOOLS_DIR=~/stardict/tools/src/
TABFILE=$(STARDICT_TOOLS_DIR)/tabfile
DICT_TOOLS_BIN_BASH=~/dict-tools/bin/bash

.PHONY: all tars decompile

all:stardict tars

# make all DICTS=xyz
DICTS=.*
OVERWRITE=false
GITHUB_TOKEN=NONE

# Let the URL not end with /.
URL=https://github.com/indic-dict/stardict-sanskrit/raw/gh-pages/$(shell pwd|sed -e s/.*[/]//)/tars

stardict:
	bash $(DICT_TOOLS_BIN_BASH)/tsv_to_stardict.sh DICTS=$(DICTS)

tars:
	bash $(DICT_TOOLS_BIN_BASH)/make_tarballs.sh $(URL) DICTS=$(DICTS)

full:
	bash $(DICT_TOOLS_BIN_BASH)/full_build.sh $(URL) DICTS=$(DICTS) GITHUB_TOKEN=$(GITHUB_TOKEN) overwrite=$(OVERWRITE)
decompile:
	# Note: cannot usefully decompile apte-bi and mw-bi
	bash $(DICT_TOOLS_BIN_BASH)/decompile_all.sh

mkfile_path := $(abspath $(lastword $(MAKEFILE_LIST)))
current_dir := $(dir $(mkfile_path))
tarlist:
	bash $(DICT_TOOLS_BIN_BASH)/update_tars_md.sh $(current_dir)tars $(URL)
