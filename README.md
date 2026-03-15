# Bank Wallet REST API
A simple Bank Wallet REST API built with Spring Boot where users can own a wallet and perform basic money operations. It demonstrates clean layering (controller/service/repository), entity mapping with JPA, validation, and error handling.

## How to run
Prerequisites:

* Java 25
* Maven 3.9+

## Steps:

From the project root folder:
```bash
mvn clean install
mvn spring-boot:run
```

The application will start on:
* http://localhost:8080

If you use H2 in‑memory, the H2 console is typically available at:
* http://localhost:8080/h2-console

To connect to the DB, use JDBC URL: _"jdbc:h2:mem:testdb"_ (or whatever you configured in the **_application.properties_** file)

----------------------------------------

## API overview

#### User Controller
- create a user (with a wallet)
- find a user by ID or email
- update user's name or email
- delete a user

#### Wallet Controller
- find a wallet by walletID or userID
- deposit in a wallet
- withdraw from a wallet
- transfer between 2 wallets

----------------------------------------

## Technologies used

- Java 25
- Maven 3.9 – build and dependency management
- Spring Boot – application framework and auto‑configuration
- Spring Web – RESTful HTTP endpoints
- Spring Data JPA / Hibernate – ORM and repository abstraction
- H2 Database (in‑memory) – relational database for development/testing
- Jakarta Validation / Hibernate Validator – request and DTO validation
- Lombok – boilerplate reduction (@Data, @Getter, @Setter, @AllArgsConstructor, @NoArgsConstructor, @Slf4j)
