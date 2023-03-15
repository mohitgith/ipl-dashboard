# IPL DashBoard
The Dashborad has the mach info of all the IpL matches played between 2008 to 2020.

# Technology Used
### Backend
* Spring Boot - Web, Batch, DevTools, JPA, Actuator
* Embedded DB - HSQL
* External DB - MySQL
### Frontend
* ReactJS
### Data Source
* IPL Data Set (Kaggle)

# Files and Configurations
### Spring Batch
Spring Batch is used to read the data from a data source (CSV File) and update it to a embedded database (HSQL DB) or any other external database (MySQL).
* __MatchInput.java__ _(src\main\java\com\learning\ipldashboard\data)_ - Input (format readable by code) after reading the dataset.
* __Match.java__ (src\main\java\com\learning\ipldashboard\model) - Output (format to be inserted in DB) which will be uploaded in DB, also the Entity (Table) is created.
* __Team.java__ (src\main\java\com\learning\ipldashboard\model) - Output (format to be inserted in DB) which will be uploaded in DB, also the Entity (Table) is created.
* __BatchConfig.java__ _(src\main\java\com\learning\ipldashboard\data)_ - Creates the job and step required to complete the job. Step will have three stages - Reader, Processor and Writer.
* __MatchDataProcessor.java__ _(src\main\java\com\learning\ipldashboard\data)_ - Processes the data which will added in DB (Processor - Stage of Step).
* __JobCompletionNotificationListener.java__ _(src\main\java\com\learning\ipldashboard\data)_ - Listner which will be executed before start and after job completion.


### Spring Data JPA
Spring Data JPA is used to create the entity (table) and query the data from the database. 

* __TeamRepository.java__ _(src\main\java\com\learning\ipldashboard\repository)_ - Repository to perform CRUD operation on Team table.
* __MatchRepository.java__ _(src\main\java\com\learning\ipldashboard\repository)_ - Repository to perform CRUD operation on Match table.
* __TeamController.java__ _(src\main\java\com\learning\ipldashboard\controller)_ - Rest Controller where all the api endpoints will be created.

For the embedded database there is no such configuation required but, if using the external database like MySQL below configuration is must.

###### application.properties _(src\main\resources)_
```
spring.datasource.url=jdbc:mysql://localhost:3306/ipl_dashboard_db
spring.datasource.username=root
spring.datasource.password=root123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=create 

spring.batch.jdbc.initialize-schema=ALWAYS
```
The spring.jpa.hibernate.ddl-auto takes one of __none__, __validate__, __update__, __create__, and __create-drop__.

| Option | Effect |
| ------ | ------ |
| none | No database Schema initialization |
| create |	Drops and creates the schema at the application startup. With this option, all your data will be gone on each startup. |
| create-drop |	Creates schema at the startup and destroys the schema on context closure. Useful for unit tests. |
| validate |	Only checks if the Schema matches the Entities. If the schema doesn’t match, then the application startup will fail. Makes no changes to the database. |
| update | Updates the schema only if necessary. For example, If a new field was added in an entity, then it will simply alter the table for a new column without destroying the data. |

Setting `spring.batch.initialize-schema` to `ALWAYS` value by default create the tables if you are using an embedded database

### React (Frontend)
React is used to create the frontend UI for the application.
#### Pages
* __TeamPage.js__ _(src\frontend\src\pages)_ - Fetches the data from the backend using REST API, display it and send the required data to the components.
#### Components
* __MatchDetailCard.js__ _(src\frontend\src\components)_
* __MatchSmallCard.js__ _(src\frontend\src\components)_
### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)

