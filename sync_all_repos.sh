#! /bin/bash
set -o verbose
shopt -s expand_aliases
source ~/.bash_aliases
CUR_DIR=$(pwd)
REPOS=(~/stardict-sanskrit/ ~/sanskritnlpjava ~/m17n-db)
INFREQUENT_REPOS=(~/stardict-pali/ ~/stardict-hindi/ ~/stardict-kannada/)
for repo in "${REPOS[@]}"; 
do echo processing: $repo;
cd $repo
$1
done;
cd $CUR_DIR
