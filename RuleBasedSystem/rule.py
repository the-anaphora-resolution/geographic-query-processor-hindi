'''
Program to process the query through a set of rules and  determine the slots
to be filled in the Postgre SQL Query
'''

import sys
import time
from glob import iglob
import json
import os



def isPropertyDistance(query):
	'''
	Function to check if the query is a distance query or not
	@param: query in list format, stripped and split
	@return: True if a distance query, False otherwise
	'''
	input_file_handler= open("../synonyms/property/distance.txt","r")
	distance_synonyms=eval(input_file_handler.readline())
	#print distance_synonyms
	input_file_handler.close()

	flag=0
	for i in set(query).intersection(distance_synonyms):
		flag=1
		#output_file_handler.write("\n"+i)
	if flag==1: return True


'''
def isPropertyLength(query):
	''
	Function to check if the query is a Length query or not
	@param: query in list format, stripped and split
	@return: True if a length query, False otherwise
	''
	input_file_handler= open("../synonyms/property/length.txt","r")
	length_synonyms=eval(input_file_handler.readline())
	#print length_synonyms
	input_file_handler.close()

	flag=0
	for i in set(query).intersection(length_synonyms):
		flag=1
		#output_file_handler.write("\n"+i)
	if flag==1: return True


def isPropertyArea(query):
	''
	Function to check if the query is a Area query or not
	@param: query in list format, stripped and split
	@return: True if a area query, False otherwise
	''
	input_file_handler= open("../synonyms/property/area.txt","r")
	area_synonyms=eval(input_file_handler.readline())
	#print area_synonyms
	input_file_handler.close()

	flag=0
	for i in set(query).intersection(area_synonyms):
		flag=1
		#output_file_handler.write("\n"+i)
	if flag==1: return True


def isPropertyCount(query):
	''
	Function to check if the query is a Count query or not
	@param: query in list format, stripped and split
	@return: True if a count query, False otherwise
	''
	input_file_handler= open("../synonyms/property/count.txt","r")
	inside_synonyms=eval(input_file_handler.readline())
	#print inside_synonyms
	input_file_handler.close()

	flag=0
	for i in set(query).intersection(count_synonyms):
		flag=1
		#output_file_handler.write("\n"+i)
	if flag==1: return True

'''


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
	#if(isPropertyCount(query)):	
	#	return 3


def getNamedEntities(query):
	'''
	Funtion to find a list of named entities in the query in tagged form
	@param: query in list format, stripped and split
	@return: a JSON object of tags and a list of named entities
	'''
	ner={}
	ner["NNP"]=[]

	fileR="../synonyms/ner/NNP/"
	for filepath in iglob(os.path.join(fileR, '*.json')): 
		#print filepath
		with open(filepath) as f:
			#print f
			synonym_dict= eval(f.readline())
			for key, value in synonym_dict.items():
				#print key
				#output_file_handler.write(set(query))
				if len(set(query).intersection(value)) >0:
					if key not in ner["NNP"]:
						ner["NNP"].append(key)
					#print key, value

			#print synonym_dict
	return ner






print "GEOGRAPHIC QUESTION ANSWERING SYSTEM"
print "------------------------------------\n"
print "Loading Query from file... "
#time.sleep(2)
#filePath=sys.argv[1]
filePath="hindiQuery.txt"

input_file_handler= open(filePath,"r")
output_file_handler= open("nlpOutput.txt","w")

query=input_file_handler.readline().strip().split()
#print query
input_file_handler.close()

for i in query:
	output_file_handler.write(i+" ")


#Get type of query 
queryProperty = checkQueryProperty(query)
print queryProperty

#Get Query Named Entities
queryNamedEntities= getNamedEntities(query)
print queryNamedEntities

#Define the Query based on the above parameters


output_file_handler.close()


