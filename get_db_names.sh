#!/bin/sh

#title           :get_db_names.sh
#description     :This script will pull all the names from the database for NER
#author          :chinmaya gautam
#date            :04152016
#version         :1.0.1    
#usage           :sh get_db_names.sh
#notes           :will only work with the database setup with GIS data for NLP project by anaphora resolution


/Library/PostgreSQL/9.5/bin/psql -d postgres -U postgres -p 5432 <<EOF
\pset tuples_only
SELECT name_engli FROM administrative;
SELECT name_local FROM administrative;
SELECT DISTINCT name_0 FROM administrative3;
SELECT DISTINCT name_1 FROM administrative3;
SELECT DISTINCT name_2 FROM administrative3;
SELECT DISTINCT name_3 FROM administrative3;
SELECT DISTINCT nam FROM water_lines;
SELECT DISTINCT name FROM water_areas;
EOF

