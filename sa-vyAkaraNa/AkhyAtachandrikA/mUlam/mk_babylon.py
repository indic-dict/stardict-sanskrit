#!/bin/python

import csv

with open('AkhyAtachandrikA.txt', 'rU') as csvfile:
	for roots in csv.reader(csvfile, delimiter=','):
		if len(roots) < 3:
			continue
		verse_position = roots[0]
		meaning = roots[1]
		print '|'.join(roots[1:]) + "\n" + meaning + "<br>" + verse_position + "<br>"  + (' '.join(roots[2:])) + "\n"

