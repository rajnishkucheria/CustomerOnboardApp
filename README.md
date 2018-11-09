# CustomerOnboardApp 
 This test app is created for automation of Service layer tests for customer onboard application<br>


## Getting started
* POM.xml contains all the dependencies required for the project
* testNG.xml contains details about test suite and test classes
* testNG.xml will be calling "TemplateUploadTest.java" for running test suite
* TemplateUploadTest.java contains all the tests
* CallRestServices.java contains call to all APIs
* /objects directory contains all the test data, Mock API paths and constants
* /operations directory contains classes to call APIs, reading xls data, etc.
* /tests contains the test classes
* Mock APIs are created with the help of Postman and can be checked in "MockAPIconfig.json" file
