# coding: utf-8
# Prerequisite: sudo easy_install regex
import regex
import sys
import collections
  
test = False

lines = []
if (test):
  test_lines = [
  "<H>SabdakalpadrumaH . [Page1-001-a+ 43]",
  "<HI>aH, puM, (atati sarvvaM vyApnoti iti ataterqaH)",
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
  lines = test_lines
else:
  lines = sys.stdin.readlines()

full_text = "\n".join(lines)
full_text = regex.sub(r'\s*\n?<>', " ", full_text)
lines = full_text.split("\n")

for line in lines:
  # Example:
  # <HI>afRI, [n], tri, (na fRI, naYasamAsaH . atra aSabdo na naYjAtaH, taTAtve anfRI ityeva syAt . apitu svaprakftireva tataSca aYmAtranipAtanAt nAtra sanDiriti jYeyam .) anfRI . afRa- grastaH . aDArI . yaTA, -- “divasasyAzwame BAge SAkaM pacati yo naraH . afRI cApravAsI ca sa vAricara modate” .. iti mahABArataM .
  # print line
  m = regex.compile('(<HI>)(.+)').match(line)
  if not m:
    continue
  line = m.group(2)
  line = regex.sub(r'\[Page.+\]', "", line)
  m = regex.compile('(.+?)(,.+)').match(line)
  line = regex.sub(r'(.+?)(,.+)', r'\g<1>\t&&\t\g<1>\g<2>', line)
  print line
  

