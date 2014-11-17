
ifndef dicts
	dicts="*"
endif

.PHONY: all tars decompile

all:decompile

tars:
	bash ../bin/make_tarballs.sh https://github.com/vvasuki/stardict-sanskrit/raw/master/en-head
	
decompile:
	bash ./decompile_all.sh
