# -*- coding: utf-8 -*-

'''
Program to process the query through a set of rules and  determine the slots
to be filled in the Postgre SQL Query
'''

import sys
import time
from glob import iglob
import json
import os

outfile_path = "query_info.txt"
query_types = ['distance', 'direction', 'neighbors', 'width', 'height', 'length', 'area', 'count', 'size']

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


def isPropertyCount(query):
	'''
	Function to check if the query is a Count query or not
	@param: query in list format, stripped and split
	@return: True if a count query, False otherwise
	'''
	input_file_handler= open("../synonyms/property/count.txt","r")
	count_synonyms=eval(input_file_handler.readline())
	#print inside_synonyms
	input_file_handler.close()

	#Get Query Named Entities
	queryNamedEntities= getNamedEntities(query)

	flag=0
	for i in set(query).intersection(count_synonyms):
		if len(queryNamedEntities["NN"])==1 and len(queryNamedEntities["NNP"])==1:
			flag=1
		#output_file_handler.write("\n"+i)
	if flag==1: return True

# -*- coding: utf-8 -*-
def isPropertySize(query):
	'''
	Function to check if the query is a Size query or not
	@param: query in list format, stripped and split
	@return: True if a area query, False otherwise
	'''
	input_file_handler= open("../synonyms/property/area.txt","r")
	area_synonyms=eval(input_file_handler.readline())
	#print area_synonyms
	input_file_handler.close()
	input_file_handler= open("../synonyms/property/length.txt","r")
	length_synonyms=eval(input_file_handler.readline())
	
	input_file_handler.close()
	#print printObject(area_synonyms)
	print query[3]

	#hexdump.dump(query[3].decode("utf-8"))
	#hexdump.dump(area_synonyms[1])
	print area_synonyms[1]


	#for x in area_synonyms:
		#print x == query[3]
	print printObject(set(query).intersection(area_synonyms))
	if len(set(query).intersection(area_synonyms)) > 0:
		return True

	if len(set(query).intersection(length_synonyms)) > 0:
		return True

		#output_file_handler.write("\n"+i)
	return False

def printObject(obj):
	for a in obj:
		print a.decode("utf-8")

def getSizeParameters(query):

	queryNamedEntities= getNamedEntities(query)
	
	with open("../synonyms/property/number.json") as f1:
		numdic = eval(f1.readline())
		

	cnt = -1
	for num in numdic:
		if len(set(query).intersection(numdic[num])) > 0:
			cnt = num
	if cnt == -1:
		if "सबसे" in query:
			cnt = 1
	if cnt == -1:
		paradic = dict()
		paradic["NNP"] = queryNamedEntities["NNP"]
		paradic["GET"] = "size"
		
		return paradic

	else:
		with open("../synonyms/property/big.txt") as f1:
			big_synonyms = eval(f1.readline())
		with open("../synonyms/property/small.txt") as f1:
			small_synonyms = eval(f1.readline())
		big = True
		if len(set(query).intersection(small_synonyms)) > 0:
			big = False
		paradic = dict()
		paradic["count"] = cnt
		if big:
			paradic["sort"] = "high"
		else:
			paradic["sort"] = "low"
		if len(queryNamedEntities["NNP"]) > 0:
			paradic["in"] = queryNamedEntities["NNP"][0]
		paradic["GET"] = "names"
		return paradic





def checkQueryProperty(query):
	'''
	Function to find the property of a query
	@param: query in list format, stripped and split
	@return: Type of query 
	'''
	if(isPropertyDistance(query)):
		return query_types[0]
	if isPropertySize(query):
		return query_types[8]
	if(isPropertyCount(query)):	
		return query_types[7]
	#if(isPropertyLength(query)):
	#	return query_types[5]
	#if(isPropertyArea(query)):
	#	return query_types[6]
	


def getNamedEntities(query):
	'''
	Funtion to find a list of named entities in the query in tagged form
	@param: query in list format, stripped and split
	@return: a JSON object of tags and a list of named entities
	'''
	ner={}

	#Finding NNP ners
	flag=0
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
					if flag==0:
						flag=1
						ner["NNP"]=[]
					if key not in ner["NNP"]:
						ner["NNP"].append(key)
					#print key, value
			#print synonym_dict

	#Finding NN ners
	flag=0	
	fileR="../synonyms/ner/NN/"
	for filepath in iglob(os.path.join(fileR, '*.json')): 
		#print filepath
		with open(filepath) as f:
			#print f
			synonym_dict= eval(f.readline())
			for key, value in synonym_dict.items():
				#print key
				#output_file_handler.write(set(query))
				if len(set(query).intersection(value)) >0:
					if flag==0:
						flag=1
						ner["NN"]=[]
					if key not in ner["NN"]:
						ner["NN"].append(key)
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
output_file_handler= open(outfile_path,"w")

#removing ? mark from the end of the input query
queryString=input_file_handler.readline().strip()
if queryString[-1:]=="?":
	queryString=queryString[:-1]

query=queryString.split()
#print query
input_file_handler.close()

#Get type of query 
queryProperty = checkQueryProperty(query)
output_file_handler.write(str(queryProperty) + "\n")
print queryProperty

#Get Query Named Entities
if queryProperty == "distance":
	queryNamedEntities= getNamedEntities(query)
	output_file_handler.write(str(queryNamedEntities))
	print queryNamedEntities
elif queryProperty == "size":
	sizeParameters = getSizeParameters(query)
	output_file_handler.write(str(sizeParameters))
	print sizeParameters
elif queryProperty == "count":
	queryNamedEntities= getNamedEntities(query)
	output_file_handler.write(str(queryNamedEntities))
	print queryNamedEntities

#Define the Query based on the above parameters

output_file_handler.close()


