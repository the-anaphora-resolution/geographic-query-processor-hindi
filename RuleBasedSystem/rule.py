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
query_types = ['distance', 'direction', 'neighbors', 'width', 'height', 'river_length', 'area', 'within', 'size_list', 'size_val', 'capital','city_in','neighbor_direction', 'river_intersect']


def checkQueryProperty(query):
	'''
	Function to find the property of a query
	@param: query in list format, stripped and split
	@return: Type of query 
	'''
	if(isPropertyNeighborDirection(query)):	
		return query_types[12]
	if(isPropertyDistance(query)):
		return query_types[0]
	if isPropertySizeList(query):
		return query_types[8]
	if isPropertyLength(query):
		return query_types[5]
	if isPropertySizeVal(query):
		return query_types[9]
	if(isPropertyDirection(query)):	
		return query_types[1]
	if isPropertyCapital(query):
		return query_types[10]
	if(isPropertyNeighbors(query)):	
		return query_types[2]
	if(isPropertyWithin(query)):	
		return query_types[7]
	if isPropertyRiverIntersect(query):
		return query_types[13]
	if(isPropertyCityIn(query)):
		return query_types[11]

	#if(isPropertyArea(query)):
	#	return query_types[6]


def printObject(obj):
	'''
	Function to print object in decoded form
	@param: object to print
	@return: null
	'''
	for a in obj:
		print a.decode("utf-8")

def isPropertyNeighborDirection(query):
	'''
	Function to find the Neighbor_Direction property of a query
	@param: query in list format, stripped and split
	@return: Neighbor_Direction in query 
	'''
	with open("../synonyms/property/direction.json") as f1:
		numdic = eval(f1.readline())
	cnt = -1
	for num in numdic:
		if len(set(query).intersection(numdic[num])) > 0:
			print cnt
			cnt = num
			break
	queryNamedEntities= getNamedEntities(query)
	if cnt != -1 and "NNP" in queryNamedEntities :
		return True
	else:
		return False


def isPropertyDirection(query):
	'''
	Function to find the Direction property of a query
	@param: query in list format, stripped and split
	@return: Direction List property in query 
	'''
	input_file_handler= open("../synonyms/property/directions_synonym.txt","r")
	direction_synonyms=eval(input_file_handler.readline())
	#print distance_synonyms
	input_file_handler.close()
	queryNamedEntities= getNamedEntities(query)
	
	if len(set(query).intersection(direction_synonyms))>0 and (queryNamedEntities.get("NNP",0)!=0) and (len(queryNamedEntities["NNP"])==2) and ("से" in query or "के" in query):
		return True
	else:
		return False

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


def isPropertyNeighbors(query):
	'''
	Function to check if the query is a Neighbors query or not
	@param: query in list format, stripped and split
	@return: True if a neighbors query, False otherwise
	'''
	input_file_handler= open("../synonyms/property/neighbors.txt","r")
	neighbors_synonyms=eval(input_file_handler.readline())
	#print inside_synonyms
	input_file_handler.close()

	#Get Query Named Entities
	queryNamedEntities= getNamedEntities(query)

	flag=0
	for i in set(query).intersection(neighbors_synonyms):
		if len(queryNamedEntities["NNP"])==1:
			flag=1
		#output_file_handler.write("\n"+i)
	if flag==1: return True




def isNNP(word):
	'''
	Funtion to find if a word is NNP
	@param: query - a word to check for NNP
	@return: True if NNP, False Otherwise
	'''
	fileR="../synonyms/ner/NNP/"
	for filepath in iglob(os.path.join(fileR, '*.json')): 
		#print filepath
		with open(filepath) as f:
			#print f
			synonym_dict= eval(f.readline())
			for key, value in synonym_dict.items():
				#print key
				#output_file_handler.write(set(query))
				if word in value:
					#print "check 1"
					return True
					#print key, value
			#print synonym_dict
	#print "check 2"
	return False



def isPropertyWithin(query):
	'''
	Function to check if the query is a Within query or not
	@param: query in list format, stripped and split
	@return: True if a count query, False otherwise
	'''
	# input_file_handler= open("../synonyms/property/count.txt","r")
	# count_synonyms=eval(input_file_handler.readline())
	# #print inside_synonyms
	# input_file_handler.close()

	#Get Query Named Entities
	queryNamedEntities= getNamedEntities(query)

	if "में" in query:
		idx= query.index("में")
		input_file_handler= open("../synonyms/ner/NN/cnsynlist.json","r")
		state_synonyms=eval(input_file_handler.readline())
		#print inside_synonyms
		input_file_handler.close()
		if query[idx-1] in state_synonyms["state"]: 
			#check query[idx-2]
			if (isNNP(query[idx-2])) == False:
				return False
		return True

	flag=0
	if "NN" in queryNamedEntities and "NNP" in queryNamedEntities and len(queryNamedEntities["NN"])==1 and len(queryNamedEntities["NNP"])==1:
		return True
		#output_file_handler.write("\n"+i)
	return False



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
	# input_file_handler= open("../synonyms/property/length.txt","r")
	# length_synonyms=eval(input_file_handler.readline())
	
	# input_file_handler.close()
	# #print printObject(area_synonyms)
	#print query[3]

	#hexdump.dump(query[3].decode("utf-8"))
	#hexdump.dump(area_synonyms[1])
	#print area_synonyms[1]

	#for x in area_synonyms:
		#print x == query[3]
	#print printObject(set(query).intersection(area_synonyms))
	if len(set(query).intersection(area_synonyms)) > 0:
		return True

	# if len(set(query).intersection(length_synonyms)) > 0:
	# 	return True

		#output_file_handler.write("\n"+i)
	return False

def isPropertyLength(query):
	'''
	Function to check if the query is a Length query or not
	@param: query in list format, stripped and split
	@return: True if a area query, False otherwise
	'''
	input_file_handler= open("../synonyms/property/length.txt","r")
	length_synonyms=eval(input_file_handler.readline())
	
	input_file_handler.close()
	# #print printObject(area_synonyms)
	#print query[3]

	#hexdump.dump(query[3].decode("utf-8"))
	#hexdump.dump(area_synonyms[1])
	#print area_synonyms[1]

	#for x in area_synonyms:
		#print x == query[3]
	#print printObject(set(query).intersection(area_synonyms))
	if len(set(query).intersection(length_synonyms)) > 0:
		return True

	# if len(set(query).intersection(length_synonyms)) > 0:
	# 	return True

		#output_file_handler.write("\n"+i)
	return False




def isPropertyCapital(query):
	'''
	Function to check if the query is a Capital query or not
	@param: query in list format, stripped and split
	@return: True if a area query, False otherwise
	'''
	input_file_handler= open("../synonyms/property/capital.txt","r")
	capital_synonyms=eval(input_file_handler.readline())
	#print area_synonyms
	input_file_handler.close()
	#print printObject(area_synonyms)
	#print query[3]

	#hexdump.dump(query[3].decode("utf-8"))
	#hexdump.dump(area_synonyms[1])
	#print area_synonyms[1]

	#for x in area_synonyms:
		#print x == query[3]
	#print printObject(set(query).intersection(area_synonyms))
	if len(set(query).intersection(capital_synonyms)) > 0:
		return True


		#output_file_handler.write("\n"+i)
	return False




def isPropertyRiverIntersect(query):
	'''
	Function to check if the query is a River Intersect query or not
	@param: query in list format, stripped and split
	@return: True if a area query, False otherwise
	'''
	input_file_handler= open("../synonyms/property/flow.txt","r")
	flow_synonyms=eval(input_file_handler.readline())
	input_file_handler.close()
	if len(set(query).intersection(flow_synonyms)) > 0:
		return True


		
	return False




def isPropertySizeList(query):
	'''
	Function to find the property of a query
	@param: query in list format, stripped and split
	@return: True if query is a SizeList query, False otherwise
	'''
	if  not isPropertySize(query):
		return False
	cnt = getCountNeeded(query)
	print cnt
	if cnt == -1:
		return False
	return True

	

def isPropertySizeVal(query):
	'''
	Function to check if the query is a SizeVal query or not
	@param: query in list format, stripped and split
	@return: True if a SizeVal query, False otherwise
	'''
	if  not isPropertySize(query):
		return False
	cnt = getCountNeeded(query)
	if cnt != -1:
		return False
	
	return True
	



def isPropertyCityIn(query):
	'''
	Function to find the city_in property of a query
	@param: query in list format, stripped and split
	@return: True if query is a city_in query, False otherwise
	'''
	
	if "कहाँ" in query:
		return True
	elif "किस" in query:
		return True
	elif "कौन" in query:
		return True
	elif "कौनसे" in query:
		return True
	else:
		return False
	


def getCountNeeded(query):
	'''
	Function to find the Count property of a query
	@param: query in list format, stripped and split
	@return: Count in query 
	'''
	
	with open("../synonyms/property/number.json") as f1:
		numdic = eval(f1.readline())
		

	cnt = -1
	for num in numdic:
		if len(set(query).intersection(numdic[num])) > 0:
			cnt = num
	if cnt == -1:
		if "सबसे" in query:
			cnt = 1
	print cnt
	return cnt	




def getSizeValParameters(query):
	'''
	Function to find the Size Val property of a query
	@param: query in list format, stripped and split
	@return: Size property in query 
	'''

	queryNamedEntities= getNamedEntities(query)
	paradic = dict()
	paradic["L1"] = queryNamedEntities["NNP"][0]
	
	return paradic


def getCityInParameters(query):
	'''
	Function to find the City In property of a query
	@param: query in list format, stripped and split
	@return: Size property in query 
	'''

	queryNamedEntities= getNamedEntities(query)
	paradic = dict()
	paradic["L1"] = queryNamedEntities["NNP"][0]
	
	return paradic


def getRiverIntersect(query):
	'''
	Function to find the River Intersect property of a query
	@param: query in list format, stripped and split
	@return: Size property in query 
	'''

	queryNamedEntities= getNamedEntities(query)
	paradic = dict()
	paradic["L1"] = queryNamedEntities["NNP"][0]
	
	return paradic




def getSizeListParameters(query):
	'''
	Function to find the Size List property of a query
	@param: query in list format, stripped and split
	@return: Size List property in query 
	'''

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
		paradic["location"] = queryNamedEntities["NNP"][0]
	else:
		paradic["location"] = "India"
	paradic["entity"] = queryNamedEntities["NN"][0]
	return paradic


def getNeighborsParameters(query, queryNamedEntities):
	'''
	Function to find the property of neighbors of a query
	@param: query in list format, stripped and split
	@return: List of neighbours 
	'''

	paradic = dict()
	paradic['L1'] = queryNamedEntities["NNP"][0]
	input_file_handler= open("../synonyms/property/count.txt","r")
	count_synonyms=eval(input_file_handler.readline())
	#print inside_synonyms
	input_file_handler.close()
	if len(set(query).intersection(count_synonyms)) > 0:
		paradic['result'] = "count"
	else:
		paradic['result'] = "list"
	return paradic

def getCapitalParameters(queryNamedEntities):
	'''
	Function to find the property of capital of a query
	@param: query in list format, stripped and split
	@return: List of neighbours 
	'''

	paradic = dict()
	paradic['L1'] = queryNamedEntities["NNP"][0]
	
	return paradic



	
def getLocationParameters(query):
	queryNamedEntities= getNamedEntities(query)
	paradic = dict()
	paradic['L1'] = queryNamedEntities["NNP"][0]
	paradic['L2'] = queryNamedEntities["NNP"][1]
	return paradic

def getDirectionParameters(query):
	queryNamedEntities= getNamedEntities(query)
	result={}
	hindi_list=[]
	#Finding NNP ners
	fileR="../synonyms/ner/NNP/"

	for filepath in iglob(os.path.join(fileR, '*.json')): 
		with open(filepath) as f:
			synonym_dict= eval(f.readline())
			for key, value in synonym_dict.items():
				if len(set(nounset).intersection(value)) >0:
					for val in value:
						if val in set(nounset) and val not in hindi_list:
							hindi_list.append(val)
							break
	# print hindi_list[0].decode('utf-8')
	# print hindi_list[1].decode('utf-8')
	key_word3=query.index(hindi_list[0])
	key_word4=query.index(hindi_list[1])
	
	if "से" in query:
		key_word1=query.index("से")
		if key_word3==key_word1-1:
			result["L2"]=queryNamedEntities["NNP"][0]
			result["L1"]=queryNamedEntities["NNP"][1]
		elif key_word4==key_word1-1:
			result["L2"]=queryNamedEntities["NNP"][1]
			result["L1"]=queryNamedEntities["NNP"][0]

	elif "के" in query:
		key_word2=query.index("के")
		if key_word3==key_word2-1:
			result["L2"]=queryNamedEntities["NNP"][0]
			result["L1"]=queryNamedEntities["NNP"][1]
		elif key_word4==key_word2-1:
			result["L2"]=queryNamedEntities["NNP"][1]
			result["L1"]=queryNamedEntities["NNP"][0]
	return result


def getNeighborDirectionParameters(query):
	queryNamedEntities= getNamedEntities(query)
	with open("../synonyms/property/direction.json") as f1:
		numdic = eval(f1.readline())
	cnt=""
	for num in numdic:
		if len(set(query).intersection(numdic[num])) > 0:
			cnt = num
			break
	result={}
	result["L1"]=queryNamedEntities["NNP"][0]
	result["direction"]=cnt
	return result


def getNamedEntities(query):
	'''
	Funtion to find a list of named entities in the query in tagged form
	@param: query in list format, stripped and split
	@return: a JSON object of tags and a list of named entities
	'''
	ner={}

	#Finding NNP ners
	#print printObject(nounset)
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
				if len(set(nounset).intersection(value)) >0:
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




def getWithinParameters(queryNamedEntities):
	'''
	Function to find the Count parameters of a query
	@param: query in list format, stripped and split
	@return: Is query a list query or a count query
	'''
	result={}
	result["L1"]=queryNamedEntities["NNP"][0]
	result["L2"]=queryNamedEntities["NN"][0]
	if len(queryNamedEntities["NN"])>1 and queryNamedEntities["NN"][0]=='state':
		result["L2"]=queryNamedEntities["NN"][1]
	input_file_handler= open("../synonyms/property/count.txt","r")
	count_synonyms=eval(input_file_handler.readline())
	#print inside_synonyms
	input_file_handler.close()
	if len(set(query).intersection(count_synonyms)) > 0:
		result['result'] = "count"
	else:
		result['result'] = "list"
	return result



'''
Main Execution point of the program begins here-->
'''
print "GEOGRAPHIC QUESTION ANSWERING SYSTEM"
print "------------------------------------\n"
print "Loading Query from file... "
#time.sleep(2)
#filePath=sys.argv[1]
filePath="hindiQuery.txt"

input_file_handler= open(filePath,"r")
output_file_handler= open(outfile_path,"w")


'''
Integrating Shallow Parser here -->
Finds Nouns in a string, which we use to find NERs (Named Entities) in query
'''
nounset = set()
useShallowParser = False
if (useShallowParser):
	os.system("python ../shallow_parser_interface.py " + filePath)
	with open("parsed_data.txt", "r") as f1:
		posmap = dict()
		for line in f1:
			ind = line.find("af")
			if ind!=-1:
				posmap[line[ind+4:(line.find(","))]] = line.split(",")[1]
		for ent in posmap:
			if posmap[ent] == 'n':
				nounset.add(ent)


#print len(nounset)
#print printObject(nounset)
##a = raw_input() 
#removing ? mark from the end of the input query
queryString=input_file_handler.readline().strip()
if queryString[-1:]=="?":
	queryString=queryString[:-1]

query = queryString.replace("-", "_")
query=query.rstrip('\n').split()
if not useShallowParser:
	nounset = set(query)
#print query
input_file_handler.close()

#Get type of query 
queryProperty = checkQueryProperty(query)
output_file_handler.write(str(queryProperty) + "\n")
print queryProperty

#Get Query Named Entities
if queryProperty == "distance":
	distanceParameters= getLocationParameters(query)
	output_file_handler.write(str(distanceParameters))
	print distanceParameters
elif queryProperty == "size_list":
	sizeParameters = getSizeListParameters(query)
	output_file_handler.write(str(sizeParameters))
	print sizeParameters
elif queryProperty == 'river_length':
	len_valParameters = getSizeValParameters(query) #Using same method since result is same
	output_file_handler.write(str(len_valParameters))
	print len_valParameters
elif queryProperty == "size_val":
	size_valParameters = getSizeValParameters(query)
	output_file_handler.write(str(size_valParameters))
	print size_valParameters
elif queryProperty == "direction":
	directionParameters= getDirectionParameters(query)
	output_file_handler.write(str(directionParameters))
	print directionParameters
elif queryProperty == "capital":
	queryNamedEntities= getNamedEntities(query)
	capitalProperties=getCapitalParameters(queryNamedEntities)
	output_file_handler.write(str(capitalProperties))	
	print capitalProperties
elif queryProperty == "within":
	queryNamedEntities= getNamedEntities(query)
	queryNamedEntities=getWithinParameters(queryNamedEntities)
	output_file_handler.write(str(queryNamedEntities))
	print queryNamedEntities
elif queryProperty == "neighbors":
	queryNamedEntities= getNamedEntities(query)
	queryNamedEntities=getNeighborsParameters(query,queryNamedEntities)
	output_file_handler.write(str(queryNamedEntities))
	print queryNamedEntities	
elif queryProperty == "river_intersect":
	riverintersectproperty=getRiverIntersect(query)
	output_file_handler.write(str(riverintersectproperty))
	print riverintersectproperty
elif queryProperty == "neighbor_direction":
	neighborDirectionEntities=getNeighborDirectionParameters(query)
	output_file_handler.write(str(neighborDirectionEntities))
	print neighborDirectionEntities
#Last Rule- city_in query
elif queryProperty == "city_in":
	queryNamedEntities= getNamedEntities(query)
	queryNamedEntities=getCityInParameters(queryNamedEntities)
	output_file_handler.write(str(queryNamedEntities))
	print queryNamedEntities



#Define the Query based on the above parameters

output_file_handler.close()


