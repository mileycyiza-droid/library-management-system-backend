# Library Management System Backend

## Student Information

- **Student Name:** Himba Aimee Mireille
- **Student ID:** 28220
- **Project Name:** Library Management System
- **Course:** Web Technology and Internet
- **Institution:** Adventist University of Central Africa (AUCA)

## Project Description

The Library Management System Backend is a REST API developed using Spring Boot, Spring Data JPA, and PostgreSQL. It manages library members, librarians, sections, desks, loans, and fines.

## Technologies Used

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Maven
- Postman

## Prerequisites

Before running the application, ensure that you have installed:

- Java 17 or a compatible version
- Maven
- PostgreSQL
- An IDE such as Visual Studio Code or IntelliJ IDEA
- Postman for API testing

## Database Configuration

Create a PostgreSQL database named:

`library_management_system`

Configure the database connection in `src/main/resources/application.properties` using your local PostgreSQL credentials.

Keep your database password private. Do not commit credentials to GitHub.

## How to Run the Application

1. Clone the repository:

   ```bash
   git clone https://github.com/mileycyiza-droid/library-management-system-backend.git
   ```

2. Navigate to the project directory:

   ```bash
   cd library-management-system
   ```

3. Ensure PostgreSQL is running and the database is configured.

4. Start the application:

   ```bash
   mvn spring-boot:run
   ```

5. The API runs at:

   `http://localhost:8080`

## API Testing

Use Postman to test the REST API endpoints for:

- Sections
- Desks
- Librarians
- Members
- Loans
- Fines
- Loan reports and bulk updates

## Version Control

The project is maintained using Git and GitHub, with separate commits for Parts A, B, C, D, E, and F of the assignment.