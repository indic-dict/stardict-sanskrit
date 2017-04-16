echo "It will create custom version from mula_vedic_index.babylon file created by make_vei_babylon.sh file; we can add as many options. here now just gave two questions to create a customized file.suggest whatever other options we can give."

lanke=0;styli=0;devai=0;
clear
read -p "
how do you want hyperlinks to be created?
--------------------------------------------------------
1)(default) link to devanagari headword,  and IAST display text
2)link to devanagari headword and display also devanagari head word
3)no hyperlinking at all,just IAST words as in pdf.
4)no hyperlinking at all, just replace IAST words with devanagari head words
1 or 2 or 3 or 4:" lanke

clear
read -p "
choose one of fallowing options regarding styling
---------------------------------------------------
1)(default)   style essential parts. (like centering,limiting width on large screens, hyperlink color customising for non clumsyness, etc)
2)(used for default minimal version) minimal, styled only hyperlinks to removes underlines.
3) utter minimal No styling, no foot link 'highlighting on click', just clicking footlink hyperlink takes you to footnote, but doesn't highlight., there will be underlines under hyperlinks.
1 or 2 or 3:" styli

cp mula_vedic_index.babylon vedic_index.babylon_customized

if [ ${lanke} -eq 2 ]; then sed -i 's/<a class="vei_a_pada" href="\([^"<>]*\)">\([^<>]*\)<\/a>/<a class="vei_a_pada" href="\1">\1<\/a>/g;' vedic_index.babylon_customized; elif [ ${lanke} -eq 3 ]; then sed -i 's/<a class="vei_a_pada" href="\([^"<>]*\)">\([^<>]*\)<\/a>/\2/g; ' vedic_index.babylon_customized;  elif [ ${lanke} -eq 4 ]; then sed -i 's/<a class="vei_a_pada" href="\([^"<>]*\)">\([^<>]*\)<\/a>/\1/g; ' vedic_index.babylon_customized;  fi

if((lanke==2||lanke==4));then
clear
read -p "Wait a second. as you choose to display hyperlink/text in devanagari, there are many IAST terms in entire text, which are not headwords/hyperlinks. So do you want to transliterate those all remaining IAST words too into devanagari?
1)Yes (Recommonded)
2)No
1 or 2:" devai
if((devai==1));then for((i=1;i<=3;i++)); do sed '8~3s/\([ ,\.(){}":;<>=\&?♞♛♜♝-]\)\([^ ,\.(){}":;<>=\&?♞♛♜♝-]*[îṝâàèāạáäḍēéëḥīíĩḷṃṁṇṅñōóöṛṣṡśṭūúĀḌÉḤĪṀṆṄÑṚṢŚṬŪÜ][^ ,\.(){}":;<>=\&?♞♛♜♝-]*\)\([ ,\.(){}":;<>=\&?♞♛♜♝-]\)/\1\n☁\2⁜\n\3/g; 8~3s/\([ ,\.(){}":;<>=\&?♞♛♜♝-]\)\([^ ,\.(){}":;<>=\&?♞♛♜♝-]*\)\( *- *\)\(<a class="vei_a_pada\)/\1\n☁\2⁜\n\3\4/g; 8~3s/<\/a>\( *- *\)\([^ ,\.(){}":;<>=\&?♞♛♜♝-]*\)\([ ,\.(){}":;<>=\&?♞♛♜♝-]\)/<\/a>\1\n☁\2⁜\n\3/g;    8~3s/\([ ,\.(){}":;<>=\&?♞♛♜♝-]\)\([^ ,\.(){}":;<>=\&?♞♛♜♝-]*\)\( *- *\)\([अआइईउऊऋॠएऎऐओऒऔंःकखगघङचछजझञटठडढणतथदधनपफबभमयरलळवशषसहािीुूृॄेॆैोॊौ]\)/\1\n☁\2⁜\n\3\4/g; 8~3s/\([अआइईउऊऋॠएऎऐओऒऔंःकखगघङचछजझञटठडढणतथदधनपफबभमयरलळवशषसहािीुूृॄेॆैोॊौ]\)\( *- *\)\([^ ,\.(){}":;<>=\&?♞♛♜♝-]*\)\([ ,\.(){}":;<>=\&?♞♛♜♝-]\)/\1\2\n☁\3⁜\n\4/g;   ' vedic_index.babylon_customized >tatkal1 && sed  ' /☁/y/[ABCDEFGHIJKLMNOPQRSTUVWXYZ]/[abcdefghijklmnopqrstuvwxyz]/;       /☁/s/î//Ig; /☁/s/ā/aa/Ig; /☁/s/ḍh/Dh/Ig;  /☁/s/ḍ/D/Ig; /☁/s/ḥ/H/Ig; /☁/s/ī/ii/Ig; /☁/s/ṃ/M/Ig; /☁/s/ṁ/M/Ig; /☁/s/ṇ/N/Ig; /☁/s/ṅ/M/Ig; /☁/s/ñ/~n/Ig; /☁/s/ṛ/R/Ig;  /☁/s/ḷ/L/Ig; /☁/s/ṣ/Sh/Ig; /☁/s/ś/sh/Ig; /☁/s/ṭ/T/Ig; /☁/s/ū/uu/Ig; /☁/s/ch/Ch/Ig; /☁/s/c/ch/g; ' tatkal1|sed '    /☁/s/kh/ख्/g; /☁/s/gh/घ्/g; /☁/s/Ch/छ्/g; /☁/s/ch/च्/g; /☁/s/jh/झ्/Ig; /☁/s/~N/ङ्/g; /☁/s/~n/ञ्/g; /☁/s/Th/ठ्/g; /☁/s/Dh/ढ्/g; /☁/s/th/थ्/g; /☁/s/dh/ध्/g; /☁/s/ph/फ्/Ig; /☁/s/bh/भ्/Ig; /☁/s/sh/श्/g; /☁/s/Sh/ष्/g; /☁/s/zh/ळ्/g; /☁/s/k/क्/Ig; /☁/s/g/ग्/Ig; /☁/s/j/ज्/Ig; /☁/s/T/ट्/g; /☁/s/D/ड्/g; /☁/s/N/ण्/g; /☁/s/t/त्/g; /☁/s/d/द्/g; /☁/s/n/न्/g; /☁/s/p/प्/Ig; /☁/s/b/ब्/Ig; /☁/s/m/म्/g; /☁/s/y/य्/Ig; /☁/s/r/र्/g; /☁/s/l/ल्/g; /☁/s/L/ळ्/g; /☁/s/v/व्/Ig; /☁/s/s/स्/Ig; /☁/s/h/ह्/g; /☁/s/्aa/ा/g; /☁/s/्ii/ी/g; /☁/s/्uu/ू/g; /☁/s/्RR/ॄ/g; /☁/s/्ai/ै/g; /☁/s/्au/ौ/g; /☁/s/्a//g; /☁/s/्i/ि/g; /☁/s/्u/ु/g; /☁/s/्R/ृ/g; /☁/s/्e/े/g; /☁/s/्o/ो/g; /☁/s/M/ं/g; /☁/s/H/ः/g; /☁/s/aa/आ/g; /☁/s/ii/ई/g; /☁/s/uu/ऊ/g; /☁/s/RR/ॠ/g; /☁/s/ai/ऐ/g; /☁/s/au/औ/g; /☁/s/a/अ/g; /☁/s/i/इ/g; /☁/s/u/उ/g; /☁/s/R/ऋ/g; /☁/s/e/ए/g; /☁/s/o/ओ/g; /☁/s/1/१/g; /☁/s/2/२/g; /☁/s/3/३/g; /☁/s/4/४/g; /☁/s/5/५/g; /☁/s/6/६/g; /☁/s/7/७/g; /☁/s/8/८/g; /☁/s/9/९/g; /☁/s/0/०/g;  ' |tr '\n' '\t' |sed 's/\t☁//g; s/⁜\t//g; s/\t/\n/g;' > vedic_index.babylon_customized;done;fi
fi

if ((styli==3||styli==2)); then sed -i 's/^.*<div class="vei_shiraputa">//; s/<\/div>$//; s/<br><a class="vei_a_pglink"/ :::: <a class="vei_a_pglink"/; s/<\/div><div class="floatclr" style="clear:left;"><hr><\/div><div class="vei_vivaraNa">/<hr>/; s/<\/div><i>Foot notes/<i>Foot notes/; s/ class="[^<>"]*"//g; '  vedic_index.babylon_customized ; fi
if((styli==2));then sed -i 's/<a/<a class="vei_a"/g; 8~3s/^/<style>a.vei_a{text-decoration:none;}:target{background: #ccc;border:solid 1px #aaa;}<\/style>/; '  vedic_index.babylon_customized ; fi

sed -i "s/id=\"fx/id=\"fx_${lanke}${styli}${devai}_/g; s/id=\"ref/id=\"ref_${lanke}${styli}${devai}_/g; s/href=\"#fx/href=\"#fx_${lanke}${styli}${devai}_/g; s/href=\"#ref/href=\"#ref_${lanke}${styli}${devai}_/g;   s/\([\"_\.]\)vei_/\1vei_${lanke}${styli}${devai}/g;   " vedic_index.babylon_customized

