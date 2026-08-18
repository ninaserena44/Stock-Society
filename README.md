# Stock Society

## Team Members

- Princess Krish Anne Bernardino
- Serena Nina Omondi

---

## Project Description

Stock Society is an inventory management web application designed for a clothing warehouse. The system allows warehouse staff to manage inventory, products, suppliers, and user accounts through a secure web interface.

For Deliverable 3, the project was enhanced with a separate Supplier microservice that communicates with the main application using Spring RestTemplate.

---

## Technologies Used

### Main Application

- Spring Boot
- Spring Security
- Spring Data JPA
- Thymeleaf
- H2 Database (Development Profile)
- MySQL (Production Profile)
- RestTemplate

### Supplier Microservice

- Spring Boot
- Spring Security
- Spring Data JPA
- H2 Database
- PostgreSQL (QA Profile)
- Docker Compose

---

## Project Structure

```text
Stock-Society/
│
├── src/                        # Main Stock Society application
├── supplier-service/           # Supplier Microservice
└── docker-compose.yml
```

---

## Features

### Main Application

- User registration and authentication
- BCrypt password encoding
- Role-based access control with ADMIN, STAFF, and CUSTOMER roles
- Custom login and logout functionality
- Admin dashboard
- Inventory management
- Item CRUD operations
- Search, filtering, sorting, and pagination
- Development environment using H2
- Production environment using MySQL
- Supplier data retrieved from the Supplier Microservice using RestTemplate

### Supplier Microservice

- Supplier CRUD REST API
- HTTP Basic Authentication
- Search suppliers by country and rating
- H2 Database (Development Profile)
- PostgreSQL (QA Profile)

---

## Architecture

The project consists of two Spring Boot applications that communicate using REST.

### Stock Society (Main Application)

- Handles user authentication and inventory management.
- Displays supplier information retrieved from the Supplier Service.
- Uses Spring RestTemplate to consume the Supplier Service REST API.

### Supplier Service (Microservice)

- Independent Spring Boot application.
- Provides Supplier CRUD REST APIs.
- Protected using HTTP Basic Authentication.
- Supports H2 (Development) and PostgreSQL (QA) profiles.

---

## Running the Project

### Prerequisites

Before running the project, make sure the following are installed:

- Java
- Maven or Maven Wrapper
- MySQL Server for the production profile
- PostgreSQL/Docker if running the Supplier Service QA profile

---

### 1. Start the Supplier Microservice

```bash
cd supplier-service
./mvnw spring-boot:run
```

The Supplier Service runs on:

```text
http://localhost:8081
```

Default credentials:

```text
Username: admin
Password: admin123
```

---

### 2. Run Stock Society with the Development Profile

The `dev` profile uses an in-memory H2 database.

From the main Stock Society project directory:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

The main application runs on:

```text
http://localhost:8080
```

The H2 console is available at:

```text
http://localhost:8080/h2-console
```

H2 connection settings:

```text
JDBC URL: jdbc:h2:mem:stocksociety;DB_CLOSE_DELAY=-1
Username: sa
Password: [leave blank]
```

Because the development database is stored in memory, application data is reset when the development database is recreated.

---

### 3. Run Stock Society with the Production Profile

The `prod` profile uses a persistent MySQL database.

Create the database before starting the application:

```sql
CREATE DATABASE stocksociety;
```

The production configuration supports the following environment variables:

```text
DB_HOST
DB_PORT
DB_NAME
DB_USERNAME
DB_PASSWORD
```

Example using Git Bash:

```bash
export DB_HOST=localhost
export DB_PORT=3306
export DB_NAME=stocksociety
export DB_USERNAME=root
export DB_PASSWORD='your_mysql_password'
```

Do not commit real database passwords to the repository.

Start Stock Society using the production profile:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

The application will connect to the configured MySQL database without requiring changes to the Java source code.

---

## REST Endpoints

### Supplier Microservice

| Method | Endpoint |
|---------|----------|
| GET | /suppliers |
| GET | /suppliers/{id} |
| POST | /suppliers |
| PUT | /suppliers/{id} |
| DELETE | /suppliers/{id} |
| GET | /suppliers/search |

---

## Spring Profiles

### Stock Society Main Application

The main application uses hierarchical YAML configuration instead of `application.properties`.

Configuration files:

```text
src/main/resources/
├── application.yml
├── application-dev.yml
└── application-prod.yml
```

The available profiles are:

- **dev** → In-memory H2 database with the H2 console enabled.
- **prod** → Persistent MySQL database configured using environment variables.

Profiles can be selected from the command line without modifying source code.

Development:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Production:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

### Supplier Service

The Supplier Service supports two Spring profiles:

- **dev** → H2 Database
- **qa** → PostgreSQL Database

The PostgreSQL QA environment can be started using the included Docker Compose configuration.

---

## Docker

A `docker-compose.yml` file is included to run the PostgreSQL database used by the QA profile.

To start PostgreSQL:

```bash
docker compose up
```
---

## Team Contributions

### Princess Krish Anne Bernardino

- Main application user registration
- BCrypt password encoding and user persistence
- User roles: ADMIN, STAFF, and CUSTOMER
- Custom login page and authentication interface
- Role-based interface improvements
- Main application YAML configuration
- Development profile using H2
- Production profile using MySQL
- Profile switching and environment variable configuration
- Main application profile testing and integration

### Serena Nina Omondi

- Supplier Microservice
- Supplier CRUD REST API
- Supplier search functionality
- Supplier Service security
- Supplier Service H2 development configuration
- Supplier Service PostgreSQL QA configuration
- Docker Compose configuration
- REST integration between Supplier Service and Stock Society