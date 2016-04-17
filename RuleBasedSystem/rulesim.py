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

	
	if len(set(query).intersection(area_synonyms)) > 0:
		return True

	if len(set(query).intersection(area_synonyms)) > 0:
		return True

		#output_file_handler.write("\n"+i)
	return False


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
		


	else:
		with open("../synonyms/property/big.txt") as f1:
			big_synonyms = eval(f1.readline())
		with open("../synonyms/property/small.txt") as f1:
			small_synonyms = eval(f1.readline())
		big = True
		if len(set(query).intersection(small_synonyms)) > 0:
			big = False

