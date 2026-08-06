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
- H2 Database
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

- User Registration and Authentication
- Role-Based Access Control
- Admin Dashboard
- Inventory Management
- Item CRUD Operations
- Search, Filtering and Pagination
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

### 1. Start the Supplier Microservice

```bash
cd supplier-service
./mvnw spring-boot:run
```

Runs on:

```
http://localhost:8081
```

Default credentials:

```
Username: admin
Password: admin123
```

---

### 2. Start the Main Application

```bash
./mvnw spring-boot:run
```

Runs on:

```
http://localhost:8080
```

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

The Supplier Service supports two Spring profiles:

- **dev** → H2 Database
- **qa** → PostgreSQL Database

---

## Docker

A `docker-compose.yml` file is included to run the PostgreSQL database used by the QA profile.

To start PostgreSQL:

```bash
docker compose up
```
