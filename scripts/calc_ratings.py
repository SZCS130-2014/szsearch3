#!/usr/bin/python

import xml.etree.ElementTree as ET
import sys

def calc_ratings(inputFile):
	productTree = ET.parse(inputFile)
	root = productTree.getroot()
	for product in root.iter("product"):
		totalrating = 0.0
		numratings = 0
		comments = product.find("Comments")
		for comment in comments.iter("Comment"):
			totalrating += 0 if comment.attrib["Rating"] == "" else float(comment.attrib["Rating"])
			numratings += 0 if comment.attrib["Rating"] == "" else 1
		avgrating = 0 if numratings == 0 else float(totalrating)/float(numratings)
		product.attrib["AvgRating"] = str(avgrating)
	return ET.dump(productTree)

if __name__ == "__main__":
	print calc_ratings(sys.argv[1])