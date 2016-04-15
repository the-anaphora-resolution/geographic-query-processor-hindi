Readme File for the Hindi Wordnet API JHWNL version 1.2

PURPOSE:
-----------------------------------------
The Hindi Wordnet 1.2, copyright IIT Bombay, has been developed for the purpose of
facilitating Natural Language Processing applications. This download provides the API
to use the Wordnet using JAVA.

CONTENTS:
-----------------------------------------

1. A jar file library for using Hindi Wordnet Stemmer Library: StemIT.jar.
2. A jar file library for Hindi WN API: JHWNL.jar.
2. Java source code for Hindi WN API.
2. Configuration files in the directory 'config'.
3. Example.java containing example usage of the API & inputwords.txt which contains sample input for the example. 
4. Docs directory contains Documentation for the API.
5. This Readme file.

Important Note: You will need the Hindi Wordnet text database and Morphological Processor files to use the API. These are available with the Hindi Wordnet release and not included here to reduce size of the API download. To use the APT, copy the directories "database" and "MorpHIN" from Hindi Wordnet directory to the location where you have placed "JHWNL.jar".

Please follow this link to enable Unicode on Windows:
http://www.microsoft.com/globaldev/handson/user/xpintlsupp.mspx

SOFTWARE REQUIREMENT:
-----------------------------------------

Windows 2000/XP or newer with Indian Language Pack
Linux with Indian Language Pack(Unicode support for Devanagari)
JRE 6 or newer

NOTE: This software needs Java Runtime Environment 6 or newer to function properly. It can be downloaded from the following website:
http://java.sun.com/javase/downloads/index.jsp

For doing actual development work, you might use JDK 6 available at the above webpage.



HOW TO USE:
-----------------------------------------
1. Decompress the JHWNL_1_2.zip in a directory of your choice
2. To compile the example usage file Examples.java, copy all the necessary files (mentioned above in the 'Important Note') from Hindi Wordnet directory to the same directory where you have placed "JHWNL.jar". Issue following command to compile Examples.java
javac -classpath JHWNL.jarjavac -classpath JHWNL.jar Examples.java

Then use following command to execute the example.
In Linux:
java -cp JHWNL.jar:.  Examples
IN MS Windows:
java -cp JHWNL.jar;.  Examples

Note: The terminal must support Unicode (UTF-8) to view the output correctly. You can use a JAVA IDE for programming by including JHWNL.jar in your classpath, but do not forget about placing the necessary Wordnet files as mentioned above.

DEVELOPED AT:
-----------------------------------------
CFILT Lab,
Dept of Computer Science & Engineering,
IIT Bombay,
Powai, Mumbai 400076.

Send bug-reports etc to:
pb[at]cse.iitb.ac.in,   
adityas[at]cse.iitb.ac.in
