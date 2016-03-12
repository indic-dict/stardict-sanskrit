# coding: utf-8
import re
import sys

# Should be run after dump_data_files.py.
# Reads files in ~/tinanta, and creates a tsv with lines such as:
# भू (सत्तायाम्, भ्वादिगण, परस्मै, लट्)	भवति भवतः भवन्ति\n भवसि भवथः भवथ\n भवामि भवावः भवामः

HOME = "/home/vvasuki/"
# print HOME
# 1 to 451 with some missing dhAtu-files in-between.
for i in range(1,451):
  filename = HOME + "/tinanta/%d" % i
  f = open(filename, 'r')
  text = f.read()
  text = text.replace('\n', '\\n')
  text = re.sub(".+Click here to get verb-forms", "", text)
  text = re.sub("References.+", "", text)
  text = re.sub("\[.+?\]", " ", text)
  text = re.sub(" +", " ", text)
  text = re.sub("^.n.n +", "", text)
  text = re.sub("\).n.n +", ")\t ", text)
  text = re.sub(".n.n +", "\n", text)
  text = re.sub(".n.n *$", "", text)
  if not '\t' in text:
    print >> sys.stderr, "Cannot parse: " + filename
    continue

  # At this point, text is a collecion of lines such as the below:
  # भू (सत्तायाम्, भ्वादिगण, परस्मै, लट्)	भवति भवतः भवन्ति\n भवसि भवथः भवथ\n भवामि भवावः भवामः
  # Now, we add lines with each of भवति भवतः भवन्ति ... as a headword.
  lines = text.split("\n")
  for line in lines:
    dhAtu_lakAra_detail, forms_line = line.split("\t")
    value = dhAtu_lakAra_detail + "\\n" + forms_line
    dhAtu, lakAra_detail = dhAtu_lakAra_detail.split(" (")
    lakAra_detail = lakAra_detail.replace(")", "")
    print dhAtu + "\t" + value
    headwords = re.sub(".n+", "", forms_line).strip().split(" ")
    for headword in headwords:
      print headword + "\t" + value
