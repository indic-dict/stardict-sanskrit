#! /usr/bin/python
# coding: utf-8
# Prerequisite: sudo easy_install regex
import regex
import sys
import collections
  
test = True

"""
Regex series:

Unhandled cases:
भेदन (भिद् ल्युट्)
"""

lines = []
if (test):
  test_lines = """
अ पु. संस्कृत वर्णमाला का प्रथम वर्ण [विशेषतया तीन दिन
तक चलने वाले सोम-याग (त्रिरात्र) के प्रथम दिन आज्य
इति”, पञ्च. ब्रा. 2०.14.3।
अंश पु.1 अ. भाग (देवों, पितरों एवं मनुष्यों के लिए नियत)
ऋ.वे. 1०.31.3; अ.वे. 11.1.5;1 ब. पशु-भाग, बौ.श्रौ.सू.
का नाम, ऋ. प्रा. 17.4; निदा.सू. 1०5.2०।
""".split("\n")
  lines = test_lines
else:
  lines = sys.stdin.readlines()

full_text = "\n".join(lines)
full_text = regex.sub(ur'^(\S+?)\s+(क्रि\.वि[ .])', '####\g<1>####\g<1> \g<2>', full_text, flags=regex.UNICODE)
full_text = regex.sub(ur'^(\S+?)\s+(स्त्री[ .])', '####\g<1>####\g<1> \g<2>', full_text, flags=regex.UNICODE)
full_text = regex.sub(ur'^(\S+) (पु[ .])', '####\g<1>####\g<1> \g<2>', full_text, flags=regex.UNICODE)
full_text = regex.sub(ur'^(\S+?)\s+(वि[ .])', '####\g<1>####\g<1> \g<2>', full_text, flags=regex.UNICODE)
full_text = regex.sub(ur'^(\S+?)\s+(न[ .])', '####\g<1>####\g<1> \g<2>', full_text, flags=regex.UNICODE)
print(full_text)

