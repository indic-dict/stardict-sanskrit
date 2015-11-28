STARDICT_TOOLS_DIR=~/stardict/tools/src/
TABFILE=$(STARDICT_TOOLS_DIR)/tabfile
BABYLON=$(STARDICT_TOOLS_DIR)/babylon

ifndef dicts
	dicts=*
endif

.PHONY: all stardict tars

all:stardict tars

dictunzip:
	eval dictunzip $(dicts)/*.dz

stardict:
	for file in `ls $(dicts)/*.tsv` ; do $(TABFILE) $$file; done;
	for file in `ls $(dicts)/*.babylon` ; do $(BABYLON) $$file; done;

babylon:
	for file in `ls $(dicts)/*.babylon` ; do $(BABYLON) $$file; done;
	
tars:
	bash ../bin/make_tarballs.sh https://github.com/vvasuki/stardict-sanskrit/raw/master/sa-head

