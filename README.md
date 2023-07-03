# README #

This README contains all the steps are necessary to get this application up and running,
and also general information about some built tools and plugins that are used.

### What is this repository for? ###

* E-Commerce is a multi tenant application able to provide e-commerce services to multiple customers simultaneously.
* Version 1.0

### Features
* Enable Multi Tenancy 
* User management
* Product management
  * Product inventory
  * Status: **Active**, **Draft**, **Archived**
  * Product collections
* Clients
  * Identity information
  * Address 1 & 2
* Orders
  * Client address
  * Order status: **Unfulfilled**, **Sent**, **Delivered**.
* Discounts
  * Discount percentage
  * Discount period
  * Discount target: **Single product**, **Collection**
  * Discount status: **Active**, **Scheduled**, **Expired**

### Built With ###
[![Java][java.com]][java-url]
[![Spring][spring.io]][spring-url]
[![Postgres][postgresql.org]][postgresql-url]
<!--[![H2Database][h2database.com]][h2database-url]-->

### Plugins and Build Tools
[![H2Database][maven.apache.org]][maven-url]
[![Flyway][flywaydb.org]][flyway-url]
[![Hibernate][hibernate.org]][hibernate-url]
[![Checkstyle][checkstyle.sourceforge.io]][checkstyle-url]


## Deployment ##
#### Run app including linting and tests ####

```sh
    mvn clean install
```

#### Run app including linting but skipping tests ####
```sh
    mvn clean install -DskipTests
```

#### Run tests ####
```sh
    mvn test
```
#### Run linting check ####
```sh
    mvn checkstyle:check
```

#### What has been configured in this application ?
* Exception Handling
* Validation
* Authentication and Authorization
* Response object and Page object
* Utilities
  * Date conversion utility
  * Base64Processor
* Swagger Configuration
* Linting
* Test environment
* CI/CD

### Who do I talk to? ###
* Product Owner: [Leotrim Vojvoda](mailto:leotrimvojvoda@universitetiaab.com)

[spring.io]: https://img.shields.io/badge/Spring_BOOT-6CB42C?style=for-the-badge&logo=spring&logoColor=FFFFFF
[spring-url]: https://spring.io/
[java.com]: https://img.shields.io/badge/Java-ed2024?style=for-the-badge&logo=oracle&logoColor=white
[java-url]: https://www.java.com/en/
[postgresql.org]: https://img.shields.io/badge/Postgresql-31648c?style=for-the-badge&logo=postgresql&logoColor=white
[postgresql-url]: https://www.postgresql.org/
[h2database.com]: https://img.shields.io/badge/H2_Database-1020fe?style=for-the-badge
[h2database-url]: https://www.h2database.com/html/main.html
[maven.apache.org]: https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white
[maven-url]: https://maven.apache.org/
[flywaydb.org]: https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white
[flyway-url]: https://flywaydb.org/
[hibernate.org]: https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=black
[hibernate-url]: https://hibernate.org/
[checkstyle.sourceforge.io]: https://img.shields.io/badge/Checkstyle-fdc205?style=for-the-badge
[checkstyle-url]: https://checkstyle.sourceforge.io/