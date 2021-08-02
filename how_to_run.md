# How to run movie service?
This file describes to how we can run service, you can run movie service by below command:  
This application is written base on Java 11.  
>`mvn spring-boot:run`

Also, you can change below properties and `application.properties` file:

>spring.datasource.url=jdbc:h2:mem:movieDB  
spring.datasource.driverClassName=org.h2.Driver  
spring.datasource.username=sa  
spring.datasource.password=password  
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect  
spring.jpa.hibernate.ddl-auto=create-drop  
jwt.secret=mySecretKey  
openMovieDatabase.url=http://www.omdbapi.com/  
openMovieDatabase.apiKey=**API_KEY**


You can obtain your own **API_KEY** by register on [OMDB API](http://www.omdbapi.com/)  
By default don't need **API_KEY** set, by default it's work.The point is that if you want to obtain more than **1000 per day** movie's information you should register with different account type which is called `patron` 

