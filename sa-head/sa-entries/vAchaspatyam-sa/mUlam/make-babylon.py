# coding: utf-8
# Prerequisite: sudo easy_install regex
# from __future__ import unicode_literals
# import unicodedata
import regex
import re
import sys
import collections
  
word_count = collections.Counter()
test_lines = ['आभ्यासिक	{@आभ्यासिक@}¦ त्रि॰ अभ्यासे निकटे भवः ठक्। १ निकटस्थिते “अल्पैश्चाभ्यासिकैर्यु क्तं शुशुभे योधरक्षितम्” भा॰ आ॰ २७६अ॰। अभ्यासात् पौनःपुन्यात् आगतः ठक्। २ अभ्यास-प्राप्ते दृढसंस्कारादौ। ']
for line in sys.stdin:
# for line in test_lines:
  try:
    (head, value) = line.split("\t")
    value = regex.sub(r'((०|१|२|३|४|५|६|७|८|९|१०){1,2})', r'<br><br>\g<1> ', value)    
    value = regex.sub(r'“', '<br>“', value)    
    value = regex.sub(r' +', ' ', value)    
    print head + "\n" + value.strip() + "\n"
  except ValueError:
    print line
    raise ValueError()
    break
   

