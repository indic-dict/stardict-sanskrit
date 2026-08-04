.PHONY: all dump_files tars

all:make_db

make_db:
	cat skd.txt| python make_simple_dict.py > kalpadruma-sa-v2.tsv

make_babylon:
	cat kalpadruma-sa-v2-dev.tsv| python make_babylon.py > ../kalpadruma-sa.babylon
