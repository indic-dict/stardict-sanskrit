STARDICT_TOOLS_DIR=~/stardict/tools/src/
DECOMPILE=$STARDICT_TOOLS_DIR/stardict2txt

for ifo_file in `ls */*.ifo`
do
  syn_file=`ls $ifo_file|sed s/.ifo/.syn/`;
  if [[ -e $syn_file ]]
  then 
    echo "skipping $ifo_file";
  else 
    echo "processing $ifo_file";
    txt_file=`ls $ifo_file|sed s/.ifo/.txt/`;
    tsv_file=`ls $ifo_file|sed s/.ifo/.tsv/`;
    # $DECOMPILE $ifo_file $txt_file;
    # mv $txt_file $tsv_file
  fi
done
