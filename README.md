# IPL DashBoard
The Dashborad has the mach info of all the IpL matches played between 2008 to 2020.

# Technology Used
### Backend
* Spring Boot - Web, Batch, DevTools, JPA, Actuator
* Embedded DB - HSQL
### Frontend
* ReactJS
### Data Source
* IPL Data Set (Kaggle)

# Files
### Spring Batch
Spring Batch is used to read the data from a data source (CSV File) and update it to a embedded database (HSQL DB).
* __MatchInput.java__ _(src\main\java\com\learning\ipldashboard\data)_ - Input (format readable by code) after reading the dataset.
* __Match.java__ (src\main\java\com\learning\ipldashboard\model) - Output (format to be inserted in DB) which will be uploaded in DB, also the Entity (Table) is created.
* __BatchConfig.java__ _(src\main\java\com\learning\ipldashboard\data)_ - Creates the job and step required to complete the job. Step will have three stages - Reader, Processor and Writer.
* __MatchDataProcessor.java__ _(src\main\java\com\learning\ipldashboard\data)_ - Processes the data which will added in DB (Processor - Stage of Step).
* __JobCompletionNotificationListener.java__ _(src\main\java\com\learning\ipldashboard\data)_ - Listner which will be executed before start and after job completion.


### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)

