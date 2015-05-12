## Database Extractor
While writing Junit test with DbUnit, you might want to export data from database to xml file and vice-versa. 
This tool will help you to get that job done job. It provides you with the following functionalities:

 - Convert database to xml file (full and partial)
 - Insert xml file back into database.
 The xml format is based on dbunit.
 
## To run the tool
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
	
## To change the default information
Information about the url, driver, username, password... are initially read from the Application.properties file in the src/main/resources folder.

These info will be displayed on the UI when the application gets started. To change them, please update this file.
