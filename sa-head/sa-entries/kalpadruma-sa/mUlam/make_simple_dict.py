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
  m = regex.compile('(.+?)([ ,].+)').match(line)
  headword = m.group(1)
  details = m.group(2)
  
  # Clean up headword, add root
  headword = regex.sub(r'M$', "m", headword)
  headwords = [headword]

  # handle aMSI|aMSin	&&	aMSI, [n], tri
  I_ending = regex.compile('(.+)([I])$').match(headword)
  if I_ending:
    s_ending = regex.compile('[ ,]+\[n\].+').match(details)
    if s_ending:
      root_headword = I_ending.group(1) + 'in'
      headwords = headwords + [root_headword]
  
  # handle atraBavAn, [t]
  An_ending = regex.compile('(.+)(An)$').match(headword)
  if An_ending:
    t_ending = regex.compile('[ ,]+\[t\].+').match(details)
    if t_ending:
      root_headword = An_ending.group(1) + 'at'
      headwords = headwords + [root_headword]
      
  # handle suDABuk, [j] puM
  # handle suradviw, [z] puM
  # snuk, [h] strI
  # hiqimbaBit, [d] puM
  # hfdispfk, [S] tri
  # anuzwup, [B]
  # krut, [D] strI,
  # prAw, [C]
  # nagnahu, [U]
  k_ending = regex.compile('(.+)([kwtpu])$').match(headword)
  if k_ending:
    cj_ending = regex.compile('[ ,]+\[([cjzhdSBDCU])\].+').match(details)
    if cj_ending:
      root_headword = k_ending.group(1) + cj_ending.group(1)
      headwords = headwords + [root_headword]  
  
  # handle soQA, [f] tri, 
  A_ending = regex.compile('(.+)([A])$').match(headword)
  if A_ending:
    f_ending = regex.compile('[ ,]+\[([fr])\].+').match(details)
    if f_ending:
      root_headword = A_ending.group(1) + f_ending.group(1)
      headwords = headwords + [root_headword]  
  
  # stambapUH, [r] strI
  UH_ending = regex.compile('(.+)(UH)$').match(headword)
  if UH_ending:
    f_ending = regex.compile('[ ,]+\[([fr])\].+').match(details)
    if f_ending:
      root_headword = UH_ending.group(1) + f_ending.group(1)
      headwords = headwords + [root_headword]  
  
  # saKA, [i] puM
  if headword == 'saKA':
    headwords = headwords + ['saKi']

  # rAH, [E] 
  if headword == 'rAH':
    headwords = headwords + ['rE']

  # dyOH, [o] strI, (d
  if headword == 'dyOH':
    headwords = headwords + ['dyo']
  
  mH_ending = regex.compile('(.+)([mH])$').match(headword)
  if mH_ending:
    # Handle: aNGaH, [s] klI
    s_ending = regex.compile('[ ,]+\[s\].+').match(details)
    if s_ending:
      root_headword = mH_ending.group(1) + 's'
    else:
      root_headword = mH_ending.group(1)
    headwords = headwords + [root_headword]
  
  line = '|'.join(headwords) + '\t&&\t' + headword + details
  print line
  

