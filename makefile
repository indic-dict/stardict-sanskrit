.PHONY: all

all:
	make -C sa-head all
	make -C en-head all
	make -C sa-kAvya all
	make -C sa-vyAkaraNa all

tars:
	make -C sa-head tars
	make -C en-head tars
	make -C sa-kAvya tars
	make -C sa-vyAkaraNa tars

tarlist:
	make -C sa-head tarlist
	make -C en-head tarlist
	make -C sa-kAvya tarlist
	make -C sa-vyAkaraNa tarlist

