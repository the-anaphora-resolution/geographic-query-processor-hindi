'''
Program to process the query through a set of rules and  determine the slots
to be filled in the Postgre SQL Query
'''

import sys
import time


def isPropertyDistance(query):
	'''
	Function to check if the query is a distance query or not
	@param: query in list format, stripped and split
	@return: True if a distance query, False otherwise
	'''
	input_file_handler= open("../synonyms/distance.txt","r")
	distance_synonyms=eval(input_file_handler.readline())
	#print distance_synonyms
	input_file_handler.close()

	flag=0
	for i in set(query).intersection(distance_synonyms):
		flag=1
		#output_file_handler.write("\n"+i)
	if flag==1: return True



def checkQueryProperty(query):
	'''
	Function to find the property of a query
	@param: query in list format, stripped and split
	@return: Type of query 
	'''
	if(isPropertyDistance(query)):
		return 0
	#if(isPropertyLength(query)):
	#	return 1
	#if(isPropertyArea(query)):
	#	return 2
	#if(isPropertyInside(query)):	
	#	return 3




print "GEOGRAPHIC QUESTION ANSWERING SYSTEM"
print "------------------------------------\n"
print "Loading Query from file... "
time.sleep(2)
#filePath=sys.argv[1]
filePath="hindiQuery.txt"

input_file_handler= open(filePath,"r")
output_file_handler= open("nlpOutput.txt","w")

query=input_file_handler.readline().strip().split()
#print query
input_file_handler.close()

for i in query:
	output_file_handler.write(i+" ")
 
queryProperty = checkQueryProperty(query)
print queryProperty

output_file_handler.close()
