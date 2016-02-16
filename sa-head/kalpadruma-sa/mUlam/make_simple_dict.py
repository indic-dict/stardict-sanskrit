# coding: utf-8
# Prerequisite: sudo easy_install regex
import regex
import sys
import collections
  

word_count = collections.Counter()
test_lines = ["<HI>aH, puM, (atati sarvvaM vyApnoti iti ataterqaH)",
"<>vizRuH . iti medinI . [Page1-001-b+ 41]",
"<>“akAro vizRuruddizwa ukArastu maheSvaraH .",
"<>makAra ucyate brahmA praRavena trayo matAH” ..",
"<>iti durgAdAsaDftavacanaM . (klI . brahma . yaTA, --",
"<>a i u e o om kalASca mUlaM brahma iti",
"<>kIrttitam, iti agnipurARam .)",
"<HI>afRI, [n], tri, (na fRI, naYasamAsaH . atra",
"<>aSabdo na naYjAtaH, taTAtve anfRI ityeva syAt .",
"<>apitu svaprakftireva tataSca aYmAtranipAtanAt",
"<>nAtra sanDiriti jYeyam .) anfRI . afRa-",
"<>grastaH . aDArI . yaTA, --",
"<>“divasasyAzwame BAge SAkaM pacati yo naraH .",
"<>afRI cApravAsI ca sa vAricara modate” ..",
"<>iti mahABArataM .",
]
# for line in sys.stdin:
for line in test_lines:
  head_v1, value = line.split("\t")
  headwords = regex.sub(" +", " ", head_v1).strip().split(" ")

  headwords = map(lambda headword : regex.sub(r'\p{P}+', "", headword).strip(), headwords)
  headwords = map(lambda headword : regex.sub(r'ं$', "म्", headword), headwords)
  headwords = filter(lambda headword : headword != "", headwords)
  headwords = list(set(headwords))
  word_count[len(headwords)] += 1
  if (len(headwords) > 2):
    headwords = headwords[:1]
  # Print the headword without the prathamA-vibhakti ending.
  value = regex.compile(r'\s+').sub(value, " ")
  value = regex.sub(r'\s+\.\.', "॥<br>", value)
  value = regex.sub(r'\s+\.', "। ", value)
  value = regex.sub(r'\s+', " ", value)
  value = regex.sub(r'((०|१|२|३|४|५|६|७|८|९|१०)+)', r'<br>\g<1>', value)
  print "|".join(headwords) + "\n" + value.strip() + "\n"

