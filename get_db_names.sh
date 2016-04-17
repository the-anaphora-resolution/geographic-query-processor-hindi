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
SELECT name_engli AS administrative FROM administrative;
SELECT DISTINCT name_1 AS administrative1 FROM administrative1;
SELECT DISTINCT name_3 AS administrative3 FROM administrative3;
SELECT DISTINCT nam AS water_lines FROM water_lines;
SELECT DISTINCT name AS water_areas FROM water_areas;
EOF

