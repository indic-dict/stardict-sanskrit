Joined files were created using: 
perl -i -pe  's/\.0+/./g' *.txt
perl -i -pe  's/\t/`/g' *.txt
// Replace "(\d+.\d+.\d+) " with \1`
csvjoin -c 1 balamanorama.txt tattvabodhini.txt laghu-kaumudi.txt --outer --delimiter \` --maxfieldsize 500000 >BTL.csv

