#! /bin/bash
GIT_MODE=$1 # gp or git pull
set -o verbose
shopt -s expand_aliases
source ~/.bash_aliases
CUR_DIR=$(pwd)
REPOS=(~/stardict-sanskrit/ ~/sanskritnlpjava ~/m17n-db)
INFREQUENT_REPOS=(~/stardict-pali/ ~/stardict-hindi/ ~/stardict-kannada/)
for repo in "${REPOS[@]}"; 
do echo processing: $repo;
cd $repo
$GIT_MODE
done;
cd $CUR_DIR
