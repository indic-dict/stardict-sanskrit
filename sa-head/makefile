STARDICT_TOOLS_DIR=~/stardict/tools/src/

ifndef dicts
	dicts=*
endif

.PHONY: all stardict tars

all:final_babylon stardict dictunzip tars

dictunzip:
	eval dictunzip $(dicts)/*.dz

final_babylon:
	bash ../bin/babylon_add_optitrans.sh

stardict:
	bash ../bin/tsv_to_stardict.sh
	
tars:
	bash ../bin/make_tarballs.sh https://github.com/vvasuki/stardict-sanskrit/raw/master/sa-head

