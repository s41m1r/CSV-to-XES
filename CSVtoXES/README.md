What it does:
-------------
It takes a .csv file as input and gives a .xes file as output


Conditions
----------

The .csv file must:

1. Have values separated by comma or semicolon, and quoted text fields
2. Have the time stamp formatted as either long integer or   date values


How to run:
----------

1. edit the mapping.properties file to specify which rows of the .csv you want to map to <trace number, concept, lifecycle, timestamp>
2. run it like 
   java -jar CSVtoXes.jar -i /your/input/file.csv -o /your/output/file.xes
   
