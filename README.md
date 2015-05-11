## Database Extractor
While writing Junit test using DbUnit, you might want to export data from data base to a xml file and vice versa. 
This small tool will help you to to the job with a very simple interface. It provides you with the following functionalities:

 - Convert database to xml file (full and partial)
 - Insert xml file back into database.
 The xml format is based on dbunit.
 
## To run the tool:
- Change your location to your project 
	```
		cd dbhelper
	```
- Build the project 
	```
		mvn clean install
	```
- Run the jar file
	```
		cd target
		java -jar dbhelper.jar
	```
