# coding: utf-8
# Prerequisite: sudo easy_install regex
# from __future__ import unicode_literals
# import unicodedata
import regex
import re
import sys
import collections
  
word_count = collections.Counter()
test_lines = ["गरामः\tगरामो ह्यत्र", "गरामम्\tगरामो ह्यत्र"]
for line in sys.stdin:
# for line in test_lines:
  try:
    (head, value) = line.split("\t")
    head2 = regex.sub("ः$", "", head, flags=regex.UNICODE)
    head2 = regex.sub("म्$", "", head2, flags=regex.UNICODE)
    headwords = list(set([head, head2]))
    headwords = filter(lambda headword : headword != "", headwords)
    print "|".join(headwords) + "\n" + value.strip() + "\n"
  except ValueError:
    print line
    break
   

