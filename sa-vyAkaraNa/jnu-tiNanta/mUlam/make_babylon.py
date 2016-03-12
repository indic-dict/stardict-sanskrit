# -*- coding: utf-8 -*-
from __future__ import unicode_literals
import unicodedata
import regex
import re
import sys
reload(sys)
sys.setdefaultencoding("utf-8")

HOME = "/home/vvasuki/"
# print HOME
f = open(HOME + '/stardict-sanskrit/sa-head/jnu-tiNanta/mUlam/jnu-tiNanta-values.txt', 'r')
lines = f.readlines()
for line in lines:
    matches = regex.findall(r"( [^,;\)\\]+? )", line, flags=re.UNICODE, overlapped=True)
    matches = [match.strip() for match in matches]
    matches = list(set(matches))
    print u'|'.join(matches) + "\n" + line.strip().replace("\\n", "<br>") + "\n"
    # break