# QA_JobReqAutomation

## How to setup and run Job Req Automation

### Pre-requisites :-  
  1. Setup JAVA
  2. Setup GRADLE
  3. Download the Project from GIT
  4. Pritunl should be configured to access hello server.
  5. To see the Remote Execution, use vnc viewer or Remmina.
  6. On local machine one time login is required through your hello email id.

### Tools and Technologies Used :-
  1. **Java Version** : Java 8
  2. **Framework** : GRADLE + TestNG :  
     - TestNG (v 6.1.1) is a testing framework. It structures, groups, and launches tests.  
     - GRADLE (v 4.2.1) is a software project management and comprehension tool. It manages all dependencies and different flows for building a project.
  3. **Google Sheets API** :  
  	  - In Google Sheets API(v4)  is an API Read, write, and format data in Sheets.
  4. **Google Drive API** :  
      - In Google Drive API(v4)  is an API allows you to create mobile, web, and desktop apps that read, write, and sync files in Google Drive.
  5. **Google GMail API** :  
      - In Google GMail API(v4)  is an API allows Read and send messages, manage drafts and attachments, search threads and messages, work with labels, setup push notifications, and manage Gmail settings.
  6. **Reporting :**  
  Extent Reporting : Extent Report (v 2.41.0) is a HTML reporting library for Selenium WebDriver for Java which is to a great degree simple to use and makes excellent execution reports. We can use this tool within our TestNG automation framework. 



###Test Steps
     * Launch the Browser
     * Write(Add Job) to the Spreadsheet
     * Close the Browser
     * Download the spreadsheet in CSV format
     * Launch SFTP server
     * Check for any file in downloads folder
     * Delete any local stored file
     * Download the spreadsheet as CSV file
     * Get the name of new file to be uploaded to the SFTP server
     * Get the file after download
     * Rename latest downloaded file with +1
     * Upload the csv file to SFTP server  // Email feature, is to email the same CSV file, to staging@staging.hello.com.
     * Disconnect from SFTP server
     * Delete the file from Old file folder
     * Check Upload progress
     * Validate the content using DB
     * Generate the test report.
     

### Execution Steps :-
**1.** Extract the downloaded project.

**2.** Open command Prompt or terminal.

**3.** Go to the project location.

**4.** Enter the below mentioned commands to compile and run the project :-  

**``./gradlew build -Psuites=staging -PtestType=Applicant -Pver=v1``**


**Clean Project** : ``./gradlew clean -Psuites=staging -PtestType=Applicant -Pver=v1``  
	
**Compile Project** : ``./gradlew build -Psuites=staging -PtestType=Applicant -Pver=v1``  
	

**On Successful compilation of the project it will download all the dependencies like (TestNG, Extent Reporting and Google API's)**
	
**Run the Project** : ``./gradlew test -Psuites=staging -PtestType=Applicant -Pver=v1``
	
     
 **-Pversion=v1** : Here v1 is the version of the api. In future if we have multiple version the we can switch to version v2 , v3 and so on.
 	
 **-Psuites=qa** : Here qa is the test suites environment. We can run our test on multiple test environments e.g. : uat, staging and production.
 	
 **-PtestType=Applicant** : Here Applicant is the name of bunch of the test of Applicant type. In case if we wants to run the test suite on single module then we can switch to modules like Interview, Requisition and so on.and if we wants to run all the modules at a time then we can use test type as "All". It will run all the test cases of all the modules.
 	    