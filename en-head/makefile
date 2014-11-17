
ifndef dicts
	dicts="*"
endif

.PHONY: all tars decompile

all:decompile

tars:
	bash ../bin/make_tarballs.sh $(dicts)
	
decompile:
	bash ./decompile_all.sh
