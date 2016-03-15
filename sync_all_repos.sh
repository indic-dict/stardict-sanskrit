#! /bin/bash
GIT_MODE=$1 # gp or git pull
set -o verbose
shopt -s expand_aliases
source ~/.bash_aliases
CUR_DIR=$(pwd)
FREQUENT_REPOS=(~/stardict-sanskrit/ ~/sanskritnlpjava ~/m17n-db )
INFREQUENT_REPOS=(~/stardict-pali/ ~/stardict-hindi/ ~/stardict-kannada/ ~/stardict-telugu/ ~/zim-sa/)
REPOS=(${FREQUENT_REPOS[@]} ${INFREQUENT_REPOS[@]})
for repo in "${REPOS[@]}"; 
do echo processing: $repo;
cd $repo
$GIT_MODE
done;
cd $CUR_DIR
