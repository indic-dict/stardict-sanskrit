set -o verbose

for tsv_file in `ls */*.tsv`
do
  echo "processing $tsv_file"
  infile=$tsv_file
  outfile=`ls $tsv_file|sed s/.tsv/.babylon/`;
  cat $infile|perl -pe 's/\n/\n\n/'|perl -pe 's/\n\n\n/\n\n/'|perl -pe 's/\t/\n/'>$outfile
  echo "produced $outfile"
done
