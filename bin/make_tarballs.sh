urlbase=$1
set -o verbose

touch tars/tars.MD
rm tars/*
touch tars/tars.MD
for dir in */
do
	base=$(basename "$dir")
	cd $dir
	timestamp=$(stat -c %y *.babylon_final| tr " " "_"|tr ":" "-"|cut -d'.' -f 1)
	if [[ -z $timestamp ]]; then
	  timestamp=$(stat -c %y *.babylon| tr " " "_"|tr ":" "-"|cut -d'.' -f 1)
	fi
	if [[ -z $timestamp ]]; then
	  timestamp=$(stat -c %y *.dict| tr " " "_"|tr ":" "-"|cut -d'.' -f 1)
	fi
	
	tarfile="${base}__${timestamp}.tar.gz"
	if [ -f "${base}.dict.dz" ]; then
	  tar -czf "${tarfile}" `ls *.idx *.dz *.ifo *.syn`
	else
	  tar -czf "${tarfile}" `ls *.idx *.dict *.ifo *.syn`
	fi
	mv "$tarfile" ../tars/
	cd ..
	if [ -f "tars/${tarfile}" ]; then
	  echo "<$urlbase/tars/${tarfile}>" >> tars/tars.MD
	else
	  echo "did not find ${tarfile}"
	fi
done
