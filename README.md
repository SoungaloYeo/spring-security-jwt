# Spring Boot + JWT

![](https://img.shields.io/badge/build-success-brightgreen.svg)

# Stack

![](https://img.shields.io/badge/java_8-✓-blue.svg)
![](https://img.shields.io/badge/spring_boot-✓-blue.svg)
![](https://img.shields.io/badge/mysql-✓-blue.svg)
![](https://img.shields.io/badge/jwt-✓-blue.svg)
![](https://img.shields.io/badge/swagger_2-✓-blue.svg)

***

# File structure

```
spring-boot-jwt/
 │
 ├── src/main/java/
 │   └── securitypoc
 │       ├── configuration
 │       │   └── SwaggerConfig.java
 │       │   ├── Role.java
 │       ├── resource
 │       │   └── UserResource.java
 │       │
 │       ├── exception
 │       │   ├── CustomException.java
 │       │   └── GlobalExceptionController.java
 │       │
 │       ├── domain
 │       │   └── User.java
 │       │
 │       ├── repository
 │       │   └── UserRepository.java
 │       │
 │       ├── security
 │       │   ├── JwtTokenFilter.java
 │       │   ├── JwtTokenFilterConfigurer.java
 │       │   ├── JwtTokenProvider.java
 │       │   ├── MyUserDetails.java
 │       │   └── WebSecurityConfig.java
 │       │
 │       ├── service
 ├       │   ├── dto
 │       │   │  ├── UserDataDTO.java
 │       │   │  └── UserResponseDTO.java
 │       │   ├── UserService.java         
 │       │   └── impl
 │       │       └── UserServiceImpl.java
 │       │
         └── JwtAuthServiceApp.java
 │
 ├── src/main/resources/
 │   └── application.yml
 │
 ├── .gitignore
 ├── LICENSE
 ├── mvnw/mvnw.cmd
 ├── README.md
 └── pom.xml
```


```yml
spring:
  datasource:
    url: jdbc:h2:mem:test_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    # url: jdbc:mysql://localhost:3306/user_db
    username: root
    password: root
  tomcat:
    max-wait: 20000
    max-active: 50
    max-idle: 20
    min-idle: 15
  jpa:
    hibernate:
      ddl-auto: create-drop
    properties:
      hibernate:
        dialect: org.hibernate.dialect.H2Dialect
        # dialect: org.hibernate.dialect.MySQL8Dialect
        format_sql: true
        id:
          new_generator_mappings: false
```
**To use this project**
```
$ git clone https://github.com/<your-user>/spring-boot-jwt
```

3. Navigate into the folder  

```
$ cd spring-boot-jwt
```

4. Install dependencies

```
$ mvn install
```

5. Run the project

```
$ mvn spring-boot:run
```

6. Navigate to `http://localhost:8080/swagger-ui.html` in your browser to check everything is working correctly. You can change the default port in the `application.yml` file

```yml
server:
  port: 8080
```

7. Make a GET request to `/api/users/info` to check you're not authenticated. You should receive a response with a `403` with an `Access Denied` message since you haven't set your valid JWT token yet

```
$ curl -X GET http://localhost:8080/api/users/info
```

8. Make a POST request to `/api/users/signin` with the default admin user we programatically created to get a valid JWT token

```
$ curl -X POST 'http://localhost:8080/api/users/signin?username=admin&password=admin'
```

9. Add the JWT token as a Header parameter and make the initial GET request to `/api/users/info` again

```
$ curl -X GET http://localhost:8080/api/users/info -H 'Authorization: Bearer <JWT_TOKEN>'
```

10. And that's it, congrats! You should get a similar response to this one, meaning that you're now authenticated

```javascript
{
  "id": 1,
  "username": "admin",
  "email": "admin@email.com",
  "roles": [
    "ROLE_ADMIN", 
    "ROLE_USER"
  ]
}
```

# INITIAL REPO FROM:
- https://github.com/murraco/spring-boot-jwt
