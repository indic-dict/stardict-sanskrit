# make all DICTS=xyz
DICTS=.*

.PHONY: all stardict tars

all:final_babylon stardict tars

final_babylon:
	bash ../bin/babylon_add_optitrans.sh DICTS=$(DICTS)

stardict:
	bash ../bin/tsv_to_stardict.sh DICTS=$(DICTS)

	
tars:
	bash ../bin/make_tarballs.sh   https://github.com/sanskrit-coders/stardict-sanskrit/raw/master/sa-sanskritnlp.vyAkaraNa.vyAkaraNa DICTS=$(DICTS)

mkfile_path := $(abspath $(lastword $(MAKEFILE_LIST)))
current_dir := $(dir $(mkfile_path))
# Let the URL not end with /.
URL=error
tarlist:
	bash ../bin/update_tars_md.sh $(current_dir)tars $(URL)


