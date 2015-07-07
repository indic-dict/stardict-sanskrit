#! /bin/bash
set -o verbose
CUR_DIR=$(pwd)
REPOS=(~/stardict-sanskrit/ ~/sanskritnlpjava ~/m17n-db)
INFREQUENT_REPOS=(~/stardict-pali/ ~/stardict-hindi/ ~/stardict-kannada/)
for repo in "${REPOS[@]}"; 
do echo processing: $repo;
cd $repo
git pull
done;
cd $CUR_DIR
