# Applications-API

### Install the project

Clone this repository to your local machine using:

```shell
git clone https://github.com/lizozavr/Applications-API.git

Open file and run application
```

### Endpoints
```shell
 POST: localhost:8080/app - Create a new application (you should provide request body)
 
 GET: localhost:8080/all - Retrieve all applications
 
 GET: localhost:8080/{id} - Get application by id
 
 DELETE: localhost:8080/{id} - Delete application identified by id
 
 GET: localhost:8080/compare?ids={1,2,3...} - Compare 2 or more apps identified by ids and return which app has the greatest version
 
 GET: localhost:8080/count?rates={1,2,3...} - Return count of applications with specified content rates
 ```
 
 ### DB H2
 ```shell
 To view the database while the application is running, you can use the h2-console:
 localhost:8080/h2-console
 
 Driver Class: org.h2.Driver
 JDBC URL: jdbc:h2:mem:appsdb
 User Name: sa
 Password: password
 ```
