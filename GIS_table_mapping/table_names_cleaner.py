'''
Program to clean mapping data from names.txt
'''

table=[]
import re

op= open("n2.txt","w")
op.write("{")
s="]:["

with open("names.txt","r") as f:
	data=f.read()
	data=data.strip().split("\n")
	#print data[1]
	
	for line in data:
		if re.match("-(-+)-", line):
			print line, data.index(line)
			#str.replace(line, s)
		line=line.lstrip()
		line=line.rstrip()
		op.write("\'"+line+"\'"+",")
op.close()

