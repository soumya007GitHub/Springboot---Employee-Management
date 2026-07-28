# Employee Management System - Spring Boot REST API

## Overview

The **Employee Management System** is a RESTful backend application built using **Spring Boot**. It provides APIs to perform complete CRUD (Create, Read, Update, Delete) operations on employee records while following a clean layered architecture and REST API best practices.

The project has been developed with scalability and maintainability in mind by separating concerns into different layers such as Controller, Service, Repository, DTO, Mapper, and Entity.

---

## Tech Stack

* Java 17+
* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* Jakarta Bean Validation
* Swagger / OpenAPI
* SLF4J Logging

---

## Project Architecture

```text
src
└── main
    ├── java
    │   └── com.soumya.empl
    │       ├── controller
    │       ├── dto
    │       ├── entity
    │       ├── mapper
    │       ├── repository
    │       ├── response
    │       ├── service
    │       └── EmplApplication
    │
    └── resources
        ├── application.properties
        └── static
```

---

## Features

* Create Employee
* Get All Employees
* Get Employee by ID
* Update Employee (PUT)
* Partial Update Employee (PATCH)
* Delete Employee
* Pagination Support
* DTO-Based Request & Response
* Entity to DTO Mapping
* Bean Validation
* Standardized API Response
* Application Logging
* Interactive Swagger Documentation

---

## REST API Endpoints

| Method | Endpoint         | Description                             |
| ------ | ---------------- | --------------------------------------- |
| GET    | `/api/empl`      | Get all employees (supports pagination) |
| GET    | `/api/empl/{id}` | Get employee by ID                      |
| POST   | `/api/empl`      | Create a new employee                   |
| PUT    | `/api/empl/{id}` | Replace an existing employee            |
| PATCH  | `/api/empl/{id}` | Partially update employee details       |
| DELETE | `/api/empl/{id}` | Delete an employee                      |

---

## Request Validation

The application uses **Jakarta Bean Validation** to validate incoming requests.

Current validations include:

* Employee name is mandatory
* Valid email address
* Valid Indian phone number
* Date of Birth cannot be null

---

## Pagination

Employee listing supports pagination.

Example:

```http
GET /api/empl?page=0&size=10
```

---

## Standard API Response

Every API returns a consistent response format.

Example:

```json
{
  "success": true,
  "message": "Employee fetched successfully",
  "data": {
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "9876543210",
    "dob": "2000-01-15"
  }
}
```

---

## Logging

The project uses **SLF4J** for application logging.

Logs are generated for operations such as:

* Fetching employees
* Creating employees
* Updating employees
* Deleting employees
* Warning logs for invalid requests

---

## API Documentation

Swagger/OpenAPI has been integrated for interactive API documentation.

After running the application, open:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## Database

Database: **MySQL**

The application uses Spring Data JPA with Hibernate ORM for database interaction.

---

## Current Project Highlights

* Layered Architecture
* Constructor Injection
* DTO Pattern
* Mapper Layer
* Spring Data JPA
* RESTful API Design
* Bean Validation
* Pagination
* Generic API Response
* SLF4J Logging
* Swagger Documentation

---

## Planned Enhancements

The following features are planned for future versions:

* Global Exception Handling
* Custom Exceptions
* Unit Testing (JUnit & Mockito)
* Spring Security with JWT Authentication
* Docker Support
* Cloud Deployment

---

## How to Run

### 1. Clone the Repository

```bash
git clone <repository-url>
```

### 2. Navigate to the Project

```bash
cd employee-management-system
```

### 3. Configure Database

Update your `application.properties` with your MySQL credentials.

### 4. Build the Project

```bash
mvn clean install
```

### 5. Run the Application

```bash
mvn spring-boot:run
```

or run the main Spring Boot application from your IDE.

---

## Author

**Soumya Ranjan Tripathy**
