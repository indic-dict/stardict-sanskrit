STARDICT_TOOLS_DIR=~/stardict/tools/src/
TABFILE=$(STARDICT_TOOLS_DIR)/tabfile


ifndef dicts
	dicts="*"
endif

.PHONY: all tars decompile

all:stardict tars

stardict:
	eval $(TABFILE) $(dicts)/*.tsv
	eval dictunzip $(dicts)/*.dz

tars:
	bash ../bin/make_tarballs.sh https://github.com/vvasuki/stardict-sanskrit/raw/master/en-head
	
decompile:
	bash ./decompile_all.sh
