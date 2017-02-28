sed '/<H>GENEALOGY/,$d; /^<H>/d; /^<>{@A@}/d;; ' pe.txt |tr '\n' '\t' | sed 's/<HI>\([^A-Z]\)/<HI1>\1/g; s/\t<HI>/\n<HI>/g;s/\t//g;' > mula1.txt
sed 's/-<>//g; s/<>//g; s/^<HI>\([^\.<>a-z]*\)\([ \.,;<>:]\).*$/\1/; s/[;:,\. ]*$//; s/  */ /g; s/^/⁛/; s/$/⁜/; s/ \([^XVI]\)⁜/⁜/;    s/ I \(([^()⁜⁛]*)\)\( I\)*/ \1 I/;       s/⁛\([^⁜⁛]*\) \([XVI1][XVI]*\)⁜/⁛\1 \2⁜⁛\1⁜/; s/ VIII⁜/_8⁜/; s/ XIII⁜/_13⁜/; s/ XII⁜/_12⁜/; s/ XIV⁜/_14⁜/; s/ XV⁜/_15⁜/; s/ XI⁜/_11⁜/;  s/ VII/_7⁜/; s/ VI⁜/_6⁜/; s/ IX⁜/_9⁜/; s/ IV⁜/_4⁜/; s/ III⁜/_3⁜/; s/ II⁜/_2⁜/; s/ X⁜/_10⁜/; s/ I⁜/_1⁜/; s/ V⁜/_5⁜/; s/ 1⁜/_1⁜/; s/ ({.*$//;  s/ *(III*)//g;    s/⁛\([^⁜⁛]*\) *( *\([HSM][0-9]*\) *)\([^⁜⁛]*\)⁜/⁛\1\2\3⁜⁛\1\3⁜/g;   s/(\([^⁜⁛()]*\)⁜/(\1)⁜/g;              s/⁛\([^⁜⁛]*\) *(\([^()⁜⁛]*\))\(_[0-9]*\)*⁜/⁛\1\3⁜⁛\2\3⁜/g; s/ *\([⁜⁛]\) */\1/g; s/ *, *⁜/⁜/g; s/ *, */⁜⁛/g; s/ *{??} *//g; s/¤//g; s/ *; */⁜⁛/g; s/⁜*$/⁜/;   s/⁛\([^⁜⁛]*\) \([XVI1][XVI]*\)⁜/⁛\1 \2⁜⁛\1⁜/; s/ VIII⁜/_8⁜/; s/ XIII⁜/_13⁜/; s/ XII⁜/_12⁜/; s/ XIV⁜/_14⁜/; s/ XV⁜/_15⁜/; s/ XI⁜/_11⁜/;  s/ VII/_7⁜/; s/ VI⁜/_6⁜/; s/ IX⁜/_9⁜/; s/ IV⁜/_4⁜/; s/ III⁜/_3⁜/; s/ II⁜/_2⁜/; s/ X⁜/_10⁜/; s/ I⁜/_1⁜/; s/ V⁜/_5⁜/; s/ 1⁜/_1⁜/; s/ _/_/g;  ' mula1.txt >h_slp1.txt
tr '[:upper:]' '[:lower:]' < h_slp1.txt > h_slp1.1.txt
sed  's/i10/î/g; s/r21/ṝ/g; s/a10/â/g; s/a11/à/g; s/e11/è/g; s/a1/ā/g; s/a2/ạ/g; s/a4/á/g; s/a7/ä/g; s/d2/ḍ/g; s/e1/ē/g; s/e4/é/g; s/e7/ë/g; s/h2/ḥ/g; s/i1/ī/g; s/i4/í/g; s/i5/ĩ/g; s/l2/ḷ/g; s/m2/ṃ/g; s/m3/ṁ/g; s/n2/ṇ/g; s/n3/ṅ/g; s/n5/ñ/g; s/o1/ō/g; s/o4/ó/g; s/o7ö//g; s/r2/ṛ/g; s/s2/ṣ/g; s/s3/ṡ/g; s/s4/ś/g; s/t2/ṭ/g; s/u1/ū/g; s/u4/ú/g; s/u7/ü/g; s/A1/Ā/g; s/D2/Ḍ/g; s/E4/É/g; s/H2/Ḥ/g; s/I1/Ī/g; s/M3/Ṁ/g; s/N2/Ṇ/g; s/N3/Ṅ/g; s/N5/Ñ/g; s/R2/Ṛ/g; s/S2/Ṣ/g; s/S4/Ś/g; s/T2/Ṭ/g; s/U1/Ū/g; s/U7/Ü/g; ' h_slp1.1.txt > h_iast1.txt
sed  's/î//g; s/ā/aa/g; s/ḍh/Dh/g;  s/ḍ/D/g; s/ḥ/H/g; s/ī/ii/g; s/ṃ/M/g; s/ṁ/M/g; s/ṇ/N/g; s/ṅ/M/g; s/ñ/~n/g; s/ṛ/R/g; s/ṣ/Sh/g; s/ś/sh/g; s/ṭ/T/g; s/ū/uu/g; s/ch/Ch/g; s/c/ch/g; ' h_iast1.txt > h_ang.txt
sed 's/kh/ख्/g; s/gh/घ्/g; s/Ch/छ्/g; s/ch/च्/g; s/jh/झ्/g; s/~N/ङ्/g; s/~n/ञ्/g; s/Th/ठ्/g; s/Dh/ढ्/g; s/th/थ्/g; s/dh/ध्/g; s/ph/फ्/g; s/bh/भ्/g; s/sh/श्/g; s/Sh/ष्/g; s/zh/ळ्/g; s/k/क्/g; s/g/ग्/g; s/j/ज्/g; s/T/ट्/g; s/D/ड्/g; s/N/ण्/g; s/t/त्/g; s/d/द्/g; s/n/न्/g; s/p/प्/g; s/b/ब्/g; s/m/म्/g; s/y/य्/g; s/r/र्/g; s/l/ल्/g; s/L/ळ्/g; s/v/व्/g; s/s/स्/g; s/h/ह्/g; s/्aa/ा/g; s/्ii/ी/g; s/्uu/ू/g; s/्RR/ॄ/g; s/्ai/ै/g; s/्au/ौ/g; s/्a//g; s/्i/ि/g; s/्u/ु/g; s/्R/ृ/g; s/्e/े/g; s/्o/ो/g; s/M/ं/g; s/H/ः/g; s/aa/आ/g; s/ii/ई/g; s/uu/ऊ/g; s/RR/ॠ/g; s/ai/ऐ/g; s/au/औ/g; s/a/अ/g; s/i/इ/g; s/u/उ/g; s/R/ऋ/g; s/e/ए/g; s/o/ओ/g; s/1/१/g; s/2/२/g; s/3/३/g; s/4/४/g; s/5/५/g; s/6/६/g; s/7/७/g; s/8/८/g; s/9/९/g; s/0/०/g;  ' h_ang.txt > h_deva1.txt
sed 's/⁛[^⁛⁜]*[a-zA-Z][^⁛⁜]*⁜//g; ' h_deva1.txt > h_deva.txt
cp h_ang.txt di.txt;cp h_ang.txt pu.txt; cp h_ang.txt dp.txt; cp h_ang.txt ri.txt ; cp h_ang.txt ru.txt; cp h_ang.txt rri.txt ; cp h_ang.txt xa.txt;cp h_ang.txt mb.txt; 
sed -i 's/uu\|ii/&☁/g; /☁/!s/^.*$//; s/⁛[^⁜⁛☁]*⁜//g;  s/☁//g; s/uu/oo/g; s/ii/ee/g;' ./di.txt
sed -i 's/M[kKgGcCjJTDNtd]/&☁/g; /☁/!s/^.*$//; s/⁛[^⁜⁛☁]*⁜//g; s/☁//g; s/M\([kKgGcCjJTDNtd]\)/n\1/g;' ./pu.txt
sed 's/M[kKgGcCjJTDNtd]/&☁/g; /☁/!s/^.*$//; s/⁛[^⁜⁛☁]*⁜//g; s/☁//g; s/M\([kKgGcCjJTDNtd]\)/n\1/g;' ./di.txt > dp.txt
sed -i 's/R/&☁/g; /☁/!s/^.*$//; s/⁛[^⁜⁛☁]*⁜//g; s/☁//g; s/RR/◑/g; s/R/✣/g;' ./r*[iu].txt
sed -i 's/◑/RI/g; s/✣/Ri/g;' ./ri.txt
sed -i 's/◑/RU/g; s/✣/Ru/g;' ./ru.txt
sed -i 's/◑/RRI/g; s/✣/RRi/g;' ./rri.txt
sed 's/M[kKgGcCjJTDNtd]/&☁/g; /☁/!s/^.*$//; s/⁛[^⁜⁛☁]*⁜//g; s/☁//g; s/M\([kKgGcCjJTDNtd]\)/n\1/g;' ri.txt > r1p.txt
sed 's/M[kKgGcCjJTDNtd]/&☁/g; /☁/!s/^.*$//; s/⁛[^⁜⁛☁]*⁜//g; s/☁//g; s/M\([kKgGcCjJTDNtd]\)/n\1/g;' ru.txt > r2p.txt
sed 's/M[kKgGcCjJTDNtd]/&☁/g; /☁/!s/^.*$//; s/⁛[^⁜⁛☁]*⁜//g; s/☁//g;  s/M\([kKgGcCjJTDNtd]\)/n\1/g;' rri.txt > r3p.txt
sed -i 's/kSh/&☁/g; /☁/!s/^.*$//; s/⁛[^⁜⁛☁]*⁜//g; s/☁//g;   s/kSh/x/g; ' ./xa.txt
sed -i 's/m[pb]/&☁/g; /☁/!s/^.*$//; s/⁛[^⁜⁛☁]*⁜//g; s/☁//g; s/M\([pb]\)/M\1/g;' ./mb.txt
cp h_deva.txt vgpn.txt; cp h_deva.txt dmb.txt;
sed -i 's/ं[कखगघचछजझटठडढणतथदध]/&☁/g; /☁/!s/^.*$//; s/⁛[^⁜⁛☁]*⁜//g; s/☁//g; s/ं\([कखगघ]\)/ङ्\1/g; s/ं\([चछजझ]\)/ञ्\1/g; s/ं\([टठडढण]\)/ण्\1/g; s/ं\([तथदध]\)/न्\1/g; ' ./vgpn.txt
sed -i 's/म्[पफबभ _⁜]/☁&/g; /☁/!s/^.*$//; s/⁛[^⁜⁛☁]*⁜//g; s/☁//g; s/म्\([पफबभ _⁜]\)/ं\1/g; ' ./dmb.txt
paste h_deva.txt vgpn.txt dmb.txt |sed 's/\t//g;' > h_deva_prstrn.txt
sed 's/अ/అ/g; s/आ/ఆ/g; s/इ/ఇ/g; s/ई/ఈ/g; s/उ/ఉ/g; s/ऊ/ఊ/g; s/ऋ/ఋ/g; s/ॠ/ౠ/g; s/ऎ/ఎ/g; s/ए/ఏ/g; s/ऐ/ఐ/g; s/ऒ/ఒ/g; s/ओ/ఓ/g; s/औ/ఔ/g; s/ं/ం/g; s/ः/ః/g; s/क/క/g; s/ख/ఖ/g; s/ग/గ/g; s/घ/ఘ/g; s/ङ/ఙ/g; s/च/చ/g; s/छ/ఛ/g; s/ज/జ/g; s/झ/ఝ/g; s/ञ/ఞ/g; s/ट/ట/g; s/ठ/ఠ/g; s/ड/డ/g; s/ढ/ఢ/g; s/ण/ణ/g; s/त/త/g; s/थ/థ/g; s/द/ద/g; s/ध/ధ/g; s/न/న/g; s/प/ప/g; s/फ/ఫ/g; s/ब/బ/g; s/भ/భ/g; s/म/మ/g; s/य/య/g; s/र/ర/g; s/ल/ల/g; s/ळ/ళ/g; s/व/వ/g; s/श/శ/g; s/ष/ష/g; s/स/స/g; s/ह/హ/g; s/ा/ా/g; s/ि/ి/g; s/ी/ీ/g; s/ु/ు/g; s/ू/ూ/g; s/ृ/ృ/g; s/ॄ/ౄ/g; s/ॆ/ె/g; s/े/ే/g; s/ै/ై/g; s/ॊ/ొ/g; s/ो/ో/g; s/ौ/ౌ/g; s/१/౧/g; s/२/౨/g; s/३/౩/g; s/४/౪/g; s/५/౫/g; s/६/౬/g; s/७/౭/g; s/८/౮/g; s/९/౯/g; s/०/౦/g;  s/्/్/g; ' h_deva_prstrn.txt > h_telugu_prstrn.txt
sed 's/अ/ಅ/g; s/आ/ಆ/g; s/इ/ಇ/g; s/ई/ಈ/g; s/उ/ಉ/g; s/ऊ/ಊ/g; s/ऋ/ಋ/g; s/ॠ/ೠ/g; s/ऎ/ಎ/g; s/ए/ಏ/g; s/ऐ/ಐ/g; s/ऒ/ಒ/g; s/ओ/ಓ/g; s/औ/ಔ/g; s/ं/ಂ/g; s/ः/ಃ/g; s/क/ಕ/g; s/ख/ಖ/g; s/ग/ಗ/g; s/घ/ಘ/g; s/ङ/ಙ/g; s/च/ಚ/g; s/छ/ಛ/g; s/ज/ಜ/g; s/झ/ಝ/g; s/ञ/ಞ/g; s/ट/ಟ/g; s/ठ/ಠ/g; s/ड/ಡ/g; s/ढ/ಢ/g; s/ण/ಣ/g; s/त/ತ/g; s/थ/ಥ/g; s/द/ದ/g; s/ध/ಧ/g; s/न/ನ/g; s/प/ಪ/g; s/फ/ಫ/g; s/ब/ಬ/g; s/भ/ಭ/g; s/म/ಮ/g; s/य/ಯ/g; s/र/ರ/g; s/ल/ಲ/g; s/ळ/ಳ/g; s/व/ವ/g; s/श/ಶ/g; s/ष/ಷ/g; s/स/ಸ/g; s/ह/ಹ/g; s/ा/ಾ/g; s/ि/ಿ/g; s/ी/ೀ/g; s/ु/ು/g; s/ू/ೂ/g; s/ृ/ೃ/g; s/ॄ/ೄ/g; s/ॆ/ೆ/g; s/े/ೇ/g; s/ै/ೈ/g; s/ॊ/ೊ/g; s/ो/ೋ/g; s/ौ/ೌ/g; s/१/೧/g; s/२/೨/g; s/३/೩/g; s/४/೪/g; s/५/೫/g; s/६/೬/g; s/७/೭/g; s/८/೮/g; s/९/೯/g; s/०/೦/g; s/्/್/g; ' ./h_deva_prstrn.txt > h_kannada_prstrn.txt
paste h_deva_prstrn.txt h_telugu_prstrn.txt  h_kannada_prstrn.txt h_iast1.txt h_ang.txt di.txt pu.txt dp.txt ri.txt ru.txt rri.txt r1p.txt r2p.txt r3p.txt xa.txt mb.txt | sed 's/\t//g;' > heads_raw.txt
cp heads_raw.txt heads_duprmd.txt 
for((i=1;i<=5;i++)); do
sed -i 's/ *\([⁛⁜]\) */\1/g; s/⁛\([^⁛⁜]*\)⁜\(.*\)⁛\1⁜/⁛\1⁜\2/g; ' heads_duprmd.txt
done
sed  's/< *>/↰/g;' mula1.txt > mula2.txt
sed -i 's/<HI1>/<NI>/g; s/<NI> *\([0-9][0-9]*[A-Z]\?\))\( *\.* *\)\({%[^{}%]*%}\)\?/☁<div class="pe_vibhaaga_1"><span class="pe_vibhaagasaMkhya_1">\1) <\/span><span class="pe_vibhaaganaama_1">\3<\/span>/g; s/<NI> *\([0-9][0-9]*[A-Z]\?\)\.\( *\.* *\)\({%[^{}%]*%}\)/☁<div class="pe_vibhaaga_1"><span class="pe_vibhaagasaMkhya">\1) <\/span><span class="pe_vibhaaganaama">\3<\/span>/g;      s/<span class="pe_vibhaaganaama"><\/span>//g; /☁/s/$/☁/;  s/☁//; '  mula2.txt
sed -i 's/<NI> *\(([0-9xiv][0-9xiv]*)\)/♞<p class="pe_vibhaaga_2"><span class="vibhaagasaMkhya_2">\1 <\/span>/g; s/<div class="pe_vibhaaga_1">\([^☁]*\)♞\([^☁]*\)☁/<div class="pe_vibhaaga_1">\1\2♞☁/g;'  mula2.txt
sed -i 's/<\/span>/☚/g; s/-↰//g; s/<C1>/∥/g;  s/\([↰♞☁☚][^↰♞☁☚∥]*\)∥/\1<table class="pe_cnpaTTika"><C1>/g; s/∥/<C1>/g; s/\(<C[0-9][0-9]*>[^↰♞☁☚]*\)\([↰♞☁☚]\)/\1☀❄♜\2/g;  s/\(<table[^♜]*$\)/&☀❄♜/;  s/<C1>/☀❄<tr class="pe_cnpaTTika"><C1>/g; s/<table class="pe_cnpaTTika">☀❄/<table class="pe_cnpaTTika">/g; s/\(<C[0-9][0-9]*\)>/☀<td class="pe_cnpaTTika">/g; s/<tr class="pe_cnpaTTika">☀/<tr class="pe_cnpaTTika">/g; ' mula2.txt
sed -i 's/<\/Poem>/☛/g;' mula2.txt
for((i=1;i<=100;i++));do
sed -i 's/<Poem>\([^↰☛]*\)↰/<Poem>\1<br>/g;' mula2.txt
sed -i 's/<Poem>\([^☛]*\)\/\//<Poem>\1 ।।/g;'  mula2.txt
done
for((i=1;i<=100;i++));do
sed -i 's/<Poem>\([^☛]*\)\//<Poem>\1 ।/g;'  mula2.txt
done
sed -i 's/<Poem>/<blockquote class="pe_padyam"><pre>/g; s/☛/<\/pre><\/blockquote>/g; ' mula2.txt
sed -i 's/<\/F>/♝/g; s/<F>\(\**\))*\([^♝]*\)♝/<span class="pe_gamanika">(\1\2)☚/g; '  mula2.txt
sed -i 's/<NI>/<span class="NIvirupu"><br><br>☚/g; s/<P>/<span class="Pvirupu"><br>☚/g; ' mula2.txt
sed -i 's/^<HI>\([^\.<>a-z]*\) \([^XVI]\)\([ \.,;<>:]\)/<span class="pe_pradhaanapada">\1☚  \2\3/; s/^<HI>\([^\.<>a-z]*\)\([ \.,;<>:]\)/<span class="pe_pradhaanapada">\1☚  \2/; '  mula2.txt
sed -i 's/\(<span class="pe_vibhaaganaama_1">\){%\([^%}{☚]*\)%}☚/\1\2  ☚/g;     s/{%/<i>/g; s/%}/<\/i>/g; s/{@/<b>/g; s/@}/<\/b>/g;'  mula2.txt
sed  's/\[ *Page/\[Page_/g; s/i10/î/g; s/r21/ṝ/g; s/a10/â/g; s/a11/à/g; s/e11/è/g; s/a1/ā/g; s/a2/ạ/g; s/a4/á/g; s/a7/ä/g; s/d2/ḍ/g; s/e1/ē/g; s/e4/é/g; s/e7/ë/g; s/h2/ḥ/g; s/i1/ī/g; s/i4/í/g; s/i5/ĩ/g; s/l2/ḷ/g; s/m2/ṃ/g; s/m3/ṁ/g; s/n2/ṇ/g; s/n3/ṅ/g; s/n5/ñ/g; s/o1/ō/g; s/o4/ó/g; s/o7ö//g; s/r2/ṛ/g; s/s2/ṣ/g; s/s3/ṡ/g; s/s4/ś/g; s/t2/ṭ/g; s/u1/ū/g; s/u4/ú/g; s/u7/ü/g; s/A1/Ā/g; s/D2/Ḍ/g; s/E4/É/g; s/H2/Ḥ/g; s/I1/Ī/g; s/M3/Ṁ/g; s/N2/Ṇ/g; s/N3/Ṅ/g; s/N5/Ñ/g; s/R2/Ṛ/g; s/S2/Ṣ/g; s/S4/Ś/g; s/T2/Ṭ/g; s/U1/Ū/g; s/U7/Ü/g; ' mula2.txt > mula3.txt
sed -i 's/<span class="pe_pradhaanapada">\([^☚]*\)☚/<span class="pe_pradhaanapada">✔\1♛☚/;' mula3.txt
sed  's/⁜/&\n/g;' h_iast1.txt |sed '/_\|^ *$/d;'>tatkal1 && sed  's/⁜/&\n/g;' h_deva1.txt |sed '/_\|^ *$/d;' > tatkal2 
while read -r line; do echo ${#line}; done < tatkal1 > pl.txt && paste pl.txt tatkal1 > tatkal1.1
paste tatkal1.1 tatkal2 |sed 's/\t//2; /⁜⁛[^⁛⁜]*[a-zA-Z][^⁛⁜]*⁜\|^[1234]\t/d;' |sort -u|sort -nr |sed 's/^[0-9]*\t//;' |sed 's/⁛\([^⁜⁛]*\)⁜⁛\([^⁜⁛]*\)⁜/sed -i\.bk "s\/\\(\[ ,\\.(){}\\":;<>=\\\&?_↰☚♞♜❄♝-\]\\)\1\\(\[ ,\\.(){}\\":;<>=\\\&?_↰☚♞❄♜♝-\]\\)\/\\1<a class="pe_a_pada" href=\"\2\">✔\1♛<\\\/a>\\2\/Ig;" mula3\.txt/g;' |sed 's/sed -i\.bk "/sed -i #/; s/" mula3/# mula3/;'|sed "s/#/'/g;"  |bash
sed 's/^.*\[Page_ *0*\([1-9][0-9]*\) *-* *\([ab]\)*+.*$/P\1\2/;' mula3.txt |sed '/^P/!s/^.*$/⁜/;1s/^/P1a\n/;' | tr '\n' '\t' |sed 's/\tP/\nP/g; s/$/\t/; s/\t\t*/\t/g; s/\t$//; s/\(P[0-9][0-9]*\)\t/\1a\t/g;' | sed ' s/$/\t⁜/;'  >putachitta1
for((i=1;i<=30;i++)); do
sed -i "s/^\(P[0-9][0-9]*[ab]\)\([^⁜]*\)\t⁜/\1\2\t\1_s${i}/g;" ./putachitta1
done
sed 's/\t.*$//;' putachitta1 >ps1 && sed '$d; 1s/^/\n/; ' ps1 > tatkal1 && sed '$s/$/\n/; 1d;' ps1 > tatkal2; sed 's/^[^\t]*\t//;' putachitta1 > tatkal3 && paste tatkal1 tatkal2 tatkal3 |sed 's/\t/_/; s/\t/\t⁜/g;' > putachitta2
for((i=1;i<=30;i++)); do
sed -i "s/^\([^\t]*\)\([^⁜]*\)\t⁜/\1\2\t\1_/g;" ./putachitta2
done
sed 's/^[^\t]*\t//; s/\t/\n/g;' putachitta2 |cat -n|sed 's/^ *//; s/\t/_/; s/a/L/g; s/b/R/g; /s/!d; ' > puta0
sed 's/_.*$//;' puta0 >ps1 && sed '$d; 1s/^/\n/; ' ps1 > tatkal1 && sed '$s/$/\n/; 1d;' ps1 > tatkal2; sed 's/^[^\t]*\t//;' puta0 > tatkal3 && paste tatkal1 tatkal2 tatkal3 |sed 's/\t/_/g;'> puta.txt
sed 's/^\([^_]*\)_\([^_]*\)_\([^_]*\)_P*\([0-9]*[RL]*\)_P*\([0-9]*[RL]*\)_P*\([0-9]*\)\([RL]*\)_s\([^_]*\)/<div class="pe_shiraputa"><a class="pe_a_pglink" href="_pe_@\1">«<\/a>word\8<a class="pe_a_pglink" href="_pe_@\2">»<\/a><br><a class="pe_a_pglink" class="pe_a_pglink" href="_pe_pn\4">↶<\/a><a class="pe_a_pglink" class="pe_a_pglink" href="_pe_pn\6\7">P\6\7<\/a><a class="pe_a_pglink" class="pe_a_pglink" href="_pe_pn\5">↷<\/a><\/div><div class="floatclr" style="clear:both;"><hr><br><\/div>/;' puta.txt > shirshika2p.txt
sed 's/_/ /g; s/^⁛\([^⁜⁛]*\)⁜.*$/<br><div class="pe_tera"><div class="pe_patra"><div class="pe_shir_shira"><div class="pe_shiram">\1<\/div><\/div>/;' h_deva.txt > mula3p1.txt
sed 's/\(<span class="pe_vibhaaganaama_1">\)\([^☚]*\)<a class="pe_a_pada"/\1\2<a class="pe_a_pada_v"/g; s/\(<span class="pe_vibhaaganaama_1">\)\([^☚]*\)<a class="pe_a_pada"/\1\2<a class="pe_a_pada_v"/g; s/\(<span class="pe_vibhaaganaama_1">\)\([^☚]*\)<a class="pe_a_pada"/\1\2<a class="pe_a_pada_v"/g; s/\(<span class="pe_vibhaaganaama_1">\)\([^☚]*\)<a class="pe_a_pada"/\1\2<a class="pe_a_pada_v"/g; s/^/<div class="pe_vivarana">/; s/$/<\/div><\/div><\/div>/; s/<\/blockquote>\(<br>\)*/<\/blockquote>/g; s/-↰//g; s/↰/ /g; s/[✔♛]//g; s/☚/<\/span>/g; s/♞/<\/p>/g; s/☁/<\/div>/g; s/☀/<\/td>/g; s/❄/<\/tr>/g; s/♜/<\/table>/g; s/\[Page[^][]*\]//g; ' mula3.txt > mula3p3.txt
paste mula3p1.txt shirshika2p.txt mula3p3.txt |sed 's/\t//g;' > mula4.txt
sed 's/^\([^_]*\)_\([^_]*\)_\([^_]*\)_P*\([0-9]*[RL]*\)_P*\([0-9]*[RL]*\)_P*\([0-9]*\)\([RL]*\)_s\([^_]*\)/⁛_pe_@\3⁜⁛_pe_pn\6\7⁜/;' puta.txt > tatkal1
paste heads_duprmd.txt tatkal1|sed 's/\t//; s/⁜⁛/|/g; s/[⁜⁛]//g;' > heads_siddhanta.txt
sed 's/^/\n\n/;' mula4.txt|sed '1d' > tatkal1 && sed 's/$/\n\n/;'  heads_siddhanta.txt > tatkal2 && paste tatkal1 tatkal2|sed 's/\t//g;' |sed '1s/^/\n#stripmethod=keep\n#sametypesequence=h\n#bookname=PuraaNa Encyclopedia\n#వందనాలు : అమ్మకి\n\n/;' > puraaNa_encyclopedia.babylon
sed -i '8~3s/^/<style>div.pe_tera{ font-family:Verdana, "Lucida Sans Unicode", sans-serif; margin: 0 auto 0 auto; line-height:1.5em; color:black; } div.pe_patra{ margin:0 auto 0 auto; padding: 1em; max-width:55.8em; font-size: 1em; } div.pe_vibhaaga_1{ margin-top:0.5em; } p.pe_vibhaaga_2{ margin-left:1em; } div.pe_shiram{ font-stle:bold; min-width:7em; padding:0.3em; font-size:1.5em; border:1px solid black; min-width:7em; } span.pe_pradhaanapada{ font-style:italic; font-weight:bold text-decoration:underline; color:black; } span.pe_vibhaaganaama_1{ font-style:italic; font-weight:bold; color:#133b5a; } span.pe_vibhaagasaMkhya_1{ font-style:italic; font-weight:bold; color:#133b5a; } a.pe_a_pglink{ text-decoration:none; color:blue; } a.pe_a_pada:link,a.pe_a_pada:visited,a.pe_a_pada{ text-decoration:none; color:#1A163A; } a.pe_a_pada_v:link,a.pe_a_pada_v:visited{ text-decoration:none; color:inherit; } a.pe_a_pada:hover{ text-decoration: underline; color:#1A163A; } a.pe_a_pada_v:hover{ text-decoration: underline; } div.pe_shir_shira{ max-width:15em; text-align:left; float:left; margin:0; }  div.pe_shiraputa{ max-width:15em; text-align:right; float:right; } div.pe_vivarana{ text-align:justify; } blockquote.pe_padyam{ font-style:italic; font-weight:bold; font-size:1.7em; } table.pe_cnpaTTika{ margin-left:2em; } <\/style>/;' ./purANa-encyclopedia.babylon_final
sed "s/<i>\([^<>]*\)<\/i>/''\1''/g;" puraaNa_encyclopedia.babylon |sed  's/^.*<div class="pe_vivarana">//; s/<\/div>//g; s/<div class="pe_vibhaaga_1"><span class="pe_vibhaagasaMkhya_1">\([^<>]*\)<\/span><span class="pe_vibhaaganaama_1">\([^<>]*\)<\/span>/<br><br>\1<i><u>\2<\/u><\/i>  /g; s/<div class="pe_vibhaaga_1"><span class="pe_vibhaagasaMkhya_1">\([^<>]*\)<\/span>/<br><br><i><u>\1<\/u><\/i>  /g;  s/<p *[^<>]*>/<br><br>/g; s/<span[^<>]*>//g;  s/<a class="[^<>"]*" href="\([^"<>]*\)">\([^<>]*\)<\/a>/\[\[\1|\2\]\]/g; s/ class="[^"<>]*"//g; s/<\/span>//g; ' |sed '1,6d;' |sed '1~3s/|.*$//; 1~3s/_.*$//;'   > purANa-encyclopedia.wiki
















