#!/usr/bin/pyhton
# -*- coding: utf-8 -*-

__author__ = "Chinmaya Gautam"
__copyright__ = "Copyright 2016, The Anaphora Resolution"
__credits__ = ["Chinamya Gautam", "Harsh Fatepuria", "Rahul Agrawal", "Simrat Singh Chabbra"]
__license__ = "GPL"
__version__ = "1.0.2"
__maintainer__ = "Chinmaya Gautam"
__email__ = "chinmaya.gautam@usc.edu"
__status__ = "Developement"

'''
note: needs psycopg2 and google-api-python-client module for python installed to work, 
and appropriate library paths to be setup
'''
import psycopg2
import sys
from apiclient.discovery import build
from glob import iglob
import json
import os

class db:
    
    def __init__(self):
        self.conn = psycopg2.connect("dbname=postgres user=postgres password=abcde")
        self.cur = self.conn.cursor()
        self.queries = dict()    #key: query type, value: query template

    def initialize_query_types(self):

        #__PH__ = place holder, needs to be replaced with values
        keys = ['distance', 'direction', 'neighbors', 'size_val', 'count', 'count_intersect']
        queries = [
            "SELECT ST_Distance(T3.G1, T3.G2) FROM (SELECT T1.name AS N1, T1.geom AS G1, T2.name AS N2, T2.geom AS G2 FROM __PH2__ AS T1 CROSS JOIN __PH4__ AS T2 where T1.name like '__PH1__' and T2.name like '__PH3__') AS T3;",
            "SELECT DISTINCT T3.N2, ST_Touches(T3.G1, T3.G2) AS rval FROM (SELECT T1.name AS N1, T1.geom AS G1, T2.name AS N2, T2.geom AS G2 FROM __PH2__ AS T1 CROSS JOIN __PH2__ AS T2 where T1.name like '__PH1__') AS T3 where ST_Touches(T3.G1, T3.G2) IS TRUE;",
            "SELECT ST_Area(T1.geom) from __PH2__ as T1 where T1.name like '__PH1__';",
            "SELECT DISTINCT T3.name FROM (SELECT T2.name as name, ST_Within(T2.geom, T1.geom) as within from __PH2__ as T1 CROSS JOIN __PH3__ as T2 WHERE T1.name like '__PH1__') as T3 where T3.within is TRUE;",
            "SELECT DISTINCT N1, ST_Intersects(T3.G1, T3.G2) FROM (SELECT T1.name AS N1, T1.geom AS G1, T2.name AS N2, T2.geom AS G2 FROM __PH3__ AS T1 CROSS JOIN __PH2__ AS T2 where T2.name like '__PH1__') AS T3 where ST_Intersects(T3.G1, T3.G2) IS TRUE;",
            "SELECT DISTINCT T3.N2 FROM (SELECT T1.name AS N1, T1.geom AS G1, T2.name AS N2, T2.geom AS G2 FROM __PH2__ AS T1 CROSS JOIN administrative1 AS T2 where T1.name like '__PH1__') AS T3 where ST_Intersects(T3.G1, T3.G2) IS TRUE;",
            "SELECT name_1 FROM administrative2 where name like '__PH1__';",
            "SELECT sum(ST_Length(geom)) FROM __PH2__ where name like '__PH1__'",
            "SELECT degrees(ST_Azimuth(ST_Centroid(T1.geom), ST_Centroid(T2.geom))) as DEG FROM __PH2__ AS T1 CROSS JOIN __PH4__ AS T2 where T1.name like '__PH1__' and T2.name like '__PH3__';",
            "SELECT capital from __PH2__ where name like '__PH1__';",
            "SELECT DISTINCT T2. N2, degrees(ST_Azimuth(ST_Centroid(T1.geom), ST_Centroid(G2))) as DEG FROM __PH2__ AS T1 CROSS JOIN (SELECT T7.N2, T7.G2, ST_Touches(T7.G1, T7.G2) AS rval FROM (SELECT T5.name AS N1, T5.geom AS G1, T6.name AS N2, T6.geom AS G2 FROM __PH2__ AS T5 CROSS JOIN __PH2__ AS T6 where T5.name like '__PH1__') AS T7 where ST_Touches(T7.G1, T7.G2) IS TRUE) AS T2 where T1.name like '__PH1__';"


            #PH1 = first location, PH2 = second location, PH3 = table
            ]
        
        self.queries['distance'] = queries[0]
        self.queries['neighbors'] = queries[1]
        self.queries['size_val'] = queries[2]
        self.queries['within'] = queries[3]
        self.queries['count_intersect'] = queries[4]
        self.queries['river_intersect'] = queries[5]
        self.queries['city_in'] = queries[6]
        self.queries['river_length'] = queries[7]
        self.queries['direction'] = queries[8]
        self.queries['capital'] = queries[9]
        self.queries['neighbor_direction'] = queries[10]

    def exec_query(self, qtype, phs):
        #qtype = query types
        #ph = list of place holders in order
        query = self.queries[qtype]
        
        for i,ph_val in enumerate(phs):
            ph_template = "__PH" + str(i+1) + "__"
            #print ph_template
            temp = query.split(ph_template)
            query = ph_val.join(temp)
        
        #print query
        print ""
        print "query: ", query
        self.cur.execute(query)
        data = ""
        data = self.cur.fetchall()
        #print data
        return data

    def close_conn(self):
        self.conn.commit()
        self.cur.close()
        self.conn.close()

    def get_table(self, name):
        #get the table having data for the name
        map_file = open('table_map.txt', 'r')
        mapping = eval(map_file.read())
        table_name = mapping[name]
        map_file.close()

        return table_name

    def process_query(self, qtype):
        if qtype == "distance":
            ph = list()
            for e in params:
                k, v = e.split(':')
                if k == 'L1' or k == 'L2':
                    qtable = inst.get_table(v)
                ph .append(v)
                ph.append(qtable)
            print "ph: ", ph
            res = inst.exec_query(qtype,ph)        
            mf = 110
            dist = res[0][0] * mf
            print ""
            print "दूरी : ", dist, " KM"
        
        if qtype == "size_val":
            ph = list()
            k,v = params[0].split(':')
            print ""
            # print "v: ", v
            qtable = inst.get_table(v)
            ph.append(v)
            ph.append(qtable)
            #print "ph: ", ph
            res = inst.exec_query(qtype, ph)[0][0]
            #print "res: ", res
            mf = 11313
            area = res * mf
            print ""
            print convertToHindi(v), " का  क्षेत्र फल : ", area, " Sq KM"
        
        if qtype == "within":
            ph = list()
            qsubtype = 'list'
            for e in params:
                k,v = e.split(':')
                if k == 'L1':
                    table_X = inst.get_table(v)
                    ph.append(v)
                if k == 'L2':
                    table_Y = inst.get_table(v)
                if k == 'result':
                    qsubtype = v
            ph.append(table_X)
            ph.append(table_Y)
            #print "ph: ", ph
            if table_Y == "water_lines":
                qtype = "count_intersect"
            res = inst.exec_query(qtype, ph)
            # print params[1].split(':')[1], "in", params[0].split(':')[1], ": "
            print ""
            print convertToHindi(params[0].split(':')[1])," की ",convertToHindi(params[1].split(':')[1]), ": "
            if qsubtype == 'count':
                print len(res)
            else:
                for e in res:
                    print convertToHindi(e[0])
        
        if qtype == "neighbors":
            ph = list()
            qsubtype = "list"
            for e in params:
                k,v = e.split(':')
                if k == "L1":
                    ph.append(v)
                    qtable = inst.get_table(v)
                if k == "result":
                    qsubtype = v
            ph.append(qtable)
            res = inst.exec_query(qtype, ph)
            if qsubtype == "count":
                print len(res)
            else:
                print convertToHindi(v),"के पडोसी राज्य :"
                for r in res:
                    print  convertToHindi(r[0])
        
        if qtype == "river_intersect":
            ph = list()
            qsubtype = "list"
            for e in params:
                k,v = e.split(':')
                if k == "L1":
                    ph.append(v)
                    qtable = inst.get_table(v)
                if k == "result":
                    qsubtype = v
            ph.append(qtable)
            res = inst.exec_query(qtype, ph)
            if qsubtype == "count":
                print len(res)
            else:
                for r in res:
                    print  convertToHindi(r[0])
        
        if qtype == "city_in":
            ph = list()
        
            for e in params:
                k,v = e.split(':')
                if k == "L1":
                    ph.append(v)
                    qtable = inst.get_table(v)
        
            ph.append(qtable)
            res = inst.exec_query(qtype, ph)
        
            for r in res:
                print  convertToHindi(r[0])
        
        if qtype == "river_length":
            ph = list()
        
            for e in params:
                k,v = e.split(':')
                if k == "L1":
                    ph.append(v)
                    qtable = inst.get_table(v)
        
            ph.append(qtable)
            res = inst.exec_query(qtype, ph)
        
            mf = 248
            for r in res:
                print  r[0] * mf
        
        if qtype == "direction":
            ph = list()
            for e in params:
                k, v = e.split(':')
                if k == 'L1' or k == 'L2':
                    qtable = inst.get_table(v)
                ph .append(v)
                ph.append(qtable)
            print "ph: ", ph
            res = inst.exec_query(qtype,ph)        
            direc = res[0][0]
        
            if (direc >= 337.5 and direc <=360) or (direc >= 0 and direc <= 22.5):
                dir = "North"
            elif direc >= 22.5 and direc <= 67.5:
                dir = "North-East"
            elif direc >= 67.5 and direc <=112.5:
                dir = "East"
            elif direc >= 112.5 and direc <= 157.5:
                dir = "South-East"
            elif direc >= 157.5 and direc <= 202.5:
                dir = "South"
            elif direc >=202.5 and direc <= 247.5:
                dir = "South-West"
            elif direc >= 247.5 and direc <= 292.5:
                dir = "West"
            elif direc >=292.5 and direc <= 337.5:
                dir = "North-West"
        
            print "दिशा: ", direc, " degrees"
            print "दिशा: ", convertToHindi(dir.lower())
        
        if qtype == "neighbor_direction":
            print "neighbor direction"
            rdirec = ""
            ph = list()
            for e in params:
                k, v = e.split(':')
                if k == 'L1':
                    qtable = inst.get_table(v)
                    ph .append(v)
                if k == 'direction':
                    rdirec = v
                
            ph.append(qtable)
            print ""
            # print "ph: ", ph
            res = inst.exec_query(qtype,ph)        
            ans = list()
            print ""
            print "आवश्यक दिशा: ", convertToHindi(rdirec)
            for r in res:
                #print r[0], r[1]
                direc = r[1]
                if ((direc >= 315 and direc <=360) or (direc >= 0 and direc <= 45)) and rdirec == "north":
                    ans.append(r[0])
                elif direc >= 0 and direc <= 90 and rdirec == "north-east":
                    ans.append(r[0])
                elif direc >= 45 and direc <=135 and rdirec == "east":
                    ans.append(r[0])
                elif direc >= 90 and direc <= 180 and rdirec == "south-east":
                    ans.append(r[0])
                elif direc >= 135 and direc <= 215 and rdirec == "south":
                    ans.append(r[0])
                elif direc >=180 and direc <= 270 and rdirec == "south-west":
                    ans.append(r[0])
                elif direc >= 225 and direc <= 315 and rdirec == "west":
                    ans.append(r[0])
                elif direc >=270 and direc <= 360 and rdirec == "north-west":
                    ans.append(r[0])
        
            ans = list(set(ans))
            for a in ans:
                print convertToHindi(a)
        
        
        
        
        if qtype == "capital":
            ph = list()
            state_name = ""
            for e in params:
                k,v = e.split(':')
                if k == "L1":
                    ph.append(v)
                    state_name = v
                    qtable = inst.get_table(v)
        
            ph.append(qtable)
            res = inst.exec_query(qtype, ph)
        
            print ""
            # print "capital of", state_name, ":", res[0][0]
            print convertToHindi(state_name), "की राजधानी :", convertToHindi(res[0][0])




def googleTranslate(word):
  service = build('translate', 'v2', developerKey='AIzaSyBEnSzexXv-Ve1E-d9rjHvygguF6rX9I8U')
  return service.translations().list(
      source='en',
      target='hi',
      q=word.decode('utf-8')
    ).execute()["translations"][0]["translatedText"]

def convertToHindi(word):
    flag=0
    fileR="../synonyms/ner/NNP/"
    for filepath in iglob(os.path.join(fileR, '*.json')): 
        #print filepath
        with open(filepath) as f:
            #print f
            synonym_dict= eval(f.readline())
            if word in synonym_dict:
                return synonym_dict[word][0]
    return googleTranslate(word)


inst = db()
qtype = sys.argv[1]
params = sys.argv[2:]

#print "qtype: ", qtype
#print "params: ", params


inst.initialize_query_types()

    #mf = 11313
    #area = res * mf
    #print "area: ", area, " Sq KM"

#inst.exec_query(qtype,['\'Delhi\'','\'Kanpur\'','administrative2'])
#inst.exec_query(qtype,['\'Greater Bombay\'','\'Varanasi\'','administrative2'])
inst.process_query(qtype)
inst.close_conn()
'''
try:
    inst.process_query(qtype)
except:
    err_file = open('error_file.txt', 'r')
    err_msg = err_file.read()
    print err_msg
inst.close_conn()
'''
