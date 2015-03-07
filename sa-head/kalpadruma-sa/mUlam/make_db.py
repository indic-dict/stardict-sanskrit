# coding: utf-8
# Prerequisite: sudo easy_install regex
import regex
import sys

for line in sys.stdin:
  head_v1, value = line.split("\t")
  headwords = regex.sub(" +", " ", head_v1).strip().split(" ")

  printed_words = set()
  for headword in headwords:
    headword = regex.sub(r'\p{P}+', "", headword).strip()
    # print headword
    if (headword != "" and not headword in printed_words):
      print headword + "\t" + value.strip()
      printed_words.add(headword)
      # just print the first headword
      break

