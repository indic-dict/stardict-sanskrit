
ifndef dicts
	dicts="*"
endif

all:decompile

tars:
	bash ../bin/make_tarballs.sh $(dicts)
	
decompile:
	bash ./decompile_all.sh
