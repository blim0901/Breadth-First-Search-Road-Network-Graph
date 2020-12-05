----------------------------------------------------------------------------
	Please follow the advices and steps below to test the program :)
----------------------------------------------------------------------------

(1)
	There are 4 .java classes provided in the folder "2001_proj2_codes": GraphFileReading.java, GraphTop2.java, GraphTopK.java, MSGraphK.java. Among these 4,  GraphFileReading.java, GraphTop2.java, GraphTopK.java are implemented using BFS algorithm, and MSGraphK.java uses Multisource BFS algorithm.
	
	<Hospitals.txt>, <roadNetwork_testing.txt> are default files for our programs. Another large input file <roadNet-CA.txt> is also provided for testing.

	All other class files are generated in advance for your convenience.

	The sub-folder "output_files_example" include output examples (solution paths) of each algorithm. RoadMapBFS.txt for GraphFileReading.java, RoadMap_Top2.txt for GraphTop2.java, RoadMap_TopK.txt for GraphTopK.java, and RoadMapMS_TopK.txt for MSGraphK.java.



(2) 
	Please ensure the folder "2001_proj2_codes" is downloaded to the correct directory.



(3) 
	Open the terminal. Direct to the correct directory where all the .java and .class and .txt files are stored.
	Enter the command: java <name of the program you want to test>
		>>> e.g. java GraphTop2 // for testing GraphTop2.java



(4) 
	When prompted the following text while executing the program: 
	
	---------------------------------------------------------------------------

	''Enter the file name that contains the hospital information.
	  Default hospital information file name is <Hospitals.txt>

	  Input file name (include .txt extension): ''
	
	----------------------------------------------------------------------------




	 Kindly enter the file name for hospital information. Please note that the content in the hospital file should follow the exact format as our sample file <Hospitals.txt>.
	 If nothing is being entered, the program will use the default hospital info file <Hospital.txt>, and the following messages will be printed: 
	 
	
	---------------------------------------------------------------

	 ''Default hospital information file name is Hospitals.txt
	   Hospital information .txt file used: Hospitals.txt''

	---------------------------------------------------------------




	If invalid input received, the following messages will be printed:
	
	-----------------------------------------------------------------------------

	''Invalid File. Default file Hospitals.txt is used as hospital sources.''

	-----------------------------------------------------------------------------



(5) 
	After the hospital information file is received, the program will output the number of hospitals.
		e.g. Number of Hospitals =  10



(6)
	When prompted the following text: 
	
	------------------------------------------------------------------------------------

	''Enter the file name that contains the road network information.
	  Default road network information file name is <roadNetwork_testing.txt>

	  Input file name (include .txt extension):''
	
	------------------------------------------------------------------------------------



	 Kindly enter the file name for road network information. Please note that the content in the road file should follow the exact format as our sample file <roadNetwork_testing.txt>.
	 If nothing is being entered, the program will use the default hospital info file <roadNetwork_testing.txt>, and the following messages will be printed: 
	 
	
	------------------------------------------------------------------------------------

	 ''Default road network information file name is roadNetwork_testing.txt
		Road network information .txt file used: roadNetwork_testing.txt''
	
	------------------------------------------------------------------------------------




	If invalid input received, the following messages will be printed:
	
	------------------------------------------------------------------------------------

	''Invalid File. Default file roadNetwork_testing is used as hospital sources.''
	
	------------------------------------------------------------------------------------



(7) 
	After the road information file is received, the program will output the number of nodes and edges.
		e.g. Number of Nodes =  10003
			 Number of Edges =  19900



(8) 
	For the program GraphTopK.java and MSGraphK.java, you will also be asked to enter an integer as below: 
	
	----------------------------------------------------------------------

	''Enter the number of nearest hospitals k you want to search: ''
	
	----------------------------------------------------------------------



(9) 
	When prompted the following text: 
	
	---------------------------------------------------------------
	
	''Create a text file to output your results.
	  Default output file name is <RoadMap_TopK.txt>

	  Input file name (include .txt extension):''
	
	---------------------------------------------------------------

	 Kindly enter the file to save the solution of paths and distances. Please enter a file name with the .txt extension. If the file name is correctly entered, a message will be printed:
	
	---------------------------------------------------------------
	
	 ''Output is written to <user defined file name>''
	
	---------------------------------------------------------------

	 If nothing entered, the program will output to the default file, and the following messages will be printed: 
	 
	
	---------------------------------------------------------------

	 ''Default output file name is <default file name>
		Output is written to <default file name>''
	
	---------------------------------------------------------------

	[RoadMapBFS.txt is the default output file for GraphFileReading.java, RoadMap_Top2.txt is the default output file for GraphTop2.java, RoadMap_TopK.txt is the default output file for GraphTopK.java, and RoadMapMS_TopK.txt is the default output file for MSGraphK.java.]



(10)
	The program terminates and outputs are reflected in the text files. :)



