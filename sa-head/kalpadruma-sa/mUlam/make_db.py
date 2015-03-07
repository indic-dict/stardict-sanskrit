# coding: utf-8
# Prerequisite: sudo easy_install regex
import regex
import sys

for line in sys.stdin:
  head_v1, value = line.split("\t")
  headwords = regex.sub(" +", " ", head_v1).strip().split(" ")

  for headword in set(headwords):
    headword = regex.sub(r'\p{P}+', "", headword).strip()
    # print headword
    if (headword != ""):
      print headword + "\t" + value.strip()
