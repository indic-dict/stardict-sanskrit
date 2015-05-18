readme.txt for krmxml download.  
May 28, 2014. Jim Funderburk

This directory contains krm.xml, an xml version of krm.txt; it also contains
the programs used to create krm.xml from krm.txt.

In the configuration of the sanskrit-lexicon server, the shell script
redo.sh will carry through all these steps. But it is likely that you
will have to make minor adaptations for everything to work on your computer.

The transcoder software component is based upon software designed and
developed by Ralph Bunker,  Malcolm Hyman, and Peter Scharf
(www.sanskritlibrary.org).

The krm.txt file is available via the krmtxt.zip download and is
released under the license described in the krmheader.xml file.

The rest of this document provides comments on the program steps appearing
in redo.sh.

1. python26 hw0.py krm.txt krmhw0.txt
 This step identifies the headwords from krm.txt, and for each headword
 identifies:
  page  The page and column number where the headword starts.
  headword  The headword itself, as coded in krm.txt in Harvard-Kyoto.
  line1,line2  The first and last lines of krm.txt containing the headword text

Note on python26:
  Step 3 below requires at least python version 2.6.
  All steps should work with the latest (2.7 currently) python version;
  the code has not been tested with python 3.

2. python26 hw1.py krmhw0.txt krmhw1.txt krmhw1_note.txt 
 'Normalize' all headword spellings, but still leave in Harvard-Kyoto.
 Then output the same format, using the normalized headword.

3. python26 hw2.py krmhw1.txt krmhw2.txt
 This converts the headword transliteration to slp1.
 This conversion is done so the headword will be coded (spelled) in a way
 consistent with that of other dictionary databases in the sanskrit-lexicon
 web site; see http://www.sanskrit-lexicon.uni-koeln.de/monier/help.html for
 transcoding details.

4. python26 make_xml.py krm.txt krmhw2.txt krm.xml
 Construct an xml file consistent with the markup described in krm.dtd.
 Essentially, krm.xml has one record for each headword line in krmhw2.txt,
 and consists of the lines from krm.txt for the headword.  Extended ascii 
 encodings are converted to an ascii form (&#xHHHH;), and a few adjustments
 to the text are made so that a well-formed xml structure results.  

5. xmllint --noout --valid krm.xml
 This uses the common unix 'xmllint' utility to check that krm.xml
 is well-formed xml which is valid with respect to the data-type definition
 specified in krm.dtd.


