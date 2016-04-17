# -*- coding: utf-8 -*-
#driver program

import sys
import os

infile_path = "query_info.txt"
os.system('python rule.py')
infile = open(infile_path, 'r')
qtype, data = infile.read().split('\n')
data = eval(data)

qparams = ""
for k in data:
    qparams+= k + ":\"" + ''.join(data[k])+ "\" "

command = "python db_interface.py"
command += " " + qtype
command += " " + qparams
print "command: ", command
os.system(command)
