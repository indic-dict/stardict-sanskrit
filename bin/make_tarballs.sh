urlbase=$1
set -o verbose

touch tars/tars.MD
rm tars/tars.MD
touch tars/tars.MD
for dir in */
do
	base=$(basename "$dir")
	cd $dir
	timestamp=$(stat -c %y *.dict| tr " " "_"|tr ":" "-"|cut -d'.' -f 1)
	tarfile="${base}__${timestamp}.tar.gz"
	tar -czf "${tarfile}" `ls *.idx *.dict *.ifo *.syn`
	mv "$tarfile" ../tars/
	cd ..
	if [ -f "tars/${tarfile}" ]; then
	  echo "<$urlbase/tars/${tarfile}>" >> tars/tars.MD
	else
	  echo "did not find ${tarfile}"
	fi
done
