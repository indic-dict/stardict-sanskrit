.PHONY: all

all:
	make -C sa-head all
	make -C en-head all
	make -C sa-kAvya all
	make -C sa-vyAkaraNam all

tars:
	make -C sa-head tars
	make -C en-head tars
	make -C sa-kAvya tars
	make -C sa-vyAkaraNam tars

