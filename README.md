# Scope
Merchant Offers is a project for creating simple offers by a Merchant.

This project makes use multiple types of technology within:
- [Spring Framework](https://spring.io/) (Spring data, Spring boot)
- [Hibernate](http://hibernate.org/) (JPA)
- [DBUnit](http://dbunit.sourceforge.net/)
- [Gson](https://github.com/google/gson)
- [HSQLDB](http://hsqldb.org/)
- [Project Lombok](https://projectlombok.org/)
- [Mapstruct](http://mapstruct.org/)
- [Junit](http://junit.org/junit4/)
- [Mockito](http://site.mockito.org/)

# Build and Test the project
To test and build this application just run the following command:

``mvn clean package``

# Build and Run Integration Tests
To test and build this application just run the following command:

``mvn clean verify``

# Run the application
To run this application just run the following command (this will use port 8080 by default):

``java -jar api/api-boot/target/api-boot-1.0.0-SNAPSHOT.jar``
