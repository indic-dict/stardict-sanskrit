STARDICT_TOOLS_DIR=~/stardict/tools/src/
TABFILE=$STARDICT_TOOLS_DIR/tabfile
BABYLON=$STARDICT_TOOLS_DIR/babylon

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

for babylon_file in `ls */*.babylon*`
do
  final_file=`ls $babylon_file|sed s/\.babylon$/.babylon_final/`;
  if [[ -e $final_file ]]
  then 
    echo "processing $final_file"
    $BABYLON $final_file
  else 
    echo "processing $babylon_file"
    $BABYLON $babylon_file
  fi
done

dictunzip */*.dz
