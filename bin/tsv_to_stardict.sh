STARDICT_TOOLS_DIR=~/stardict/tools/src/
TABFILE=$STARDICT_TOOLS_DIR/tabfile
BABYLON=$STARDICT_TOOLS_DIR/babylon

# dictunzip */*.dz

for tsv_file in `ls */*.tsv`
do
  syn_file=`ls $tsv_file|sed s/.tsv/.syn/`;
  if [[ -e $syn_file ]]
  then 
    echo "skipping $tsv_file"
  else 
    echo "processing $tsv_file"
    $TABFILE $tsv_file
  fi
done

for babylon_file in `ls */*.babylon.final`
do
    echo "processing $babylon_file"
    $BABYLON $babylon_file
done
