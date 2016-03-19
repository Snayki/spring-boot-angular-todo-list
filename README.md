# Spring boot | Custom Spring Security | AngularJs | ToDo List example #

A sample "ToDo List" project to demonstrate how a web app can be built using a Spring Boot / AngularJs stack. 
The frontend is based on AngularJs and the backend is composed of JSON REST web services based on Spring Boot / JPA, secured with Spring Security (with custom providers and filters).

### Installation dependencies ###

The following dependencies are necessary: 

 - Java 8
 - bower
 - maven 3
 - mysql
 
### DB preparation ###

Application DB is based on MySql and should be prepared before start project. 
Previously there  should be created "todo-example" schema and user with "todoUser/todoPassword" credentials. 

### Installing frontend dependencies ###

After cloning the repository, the following command installs the Javascript dependencies:

    bower install

### Building and starting the server ###

To build the backend and start the server, run the following command on the root folder of the repository:

    mvn clean spring-boot:run

The application is accessible at the following URL:

    http://localhost:8080/
    
To login to the system user should use the following credentials:

    username: user / password: password       

