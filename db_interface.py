#!/usr/bin/pyhton

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

class db:
    
    def __init__(self):
        self.conn = psycopg2.connect("dbname=postgres user=postgres password=abcde")
        self.cur = self.conn.cursor()
        self.queries = dict()    #key: query type, value: query template

    def initialize_query_types(self):

        #__PH__ = place holder, needs to be replaced with values
        keys = ['distance', 'direction', 'neighbors', 'width', 'height', 'length']
        queries = [
            "SELECT ST_Distance(T3.G1, T3.G2) FROM (SELECT T1.name_2 AS N1, T1.geom AS G1, T2.name_2 AS N2, T2.geom AS G2 FROM __PH3__ AS T1 CROSS JOIN __PH3__ AS T2 where T1.name_2 like __PH1__ and T2.name_2 like __PH2__) AS T3;"
            #PH1 = first location, PH2 = second location, PH3 = table
            ]

        # ^^^ this needs to be populated
        
        self.queries['distance'] = queries[0]
        
        
    def exec_query(self, qtype, phs):
        #qtype = query types
        #ph = list of place holders in order
        query = self.queries[qtype]
        
        for i,ph_val in enumerate(phs):
            ph_template = "__PH" + str(i+1) + "__"
            print ph_template
            temp = query.split(ph_template)
            query = ph_val.join(temp)
        
        print query

        self.cur.execute(query)
        data = self.cur.fetchall()
        print data
        return data

    def close_conn(self):
        self.conn.commit()
        self.cur.close()
        self.conn.close()

        
inst = db()
inst.initialize_query_types()
inst.exec_query('distance',['\'Delhi\'','\'Kanpur\'','administrative2'])
inst.exec_query('distance',['\'Greater Bombay\'','\'Varanasi\'','administrative2'])
inst.close_conn()
