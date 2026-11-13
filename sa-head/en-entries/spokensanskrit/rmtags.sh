sed -i 's/<b *>//g' ./spokensanskrit.babylon
sed -i 's/<\/b *>//g' ./spokensanskrit.babylon
sed -i 's/<span *[^\n<>]*>//g' ./spokensanskrit.babylon
sed -i 's/<\/span[^\n<>]*>//g' ./spokensanskrit.babylon
sed -i 's/<\/td[^\n<>]*> *<td[^\n<>]*>/    /g' ./spokensanskrit.babylon
sed -i 's/<[\/]*tr[^\n<>]*>/<Q>/g'  ./spokensanskrit.babylon
sed -i 's/<[^\nQ<>]*>//g' ./spokensanskrit.babylon
sed -i 's/> *</></g' ./*.babylon
sed -i 's/<Q>/<br>/g' ./spokensanskrit.babylon
sed -i 's/\&nbsp;/ /g' ./spokensanskrit.babylon 
