#! /bin/bash
shopt -s expand_aliases
source ~/.bash_aliases.laptop


GIT_MODE=$1 # gp or git pull
set -o verbose
shopt -s expand_aliases
source ~/.bash_aliases
CUR_DIR=$(pwd)
FREQUENT_REPOS=(~/stardict-sanskrit/ ~/sanskritnlpjava ~/subhAShita-pratimAlA-scala)
INFREQUENT_REPOS=(~/stardict-tamil/ ~/stardict-pali/ ~/stardict-hindi/ ~/stardict-kannada/ ~/stardict-telugu/ ~/zim-sa/ ~/m17n-db ~/subhAShita-db-sanskrit)
REPOS=(${FREQUENT_REPOS[@]} ${INFREQUENT_REPOS[@]})
for repo in "${REPOS[@]}"; 
do echo processing: $repo;
	cd $repo
	echo doing: $GIT_MODE
	$GIT_MODE
done;
cd $CUR_DIR
