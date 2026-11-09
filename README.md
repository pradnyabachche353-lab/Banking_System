# Banking and Transaction Management System

A backend application developed using **Java, Spring Boot, Spring Security, JWT Authentication, Spring Data JPA, and MySQL** to manage banking operations securely.

## Overview

The Banking and Transaction Management System allows users to register, log in, create bank accounts, and perform financial transactions through REST APIs.

The application uses JWT-based authentication and account ownership validation to ensure that users can access only their own accounts.

## Technologies Used

- **Language:** Java
- **Framework:** Spring Boot
- **Database:** MySQL
- **ORM:** Spring Data JPA / Hibernate
- **Security:** Spring Security, JWT
- **Password Encryption:** BCrypt
- **API Testing:** Postman
- **Build Tool:** Maven
- **IDE:** IntelliJ IDEA

## Features

- User registration and login
- JWT-based authentication
- Secure password hashing using BCrypt
- Bank account creation
- Retrieve all accounts belonging to the authenticated user
- Retrieve account details by ID
- Deposit money
- Withdraw money
- Transfer money between accounts
- Transaction history
- Account ownership validation
- Input validation and global exception handling
- Automatic transaction record creation

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/auth/register` | Register a new user |
| POST | `/auth/login` | User login |
| POST | `/accounts` | Create a bank account |
| GET | `/accounts` | Get user's accounts |
| GET | `/accounts/{id}` | Get account by ID |
| PUT | `/deposit/{id}` | Deposit money |
| PUT | `/withdraw/{id}` | Withdraw money |
| PUT | `/transfer/{s_id}/{r_id}` | Transfer money |
| GET | `/{id}/transactions` | Get transaction history |

**Authentication:** Protected endpoints require a valid JWT in the Authorization header.

`Authorization: Bearer <your-token>`

## Database

The application uses MySQL to store:

- User information
- Bank account details
- Transaction records

## How to Run the Project

1. Clone the repository.

   ```bash
   git clone https:/pradnyabachche353-lab/github.com//Banking-System.git
   ```

2. Open the project in IntelliJ IDEA.

3. Create a MySQL database named `bank_db`.

4. Configure your database credentials and JWT secret using environment variables or your local configuration.

5. Run the application using:

   ```bash
   ./mvnw spring-boot:run
   ```

   On Windows:

   ```cmd
   mvnw.cmd spring-boot:run
   ```

6. Test the REST APIs using Postman.

## Future Improvements

- Frontend integration
- Role-based authorization
- Improved transaction concurrency handling
- Unit and integration testing
- API documentation using Swagger

## Author

Developed as a Java Spring Boot backend project to explore REST API development, database management, authentication, and secure financial transactions.
