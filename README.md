# Library Management System

A Spring Boot based Library Management System built using Java, Spring Boot, Spring Security, JWT, PostgreSQL, Hibernate/JPA and Maven.

## Features

- User Signup
- User Login
- JWT Authentication
- Access Token
- Refresh Token
- Refresh Token using HTTP-only Cookie
- Session Management
- Spring Security
- PostgreSQL Database
- Hibernate/JPA
- Spring Boot Actuator
- Application Logging

## Technologies Used

- Java
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Spring Boot Actuator
- Lombok

```text
LibraryProject/
├── src/
├── .mvn/
├── mvnw
├── mvnw.cmd
├── pom.xml
├── .gitignore
└── .gitattributes

## Configuration

Application Name: LibraryProject

Server Port: 9094

Database: PostgreSQL

Database Name: LibraryManagementSystem

Hibernate is configured with automatic database schema updates using JPA/Hibernate.

## JWT Authentication

The application uses JWT-based authentication with Access Token and Refresh Token.

Authentication Flow:

Signup
   ↓
Login
   ↓
Authentication
   ↓
Access Token + Refresh Token
   ↓
Refresh Token → HTTP-only Cookie
   ↓
Protected APIs → Access Token
   ↓
Access Token Expired
   ↓
Refresh API
   ↓
New Access Token

## API Endpoints

### Signup

POST /auth/api/signup

### Login

POST /auth/api/login

### Refresh Token

POST /auth/api/refresh

Protected APIs require the Access Token in the Authorization header:

Authorization: Bearer <access-token>

The Refresh Token is stored and handled using an HTTP-only Cookie.

## Spring Boot Actuator

Spring Boot Actuator is enabled for application monitoring and information.

Available endpoints include:

/actuator
/actuator/health
/actuator/info

Actuator provides information related to:

- Application
- Environment
- Build
- Git
- Java
- Operating System

## Logging

Application logging is configured using Spring Boot logging.

TRACE level logging is enabled for the LibraryProject package.

Console logs include:

- Date
- Time
- Log Level
- Message

## How to Run

### 1. Clone the Repository

git clone <YOUR_REPOSITORY_URL>

### 2. Configure PostgreSQL

Create a PostgreSQL database named:

LibraryManagementSystem

Update the PostgreSQL username and password in application.properties.

### 3. Configure JWT Secret

Set the following environment variable:

SECRET_KEY

The application reads the JWT secret using:

jwt.secret=${SECRET_KEY}

### 4. Run the Application

For Windows:

mvnw.cmd spring-boot:run

For Linux/macOS:

./mvnw spring-boot:run

The application will run on:

http://localhost:9094

## API Testing

The APIs can be tested using Postman.

Authentication APIs:

POST /auth/api/signup
POST /auth/api/login
POST /auth/api/refresh

Protected APIs require a valid JWT Access Token.

## Security

Spring Security is used to protect authenticated APIs.

The Refresh Token is handled separately from the Access Token.

The Access Token is sent through the Authorization header, while the Refresh Token is handled through an HTTP-only Cookie.

## Author

Shishir Srivastava

Library Management System developed using Spring Boot, PostgreSQL, Spring Security and JWT Authentication.
