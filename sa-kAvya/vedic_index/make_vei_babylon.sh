clear;
read -p "
choose one of fallowing dealing for line breaks of original print book
---------------------------------------------------
1)(default) remove those line breaks of original pdf, and thus allow free reflowing of text on all devices. (doesn't remove paragraphs, indents etc. they will kept intact. only width specific line breaks will be removed).
2)NOT Recommonded. keep those line breaks. it will be useful, when you are simulating original pdf appearance,and width,etc.
1 or 2:" vrvi


sed '/<H>1\. *SANSKRIT INDEX/,$d;' vei.txt|sed '27050,27530d;' | sed '/<H>/d;' |sed 's/<g><\/g>//g;' |sed 1d > mula1.txt
tr '\n' '\t' <mula1.txt|sed 's/\t<P>\([0-9][0-9]* *\. *\)*{@/\n&/g; s/\t//g;' > mula1.1.txt
sed 's/-<>//g; s/<>//g; s/^<P> *\([0-9][0-9]*\) *\. *{@\([^@{}]*[^@{} ,\.:;=-]\)[ ,\.:;=-]*@}.*$/⁛\2_\1⁜⁛\2⁜/; s/^<P> *{@\([^@{}]*[^@{} ,\.:;=-]\)[ ,\.:;=-]*@} *or *{@\([^@{}]*[^@{} ,\.:;=-]\)[ ,\.:;=-]*@}.*$/⁛\1⁜⁛\2⁜/; s/^<P> *{@\([^@{}]*[^@{} ,\.:;=-]\)[ ,\.:;=-]*@}.*$/⁛\1⁜/; s/ *\([,\.-]\) */\1/g;' mula1.1.txt > h_slp1.txt

echo "

Creating headers,and formatting, structure etc.."
sed -i 's/⁛\([^⁛⁜]*\),\([^⁛⁜]*\)⁜/⁛\1⁜⁛\2⁜/g; s/⁛\([^⁛⁜]*\),\([^⁛⁜]*\)⁜/⁛\1⁜⁛\2⁜/g; s/⁛\([^⁛⁜]*\),\([^⁛⁜]*\)⁜/⁛\1⁜⁛\2⁜/g; s/⁛\([^⁛⁜_]*\)-\([^⁛⁜_]*\)⁜/⁛\1-\2⁜⁛\1\2⁜⁛\1⁜⁛\2⁜/g; s/  */ /g; ' h_slp1.txt
sed -i 's/⁛\([^⁛⁜]*[^⁛⁜sS]\)[sS]⁜/&⁛\1⁜/g; s/⁛\([^⁛⁜]*\)i1⁜/&⁛\1i⁜/g;' h_slp1.txt
sed -i.bk 's/⁛.⁜//g; s/⁛.[0-9]⁜//g; /-/s/⁛.[aeiou][0-9]*⁜//g;' h_slp1.txt
tr '[:upper:]' '[:lower:]' < h_slp1.txt > h_slp1.1.txt
sed  's/i10/î/g; s/r21/ṝ/g; s/a10/â/g; s/a11/à/g; s/e11/è/g; s/a1/ā/g; s/a2/ạ/g; s/a4/á/g; s/a7/ä/g; s/d2/ḍ/g; s/e1/ē/g; s/e4/é/g; s/e7/ë/g; s/h2/ḥ/g; s/i1/ī/g; s/i4/í/g; s/i5/ĩ/g; s/l2/ḷ/g; s/m2/ṃ/g; s/m3/ṁ/g; s/n2/ṇ/g; s/n3/ṅ/g; s/n5/ñ/g; s/o1/ō/g; s/o4/ó/g; s/o7/ö/g; s/r2/ṛ/g; s/s2/ṣ/g; s/s3/ṡ/g; s/s4/ś/g; s/t2/ṭ/g; s/u1/ū/g; s/u4/ú/g; s/u7/ü/g; s/A1/Ā/g; s/D2/Ḍ/g; s/E4/É/g; s/H2/Ḥ/g; s/I1/Ī/g; s/M3/Ṁ/g; s/N2/Ṇ/g; s/N3/Ṅ/g; s/N5/Ñ/g; s/R2/Ṛ/g; s/S2/Ṣ/g; s/S4/Ś/g; s/T2/Ṭ/g; s/U1/Ū/g; s/U7/Ü/g; ' h_slp1.1.txt > h_iast1.txt
sed  's/î//g; s/ā/aa/g; s/ḍh/Dh/g;  s/ḍ/D/g; s/ḥ/H/g; s/ī/ii/g; s/ṃ/M/g; s/ṁ/M/g; s/ṇ/N/g; s/ṅ/M/g; s/ñ/~n/g; s/ṛ/R/g; s/ṣ/Sh/g; s/ś/sh/g; s/ṭ/T/g; s/ū/uu/g; s/ch/Ch/g; s/c/ch/g; ' h_iast1.txt > h_ang.txt
sed 's/kh/ख्/g; s/gh/घ्/g; s/Ch/छ्/g; s/ch/च्/g; s/jh/झ्/g; s/~N/ङ्/g; s/~n/ञ्/g; s/Th/ठ्/g; s/Dh/ढ्/g; s/th/थ्/g; s/dh/ध्/g; s/ph/फ्/g; s/bh/भ्/g; s/sh/श्/g; s/Sh/ष्/g; s/zh/ळ्/g; s/k/क्/g; s/g/ग्/g; s/j/ज्/g; s/T/ट्/g; s/D/ड्/g; s/N/ण्/g; s/t/त्/g; s/d/द्/g; s/n/न्/g; s/p/प्/g; s/b/ब्/g; s/m/म्/g; s/y/य्/g; s/r/र्/g; s/l/ल्/g; s/L/ळ्/g; s/v/व्/g; s/s/स्/g; s/h/ह्/g; s/्aa/ा/g; s/्ii/ी/g; s/्uu/ू/g; s/्RR/ॄ/g; s/्ai/ै/g; s/्au/ौ/g; s/्a//g; s/्i/ि/g; s/्u/ु/g; s/्R/ृ/g; s/्e/े/g; s/्o/ो/g; s/M/ं/g; s/H/ः/g; s/aa/आ/g; s/ii/ई/g; s/uu/ऊ/g; s/RR/ॠ/g; s/ai/ऐ/g; s/au/औ/g; s/a/अ/g; s/i/इ/g; s/u/उ/g; s/R/ऋ/g; s/e/ए/g; s/o/ओ/g; s/1/१/g; s/2/२/g; s/3/३/g; s/4/४/g; s/5/५/g; s/6/६/g; s/7/७/g; s/8/८/g; s/9/९/g; s/0/०/g;  ' h_ang.txt > h_deva1.txt
sed 's/⁛[^⁛⁜]*[a-zA-Z][^⁛⁜]*⁜//g; ' h_deva1.txt > h_deva.txt
cp h_ang.txt di.txt;cp h_ang.txt pu.txt; cp h_ang.txt dp.txt; cp h_ang.txt ri.txt ; cp h_ang.txt ru.txt; cp h_ang.txt rri.txt ; cp h_ang.txt xa.txt;cp h_ang.txt mb.txt; 
sed -i.bk 's/uu\|ii/&☁/g; /☁/!s/^.*$//; s/⁛[^⁜⁛☁]*⁜//g;  s/☁//g; s/uu/oo/g; s/ii/ee/g;' ./di.txt
sed -i.bk 's/M[kKgGcCjJTDNtd]/&☁/g; /☁/!s/^.*$//; s/⁛[^⁜⁛☁]*⁜//g; s/☁//g; s/M\([kKgGcCjJTDNtd]\)/n\1/g;' ./pu.txt
sed 's/M[kKgGcCjJTDNtd]/&☁/g; /☁/!s/^.*$//; s/⁛[^⁜⁛☁]*⁜//g; s/☁//g; s/M\([kKgGcCjJTDNtd]\)/n\1/g;' ./di.txt > dp.txt
sed -i 's/R/&☁/g; /☁/!s/^.*$//; s/⁛[^⁜⁛☁]*⁜//g; s/☁//g; s/RR/◑/g; s/R/✣/g;' ./r*[iu].txt
sed -i 's/◑/RI/g; s/✣/Ri/g;' ./ri.txt
sed -i 's/◑/RU/g; s/✣/Ru/g;' ./ru.txt
sed -i 's/◑/RRI/g; s/✣/RRi/g;' ./rri.txt
sed 's/M[kKgGcCjJTDNtd]/&☁/g; /☁/!s/^.*$//; s/⁛[^⁜⁛☁]*⁜//g; s/☁//g; s/M\([kKgGcCjJTDNtd]\)/n\1/g;' ri.txt > r1p.txt
sed 's/M[kKgGcCjJTDNtd]/&☁/g; /☁/!s/^.*$//; s/⁛[^⁜⁛☁]*⁜//g; s/☁//g; s/M\([kKgGcCjJTDNtd]\)/n\1/g;' ru.txt > r2p.txt
sed 's/M[kKgGcCjJTDNtd]/&☁/g; /☁/!s/^.*$//; s/⁛[^⁜⁛☁]*⁜//g; s/☁//g;  s/M\([kKgGcCjJTDNtd]\)/n\1/g;' rri.txt > r3p.txt
sed -i 's/kSh/&☁/g; /☁/!s/^.*$//; s/⁛[^⁜⁛☁]*⁜//g; s/☁//g;   s/kSh/x/g; ' ./xa.txt
sed -i.bk 's/m[pb]/&☁/g; /☁/!s/^.*$//; s/⁛[^⁜⁛☁]*⁜//g; s/☁//g; s/M\([pb]\)/M\1/g;' ./mb.txt
cp h_deva.txt vgpn.txt; cp h_deva.txt dmb.txt;
sed -i.bk 's/ं[कखगघचछजझटठडढणतथदध]/&☁/g; /☁/!s/^.*$//; s/⁛[^⁜⁛☁]*⁜//g; s/☁//g; s/ं\([कखगघ]\)/ङ्\1/g; s/ं\([चछजझ]\)/ञ्\1/g; s/ं\([टठडढण]\)/ण्\1/g; s/ं\([तथदध]\)/न्\1/g; ' ./vgpn.txt
sed -i.bk 's/म्[पफबभ _⁜]/☁&/g; /☁/!s/^.*$//; s/⁛[^⁜⁛☁]*⁜//g; s/☁//g; s/म्\([पफबभ _⁜]\)/ं\1/g; ' ./dmb.txt
paste h_deva.txt vgpn.txt dmb.txt |sed 's/\t//g;' > h_deva_prstrn.txt
sed 's/अ/అ/g; s/आ/ఆ/g; s/इ/ఇ/g; s/ई/ఈ/g; s/उ/ఉ/g; s/ऊ/ఊ/g; s/ऋ/ఋ/g; s/ॠ/ౠ/g; s/ऎ/ఎ/g; s/ए/ఏ/g; s/ऐ/ఐ/g; s/ऒ/ఒ/g; s/ओ/ఓ/g; s/औ/ఔ/g; s/ं/ం/g; s/ः/ః/g; s/क/క/g; s/ख/ఖ/g; s/ग/గ/g; s/घ/ఘ/g; s/ङ/ఙ/g; s/च/చ/g; s/छ/ఛ/g; s/ज/జ/g; s/झ/ఝ/g; s/ञ/ఞ/g; s/ट/ట/g; s/ठ/ఠ/g; s/ड/డ/g; s/ढ/ఢ/g; s/ण/ణ/g; s/त/త/g; s/थ/థ/g; s/द/ద/g; s/ध/ధ/g; s/न/న/g; s/प/ప/g; s/फ/ఫ/g; s/ब/బ/g; s/भ/భ/g; s/म/మ/g; s/य/య/g; s/र/ర/g; s/ल/ల/g; s/ळ/ళ/g; s/व/వ/g; s/श/శ/g; s/ष/ష/g; s/स/స/g; s/ह/హ/g; s/ा/ా/g; s/ि/ి/g; s/ी/ీ/g; s/ु/ు/g; s/ू/ూ/g; s/ृ/ృ/g; s/ॄ/ౄ/g; s/ॆ/ె/g; s/े/ే/g; s/ै/ై/g; s/ॊ/ొ/g; s/ो/ో/g; s/ौ/ౌ/g; s/१/౧/g; s/२/౨/g; s/३/౩/g; s/४/౪/g; s/५/౫/g; s/६/౬/g; s/७/౭/g; s/८/౮/g; s/९/౯/g; s/०/౦/g;  s/्/్/g; ' h_deva_prstrn.txt > h_telugu_prstrn.txt
sed 's/अ/ಅ/g; s/आ/ಆ/g; s/इ/ಇ/g; s/ई/ಈ/g; s/उ/ಉ/g; s/ऊ/ಊ/g; s/ऋ/ಋ/g; s/ॠ/ೠ/g; s/ऎ/ಎ/g; s/ए/ಏ/g; s/ऐ/ಐ/g; s/ऒ/ಒ/g; s/ओ/ಓ/g; s/औ/ಔ/g; s/ं/ಂ/g; s/ः/ಃ/g; s/क/ಕ/g; s/ख/ಖ/g; s/ग/ಗ/g; s/घ/ಘ/g; s/ङ/ಙ/g; s/च/ಚ/g; s/छ/ಛ/g; s/ज/ಜ/g; s/झ/ಝ/g; s/ञ/ಞ/g; s/ट/ಟ/g; s/ठ/ಠ/g; s/ड/ಡ/g; s/ढ/ಢ/g; s/ण/ಣ/g; s/त/ತ/g; s/थ/ಥ/g; s/द/ದ/g; s/ध/ಧ/g; s/न/ನ/g; s/प/ಪ/g; s/फ/ಫ/g; s/ब/ಬ/g; s/भ/ಭ/g; s/म/ಮ/g; s/य/ಯ/g; s/र/ರ/g; s/ल/ಲ/g; s/ळ/ಳ/g; s/व/ವ/g; s/श/ಶ/g; s/ष/ಷ/g; s/स/ಸ/g; s/ह/ಹ/g; s/ा/ಾ/g; s/ि/ಿ/g; s/ी/ೀ/g; s/ु/ು/g; s/ू/ೂ/g; s/ृ/ೃ/g; s/ॄ/ೄ/g; s/ॆ/ೆ/g; s/े/ೇ/g; s/ै/ೈ/g; s/ॊ/ೊ/g; s/ो/ೋ/g; s/ौ/ೌ/g; s/१/೧/g; s/२/೨/g; s/३/೩/g; s/४/೪/g; s/५/೫/g; s/६/೬/g; s/७/೭/g; s/८/೮/g; s/९/೯/g; s/०/೦/g; s/्/್/g; ' ./h_deva_prstrn.txt > h_kannada_prstrn.txt
paste h_deva_prstrn.txt h_telugu_prstrn.txt  h_kannada_prstrn.txt h_iast1.txt h_ang.txt di.txt pu.txt dp.txt ri.txt ru.txt rri.txt r1p.txt r2p.txt r3p.txt xa.txt mb.txt | sed 's/\t//g;' > heads_raw.txt
cp heads_raw.txt heads_duprmd.txt 
for((i=1;i<=5;i++)); do
sed -i.bk 's/ *\([⁛⁜]\) */\1/g; s/⁛\([^⁛⁜]*\)⁜\(.*\)⁛\1⁜/⁛\1⁜\2/g; ' heads_duprmd.txt
done



sed 's/< *F *> */♛/g; s/< *\/F *>/♝/g; s/♛♝//g; s/♛1)\([^♛♝]*♝\)\([^♛♝]*\)♛\([^♛♝0-9]\)/♛1)\1\2♛2)\3/;s/$/◑/;' mula1.1.txt > mula1.2.txt
for((i=1;i<=200;i++));do sed -i 's/^\([^◑]*\)♛\([0-9][0-9]*\)\([ ()]*\)\([^♛♝◑]*\)♝\([^♛♝◑]*\)◑/\1☀\2☆\5◑♛\2) \4♝/;' mula1.2.txt ; done;

sed 's/☀1☆/❄&/g; s/☀1☆\([^◑❄]*\)❄☀1☆/☀1☆\1❄☀1b☆/; s/♛1)/↰&/g;  s/♛1)\([^↰]*\)↰♛1)/♛1)\1↰♛1b)/;  s/[❄↰]//g; ' mula1.2.txt >mula1.3.txt
for((i=1;i<=200;i++));do sed -i 's/☀1b☆\([^◑]*\)☀\([0-9][0-9]*\)☆/☀1b☆\1☀\2b☆/; s/♛1b)\(.*\)♛\([0-9][0-9]*\))/♛1b)\1♛\2b)/;' mula1.3.txt ; done;
#for((i=1;i<=200;i++)); do  sed -i 's/☀1b☆\([^◑]*\)☀\([0-9][0-9]*\)☆/☀1b☆\1☀\2b☆/;' mula1.3.txt; sed -i 's/♛1b)\(.*\)♛\([0-9][0-9]*\))/♛1b)\1♛\2b)/;' mula1.3.txt ; done;
sed -i 's/☀1b☆\([^◑]*\)☀1b☆/☀1b☆\1☀1c☆/;   s/♛1b)\(.*\)♛1b)/♛1b)\1♛1c)/; ' mula1.3.txt
for((i=1;i<=200;i++));do sed -i 's/☀1c☆\([^◑]*\)☀\([0-9][0-9]*\)b☆/☀1c☆\1☀\2c☆/; s/♛1c)\(.*\)♛\([0-9][0-9]*\)b)/♛1c)\1♛\2c)/;' mula1.3.txt ; done;
sed -i 's/^/<div class="vei_vivaraNa">/; s/◑♛\([0-9][0-9]*\)) /<\/div_vivaraNa>◑<div class="vei_footnts"><ol class="vei_oftn" start="\1">♛\1) /; s/◑$/<\/div_vivaraNa>&/; s/♛1\([bc]\))/<\/ol><ol class="vei_oft\1" start="1">&/g; /<ol /s/$/<\/ol>/; /♛/s/$/<\/div_footnts">/;                    s/♛\([0-9][0-9]*[bc]*\))/<\/li><li id="fx\1_vei_"><a class="vei_ft" href="#ref\1_vei_">↑<\/a>/g; s/\(<ol[^<>]*>\)<\/li>/\1/g; s/<\/ol>/<\/li><\/ol>/g; s/☀\([^☀☆]*\)☆/<sup class="vei_fref" id="ref\1_vei_"><a class="vei_ft" href="#fx\1_vei_">\1<\/a><\/sup>/g; s/♝<\/li>/<\/li>/g;' mula1.3.txt
j=$(cat mula1.3.txt|wc -l)
for((i=1;i<=$((j+1));i++)); do sed -i "${i}s/_vei_/&${i}/g;" mula1.3.txt; done;

sed 's/<>/↰/g; s/{@\([^{@]*\)@}/<b>\1<\/b>/g; s/{%\([^{%]*\)%}/<i>\1<\/i>/g; s/<P>/<\/p><p>/g; s/<\/p><p>/<p>/; /<p>/s/<\/div_vivaraNa>/<\/p>&/;  ' mula1.3.txt > mula1.4.txt
sed -i '/<C/s/ *\.\.\.[ \.]*//g; /<C/s/<\/p>/☁/g; /<C/s/<p>/♞/g; s/<C1>/↷<\/td><\/tr><tr class="vei_paTTika"><td class="vei_paTTika">/g; s/\(<C[0-9]>\)/↷<\/td><td  class="vei_paTTika">/g; s/\(<td [^<>]*>\)\([^↷☁♞]*\)\([☁♞]\)/\1\2<\/td><\/tr><\/table>/g; s/\([☁♞]\)\([^☁♞↷]*\)↷<\/td><\/tr>/\1\2<table class="vei_paTTika">/g; s/☁/<\/p>/g; s/♞/<p>/g; s/↷//g; s/<p>/<p class="vei_para">/g;' mula1.4.txt

sed 's/\[ *Page\([12]\)\([^][]*\)\]/\[Page_\1\2\]/g; s/i10/î/g; s/r21/ṝ/g; s/a10/â/g; s/a11/à/g; s/e11/è/g; s/a1/ā/g; s/a2/ạ/g; s/a4/á/g; s/a7/ä/g; s/d2/ḍ/g; s/e1/ē/g; s/e4/é/g; s/e7/ë/g; s/h2/ḥ/g; s/i1/ī/g; s/i4/í/g; s/i5/ĩ/g; s/l2/ḷ/g; s/m2/ṃ/g; s/m3/ṁ/g; s/n2/ṇ/g; s/n3/ṅ/g; s/n5/ñ/g; s/o1/ō/g; s/o4/ó/g; s/o7ö//g; s/r2/ṛ/g; s/s2/ṣ/g; s/s3/ṡ/g; s/s4/ś/g; s/t2/ṭ/g; s/u1/ū/g; s/u4/ú/g; s/u7/ü/g; s/A1/Ā/g; s/D2/Ḍ/g; s/E4/É/g; s/H2/Ḥ/g; s/I1/Ī/g; s/M3/Ṁ/g; s/N2/Ṇ/g; s/N3/Ṅ/g; s/N5/Ñ/g; s/R2/Ṛ/g; s/S2/Ṣ/g; s/S4/Ś/g; s/T2/Ṭ/g; s/U1/Ū/g; s/U7/Ü/g; s/<b>/&___/; s/<\/b>/___&/; s/\([ ,\.:-]*\)___<\/b>/___\1<\/b>/;' mula1.4.txt > mula1.5.txt
if((vrvi==1));then sed -i 's/- *\(\[ *Page[^][]*\]\)↰/ \1/g; s/-↰//g; s/↰/ /g; ' mula1.5.txt ; elif((vrvi==2)); then sed -i 's/- *\(\[ *Page[^][]*\]\)↰/-↰\1/g; s/↰/ <BR> /g;' mula1.5.txt;else echo "The answer is UNKNOWN."; fi

echo "Creating hyperlinks, may take time."
sed  "s/⁜/&\n/g; s/'//g; s/⁛⁜//g;" h_iast1.txt |sed '/_\|^ *$\|⁛div⁜/d;'>tatkal1 && sed  "s/⁜/&\n/g; s/'//g; s/⁛⁜//g;" h_deva1.txt |sed '/_\|^ *$\|⁛दिव्⁜/d;' > tatkal2 
while read -r line; do echo ${#line}; done < tatkal1 > pl.txt && paste pl.txt tatkal1 > tatkal1.1
paste tatkal1.1 tatkal2 |sed 's/\t//2; /⁜⁛[^⁛⁜]*[a-zA-Z][^⁛⁜]*⁜\|^[1234]\t/d;' |sort -u|sort -nr |sed 's/^[0-9]*\t//;' |sed 's/⁛\([^⁜⁛]*\)⁜⁛\([^⁜⁛]*\)⁜/sed -i\.bk "s\/\\(\[ ,\\.(){}\\":;<>=\\\&?♞♛♜♝-\]\\)\1\\(\[ ,\\.(){}\\":;<>=\\\&?♞♛♜♝-\]\\)\/\\1<a class="vei_a_pada" href=\"\2\">✔\1☁✔_\2☁<\\\/a>\\2\/Ig;" mula1.5\.txt/g;' |sed 's/sed -i\.bk "/sed -i #/; s/" mula1.5/# mula1.5/;'|sed "s/#/'/g;"  |bash
sed -i 's/___//; s/___//; ' ./mula1.5.txt

echo "extracting page info,and linking"
sed ' s/\[ *Page_[12]-title[^][]*\]//g;     s/^.*\[Page_ *\([12]\) *- *\([0-9][0-9]*\) *+.*$/P\1\2/;' mula1.5.txt |sed '/^P/!s/^.*$/⁜/;1s/^/P1000\n/;' | tr '\n' '\t' |sed 's/\tP/\nP/g; s/$/\t/; s/\t\t*/\t/g; s/\t$//; ' | sed ' s/$/\t⁜/;'  >putachitta1
for((i=1;i<=30;i++)); do
sed -i "s/^\(P[0-9][0-9]*\)\([^⁜]*\)\t⁜/\1\2\t\1_s${i}/g;" ./putachitta1
done
sed 's/\t.*$//;' putachitta1 >ps1 && sed '$d; 1s/^/\n/; ' ps1 > tatkal1 && sed '$s/$/\n/; 1d;' ps1 > tatkal2; sed 's/^[^\t]*\t//;' putachitta1 > tatkal3 && paste tatkal1 tatkal2 tatkal3 |sed 's/\t/_/; s/\t/\t⁜/g;' > putachitta2
for((i=1;i<=30;i++)); do
sed -i "s/^\([^\t]*\)\([^⁜]*\)\t⁜/\1\2\t\1_/g;" ./putachitta2
done
sed 's/^[^\t]*\t//; s/\t/\n/g;' putachitta2 |cat -n|sed 's/^ *//; s/\t/_/;  /s/!d; ' > puta0
sed 's/_.*$//;' puta0 >ps1 && sed '$d; 1s/^/\n/; ' ps1 > tatkal1 && sed '$s/$/\n/; 1d;' ps1 > tatkal2; sed 's/^[^\t]*\t//;' puta0 > tatkal3 && paste tatkal1 tatkal2 tatkal3 |sed 's/\t/_/g;'> puta.txt

echo "summing up"
j=$(cat mula1.5.txt|wc -l) && k=$((j+1))
sed "s/P1000_/P1001_/g; ${k},$ d;" puta.txt| sed 's/^\([^_]*\)_\([^_]*\)_\([^_]*\)_P*\([0-9]*\)_P*\([0-9]*\)_P*\([0-9]*\)_s\([^_]*\)/<div class="vei_shiraputa"><a class="vei_a_pglink" href="_vei_@\1">«<\/a>pada\7<a class="vei_a_pglink" href="_vei_@\2">»<\/a><br><a class="vei_a_pglink" href="_vei_pn\4">↶<\/a><a class="vei_a_pglink" href="_vei_pn\6">P\6<\/a><a class="vei_a_pglink" href="_vei_pn\5">↷<\/a><\/div><div class="floatclr" style="clear:left;"><hr><\/div>/;    s/vei_pn\([12]\)\([0-9]*\)/vei\1_pn\2/g; s/>P\([0-9]\)\([0-9]*\)<\/a>/>\1:\2<\/a>/;   ' > shirshika2p.txt

sed 's/^\([^_]*\)_\([^_]*\)_\([^_]*\)_P*\([0-9]*\)_P*\([0-9]*\)_P*\([0-9]*\)_s\([^_]*\)/⁛_vei_@\3⁜⁛_vei_pn\6⁜/; s/vei_pn\([12]\)\([0-9]*\)/vei\1_pn\2/g;   ' puta.txt > tatkal1 && sed 's/⁛⁜//g;' heads_duprmd.txt > tatkal2
paste tatkal2 tatkal1|sed 's/\t//; s/⁜⁛/|/g; s/[⁜⁛]//g;'| sed 's/_\([१२३४५६७८९०][१२३४५६७८९0]*\)//;' > heads_siddhanta.txt
sed 's/^⁛\([^⁜⁛]*\)⁜.*$/<div class="vei_patra"><div class="vei_shiram"><h2> \1<\/h2><\/div>/; s/_\([१२३४५६७८९०][१२३४५६७८९0]*\)/ ।\1/;' h_deva.txt > mula1.5p1.txt
sed 's/$/<\/div>/; s/◑//; ' mula1.5.txt > mula1.5p3.txt
paste mula1.5p1.txt shirshika2p.txt mula1.5p3.txt |sed 's/\t//g;' > mula2.txt
sed 's/^/\n\n/; s/<\/div[^<>]*>/<\/div>/g;  s/<div class="vei_footnts">/<i>Foot notes<\/i><hr noshade size=1 color=black>/; s/\[ *Page\([^][]*\)\]//g;' mula2.txt|sed '1d' > tatkal1 && sed 's/$/\n\n/;'  heads_siddhanta.txt > tatkal2 && paste tatkal1 tatkal2|sed 's/\t//g;' |sed '1s/^/\n#stripmethod=keep\n#sametypesequence=h\n#bookname=vedic_index\n#అమ్మ కి\n\n/;' > mula_vedic_index.babylon

sed -i 's/✔_[^✔☁]*☁//g; s/[✔☁]//g;' mula_vedic_index.babylon
sed -i '8~3s/^/<style>div.vei_patra{background: #FDD5B1;margin:0 auto 0 auto;padding:1em;max-width:55.8em;font-size: 1em;line-height:1.5em;}div.vei_shiram{font-style:bold;padding-bottom:0.3em;max-width:20em;text-align:left;float:left;height:3em;} div.vei_shiraputa{ max-width:15em; text-align:right; float:right; } p.vei_para{text-indent: 2em;}  a.vei_a_pglink,a.vei_ft,a.vei_a_pada{text-decoration:none; color:#664427;}a.vei_ft{color: #097168;}a.pe_a_pada:hover{ text-decoration: underline;}div.vei_footnts{font-size: 0.9em;}:target {background: #ccc; border: solid 1px #aaa; }<\/style>/;' mula_vedic_index.babylon


lanke=1;styli=2;devai=0;
sed  's/^.*<div class="vei_shiraputa">//; s/<\/div>$//; s/<br><a class="vei_a_pglink"/ :::: <a class="vei_a_pglink"/; s/<\/div><div class="floatclr" style="clear:left;"><hr><\/div><div class="vei_vivaraNa">/<hr>/; s/<\/div><i>Foot notes/<i>Foot notes/; s/ class="[^<>"]*"//g; s/<a/<a class="vei_a"/g; 8~3s/^/<style>a.vei_a{text-decoration:none;}:target{background: #ccc;border:solid 1px #aaa;}<\/style>/; ' mula_vedic_index.babylon >vedic_index.babylon
sed -i "s/id=\"fx/id=\"fx_${lanke}${styli}${devai}_/g; s/id=\"ref/id=\"ref_${lanke}${styli}${devai}_/g; s/href=\"#fx/href=\"#fx_${lanke}${styli}${devai}_/g; s/href=\"#ref/href=\"#ref_${lanke}${styli}${devai}_/g;   s/\([\"_\.]\)vei_/\1vei_${lanke}${styli}${devai}/g;    " vedic_index.babylon

lanke=1;styli=1;devai=0;
sed  "s/id=\"fx/id=\"fx_${lanke}${styli}${devai}_/g; s/id=\"ref/id=\"ref_${lanke}${styli}${devai}_/g; s/href=\"#fx/href=\"#fx_${lanke}${styli}${devai}_/g; s/href=\"#ref/href=\"#ref_${lanke}${styli}${devai}_/g;   s/\([\"_\.]\)vei_/\1vei_${lanke}${styli}${devai}/g;    " mula_vedic_index.babylon > vedic_index_styled.babylon

echo done. mula_vedic_index.babylon is mula of babylon files from which we can create customized babylon file with vei_babylon_customiser.sh script. present execution of make already creates default versions vedic_index.babylon, which is minimal one; and vedic_index_styled.babylon , which is styled one. we can create own customised file with options of devanagari links, or styling etc, using mula_vedic_index.babylon as source and vei_babylon_customiser.sh as script by replying it's prompts.











































































