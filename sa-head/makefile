STARDICT_TOOLS_DIR=~/stardict/tools/src/
TABFILE=$(STARDICT_TOOLS_DIR)/tabfile

ifndef dicts
	dicts="*"
endif

all:
	$(TABFILE) $(dicts)/*.tsv
	dictunzip $(dicts)/*.dz
	bash ./make_tarballs.sh $(dicts)

tars:
	bash ./make_tarballs.sh $(dicts)

test:
	echo $(dicts)
	bash ./make_tarballs.sh
