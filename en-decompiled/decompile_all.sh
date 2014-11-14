STARDICT_TOOLS_DIR=~/stardict/tools/src/
DECOMPILE=$STARDICT_TOOLS_DIR/stardict2txt

for ifo_file in `ls */*.ifo`
do
	txt_file=`ls $ifo_file|sed s/.ifo/.txt/`
	$DECOMPILE $ifo_file $txt_file
done
