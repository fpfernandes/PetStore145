# API REST Test Project

This project contains automated API tests for the Swagger Petstore API using Java, Maven, JUnit 5, and Rest-Assured.

## Overview

The test suite validates basic CRUD-style behavior against the public Petstore endpoints, including:

- Creating a pet
- Creating a user
- Creating an order
- Updating a pet status
- Retrieving a pet and an order

## Technologies Used

- Java 8
- Maven
- JUnit 5
- Rest-Assured
- Hamcrest
- Gson

## Project Structure

```text
.
├── data/
│   ├── order.json
│   ├── pet.json
│   └── user.json
├── src/
│   └── test/
│       └── java/
│           └── petstore/
│               ├── Order.java
│               ├── Pet.java
│               ├── PetTest.java
│               └── User.java
└── pom.xml
```

## Prerequisites

Make sure you have the following installed:

- JDK 8 or newer
- Maven 3.6 or newer

## Running the Tests

From the project root, run:

```bash
mvn test
```

You can also run the main test class directly:

```bash
mvn -Dtest=petstore.PetTest test
```

## Notes

- The tests call the public Swagger Petstore API, so an internet connection is required.
- The test methods are organized with execution order to simulate a realistic API workflow.
- Test payloads are loaded from the JSON files in the data folder.
