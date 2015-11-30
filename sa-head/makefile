STARDICT_TOOLS_DIR=~/stardict/tools/src/
TABFILE=$(STARDICT_TOOLS_DIR)/tabfile
BABYLON=$(STARDICT_TOOLS_DIR)/babylon

ifndef dicts
	dicts=*
endif

.PHONY: all stardict tars

all:stardict dictunzip tars

dictunzip:
	eval dictunzip $(dicts)/*.dz

stardict: babylon tsv

babylon:
	for file in `ls $(dicts)/*.babylon` ; do $(BABYLON) $$file; done;

tsv:
	for file in `ls $(dicts)/*.tsv` ; do echo $$file; $(TABFILE) $$file; done;
	
tars:
	bash ../bin/make_tarballs.sh https://github.com/vvasuki/stardict-sanskrit/raw/master/sa-head

