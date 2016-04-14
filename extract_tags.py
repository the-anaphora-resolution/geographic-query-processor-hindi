#!/usr/bin/python

__author__ = "Chinmaya Gautam"
__copyright__ = "Copyright 2016, The Anaphora Resolution"
__credits__ = ["Chinamya Gautam", "Harsh Fatehpuria", "Rahul Agrawal", "Simrat Singh Chabbra"]
__license__ = "GPL"
__version__ = "1.0.1"
__maintainer__ = "Chinmaya Gautam"
__email__ = "chinmaya.gautam@usc.edu"
__status__ = "Developement"

word_tag = dict()

#the input file parsed_data.txt is generated from shallow_parser_interface.py
infile = open("parsed_data.txt", "r")
data = infile.read().split('\n')
infile.close()

loi = list()                           #lines of interest

for line in data:
    try:
        if line[0] == "<" and line[1] == "f" and line[2] == "s":
            loi.append(line)
    except:
        continue

len_loi = len(loi)
for i in range(len_loi):
    coi = loi[i].split(' ')[1][4:]   #chunks of interest
    ccoi = coi.split(',')            #chunk of chunks of interest
    if ccoi[0] not in word_tag:
        word_tag[ccoi[0]] = ccoi[1]

    loi[i] = coi

outfile = open("word_tag_pairs.txt", 'w')
for word in word_tag:
    outfile.write(word + " " + word_tag[word] + "\n")
    #print word, " ", word_tag[word]
    
outfile.close()

