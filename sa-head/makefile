STARDICT_TOOLS_DIR=~/stardict/tools/src/

ifndef dicts
	dicts=*
endif

.PHONY: all stardict tars

all:stardict dictunzip tars

dictunzip:
	eval dictunzip $(dicts)/*.dz

stardict:
	bash ../bin/tsv_to_stardict.sh
	
tars:
	bash ../bin/make_tarballs.sh https://github.com/vvasuki/stardict-sanskrit/raw/master/sa-head

