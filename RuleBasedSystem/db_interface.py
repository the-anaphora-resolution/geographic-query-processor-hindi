#!/usr/bin/pyhton
# -*- coding: utf-8 -*-

__author__ = "Chinmaya Gautam"
__copyright__ = "Copyright 2016, The Anaphora Resolution"
__credits__ = ["Chinamya Gautam", "Harsh Fatehpuria", "Rahul Agrawal", "Simrat Singh Chabbra"]
__license__ = "GPL"
__version__ = "1.0.2"
__maintainer__ = "Chinmaya Gautam"
__email__ = "chinmaya.gautam@usc.edu"
__status__ = "Developement"

'''
note: needs psycopg module for python installed to work, 
and appropriate library paths to be setup
'''
import psycopg2
import sys

class db:
    
    def __init__(self):
        self.conn = psycopg2.connect("dbname=postgres user=postgres password=abcde")
        self.cur = self.conn.cursor()
        self.queries = dict()    #key: query type, value: query template

    def initialize_query_types(self):

        #__PH__ = place holder, needs to be replaced with values
        keys = ['distance', 'direction', 'neighbors', 'size_val', 'count', 'count_intersect']
        queries = [
            "SELECT ST_Distance(T3.G1, T3.G2) FROM (SELECT T1.name_2 AS N1, T1.geom AS G1, T2.name_2 AS N2, T2.geom AS G2 FROM __PH3__ AS T1 CROSS JOIN __PH3__ AS T2 where T1.name_2 like '__PH1__' and T2.name_2 like '__PH2__') AS T3;",
            "",
            "SELECT T3.N2, ST_Touches(T3.G1, T3.G2) AS rval FROM (SELECT T1.name_1 AS N1, T1.geom AS G1, T2.name_1 AS N2, T2.geom AS G2 FROM __PH2__ AS T1 CROSS JOIN __PH2__ AS T2 where T1.name_1 like '__PH1__') AS T3 where ST_Touches(T3.G1, T3.G2) IS TRUE;",
            "SELECT ST_Area(T1.geom) from __PH2__ as T1 where T1.name_1 like '__PH1__';",
            "SELECT T3.name FROM (SELECT T2.name_3 as name, ST_Within(T2.geom, T1.geom) as within from __PH2__ as T1 CROSS JOIN __PH3__ as T2 WHERE T1.name_1 like '__PH1__') as T3 where T3.within is TRUE;",
            "SELECT DISTINCT N1, ST_Intersects(T3.G1, T3.G2) FROM (SELECT T1.nam AS N1, T1.geom AS G1, T2.name_1 AS N2, T2.geom AS G2 FROM __PH3__ AS T1 CROSS JOIN __PH2__ AS T2 where T2.name_1 like '__PH1__') AS T3 where ST_Intersects(T3.G1, T3.G2) IS TRUE;",

            "SELECT DISTINCT T3.N2 FROM (SELECT T1.nam AS N1, T1.geom AS G1, T2.name_1 AS N2, T2.geom AS G2 FROM __PH2__ AS T1 CROSS JOIN administrative1 AS T2 where T1.nam like '__PH1__') AS T3 where ST_Intersects(T3.G1, T3.G2) IS TRUE;",

            "SELECT name_1 FROM administrative2 where name_2 like '__PH1__';",

            "SELECT sum(ST_Length(geom)) FROM __PH2__ where nam like '__PH1__'",

            "SELECT degrees(ST_Azimuth(ST_Centroid(T1.geom), ST_Centroid(T2.geom))) as DEG FROM __PH3__ AS T1 CROSS JOIN __PH3__ AS T2 where T1.name_2 like '__PH1__' and T2.name_2 like '__PH2__';",

            "SELECT capital from __PH2__ where name_1 like '__PH1__';"

            #PH1 = first location, PH2 = second location, PH3 = table
            ]
        
        self.queries['distance'] = queries[0]
        self.queries['size_val'] = queries[3]
        self.queries['count'] = queries[4]
        self.queries['neighbors'] = queries[2]
        self.queries['count_intersect'] = queries[5]
        self.queries['river_intersect'] = queries[6]
        self.queries['city_in'] = queries[7]
        self.queries['river_length'] = queries[8]
        self.queries['direction'] = queries[9]
        self.queries['capital'] = queries[10]

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

inst = db()
qtype = sys.argv[1]
params = sys.argv[2:]

#print "qtype: ", qtype
#print "params: ", params


inst.initialize_query_types()


if qtype == "distance":
    ph = list()
    for e in params:
        k, v = e.split(':')
        if k == 'L1' or k == 'L2':
            qtable = inst.get_table(v)
        ph .append(v)
    ph.append(qtable)
    #print "ph: ", ph
    res = inst.exec_query(qtype,ph)        
    mf = 110
    dist = res[0][0] * mf
    print "distance: ", dist, " KM"

if qtype == "size_val":
    ph = list()
    k,v = params[0].split(':')
    print "v: ", v
    qtable = inst.get_table(v)
    ph.append(v)
    ph.append(qtable)
    #print "ph: ", ph
    res = inst.exec_query(qtype, ph)[0][0]
    #print "res: ", res
    mf = 11313
    area = res * mf
    print "area: ", area, " Sq KM"

if qtype == "count":
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
    print params[1].split(':')[1], "in", params[0].split(':')[1], ": "
    if qsubtype == 'count':
        print len(res)
    else:
        for e in res:
            print e[0]

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
        for r in res:
            print  r[0]

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
            print  r[0]

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
        print  r[0]

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
    #print "ph: ", ph
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

    print "direction: ", direc, " degrees"
    print "direction: ", dir

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

    print "capital of", state_name, ":", res[0][0]


    #mf = 11313
    #area = res * mf
    #print "area: ", area, " Sq KM"

#inst.exec_query(qtype,['\'Delhi\'','\'Kanpur\'','administrative2'])
#inst.exec_query(qtype,['\'Greater Bombay\'','\'Varanasi\'','administrative2'])
inst.close_conn()
