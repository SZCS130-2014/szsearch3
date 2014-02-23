#!/usr/bin/python

import sys
import getopt
import xml.etree.ElementTree as ET

""" A class to merge datasets 1 and 2 into a single
	XML file """
class DataMerger:

	def __init__(self):
		self.productsTree = ''
		self.products = {}

	def processFirstDataset(self, inputFile):
		print "Processing Input File..."

		# Parse the input file into an XML DOM
		self.productsTree = ET.parse(inputFile)
		productsRoot = self.productsTree.getroot()

		# Iterate over DOM adding to dictionary
		for productNode in productsRoot:
			pid = productNode.attrib['PID']
			if pid not in self.products:
				self.products[pid] = productNode
			else:
				print "Collision Detected: ", pid


	def mergeSecondDataset(self, mergeFile):
		print "Merging Second File..."

		mergeTree = ET.parse(mergeFile)
		mergeRoot = mergeTree.getroot()

		# Iterate over DOM adding new fields to existing products
		for node in mergeRoot:
			pid = node[0].text
			if pid in self.products:
				category = ET.fromstring('<Category>' + node[1].text + '</Category>')
				displayName = ET.fromstring('<DisplayName>' + node[2].text + '</DisplayName>')
				self.products[pid].append(category)
				self.products[pid].append(displayName)

	def writeNewDataset(self, outputFile):
		print "Writing New File..."

		# Write the modified merge dataset to a new file
		self.productsTree.write(outputFile)

	def main(self, argv):
		# Process arguments
		inputFile = ''
		mergeFile = ''
		outputFile = ''

		try:
			opts, args = getopt.getopt(argv, 'hi:m:o:', ["inputfile=", "mergefile=", "outputfile="])
		except getopt.GetoptError:
			print "usage: data_merge.py -i <inputfile> -m <mergefile> -o <outputfile>"
			sys.exit(2)

		for opt, arg in opts:
			if opt == '-h':
				print "usage: data_merge.py -i <inputfile> -m <mergefile> -o <outputfile>"
				sys.exit()
			elif opt in ("-i", "--inputfile"):
				inputFile = arg
			elif opt in ("-m", "--mergefile"):
				mergeFile = arg
			elif opt in ("-o", "--outputfile"):
				outputFile = arg

		print 'Input File: ', inputFile
		print 'Merge File: ', mergeFile
		print 'OutputFile: ', outputFile

		# Add all products in first dataset to dictionary
		self.processFirstDataset(inputFile)

		# Add attributes matching a PID from first dataset
		# into appropriate dictionary entry
		self.mergeSecondDataset(mergeFile)

		# Write the dictionary to the output file
		self.writeNewDataset(outputFile)


if __name__  == '__main__':
	DataMerger().main(sys.argv[1:])
