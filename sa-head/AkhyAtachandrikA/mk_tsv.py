#!/bin/python

import csv

with open('AkhyAtachandrikA.txt', 'rU') as csvfile:
	for roots in csv.reader(csvfile, delimiter=','):
		if len(roots) < 3:
			continue
		verse_position = roots[0]
		meaning = roots[1]
		for root in roots[2:]:
			print root + "\t" + meaning + " " + verse_position + " "  + ' '.join(roots[2:])
			pass
		print meaning + "\t" + meaning +  " " + verse_position + " "  + ' '.join(roots[2:])

