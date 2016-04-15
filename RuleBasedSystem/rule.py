'''
Program to process the query through a set of rules and  determine the slots
to be filled in the Postgre SQL Query
'''

import sys

print "GEOGRAPHIC QUESTION ANSWERING SYSTEM"
print "------------------------------------\n"
print "Loading Query from file... "

#filePath=sys.argv[1]

#WINDOWS: filePath="C:\Users\rahul\Desktop\hindiCorpus.txt"
#LINUX: filePath="C:/Users/rahul/Desktop/hindiCorpus.txt"

filePath="hindiQuery.txt"

input_file_handler= open(filePath,"r")
output_file_handler= open("nlpOutput.txt","w")

#query=input_file_handler.readline().strip().split()
query=input_file_handler.readline().strip().split()
#print query
input_file_handler.close()

for i in query:
	output_file_handler.write(i+" ")

#Finding Synonyms
input_file_handler= open("../synonyms/distance.txt","r")
distance_synonyms=eval(input_file_handler.readline())
#print distance_synonyms
input_file_handler.close()

flag=0
for i in set(query).intersection(distance_synonyms):
	flag=1
	output_file_handler.write("\n"+i)
if flag==1: print "TRUE"
input_file_handler.close()
output_file_handler.close()
