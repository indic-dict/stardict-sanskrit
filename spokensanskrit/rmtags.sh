sed -i 's/<b *>//g' ./spokensanskrit4.2.babylon
sed -i 's/<\/b *>//g' ./spokensanskrit4.2.babylon
sed -i 's/<span *[^\n<>]*>//g' ./spokensanskrit4.2.babylon
sed -i 's/<\/span[^\n<>]*>//g' ./spokensanskrit4.2.babylon
sed -i 's/<\/td[^\n<>]*> *<td[^\n<>]*>/    /g' ./spokensanskrit4.2.babylon
sed -i 's/<[\/]*tr[^\n<>]*>/<Q>/g'  ./spokensanskrit4.2.babylon
sed -i 's/<[^\nQ<>]*>//g' ./spokensanskrit4.2.babylon
sed -i 's/> *</></g' ./*4.2.babylon
sed -i 's/<Q>/<br>/g' ./spokensanskrit4.2.babylon
sed -i 's/\&nbsp;/ /g' ./spokensanskrit4.2.babylon 
