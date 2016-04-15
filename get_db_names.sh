#!/bin/sh

#title           :get_db_names.sh
#description     :This script will pull all the names from the database for NER
#author          :chinmaya gautam
#date            :04152016
#version         :1.0.2    
#usage           :sh get_db_names.sh
#notes           :will only work with the database setup with GIS data for NLP project by anaphora resolution


#to turn off tuple headers: \pset tuples_only
/Library/PostgreSQL/9.5/bin/psql -d postgres -U postgres -p 5432 <<EOF
SELECT name_engli AS country_name FROM administrative;
SELECT name_local AS country_local_name FROM administrative;
SELECT DISTINCT name_1 AS states FROM administrative3;
SELECT DISTINCT name_2 AS cities FROM administrative3;
SELECT DISTINCT name_3 AS cities FROM administrative3;
SELECT DISTINCT nam AS rivers FROM water_lines;
SELECT DISTINCT name AS lakes FROM water_areas;
EOF

