

# 📝 To-Do List Application

A Java Spring Boot application that manages a to-do list with CRUD operations, backed by MySQL, and fully containerized using Docker & Docker Compose.
Includes automated startup via a `startup.sh` script and API documentation via Swagger.

---
## 📚 Table of Contents

1. [Features](#features)
2. [Tech Stack](#tech-stack)
3. [Architecture](#architecture)
4. [API Endpoints](#api-endpoints)
5. [Setup & Installation](#setup--installation)
6. [Running the Application](#running-the-application)
7. [Swagger API Documentation](#swagger-api-documentation)
8. [Requirements Check](#requirements-check)
---

## Features

* Create, Read, Update, Delete (CRUD) to-do items.
* MySQL database with persistent storage using Docker volumes.
* MySQL is only accessible within the Docker network (no exposed ports).
* Java Spring Boot application exposed on port **10279** (mapped internally to 8080).
* API documentation powered by **Swagger/OpenAPI**.
* Automated application startup using a **bash script**.

---

## Tech Stack

* **Java 24 (Temurin JDK)**
* **Spring Boot**
* **MySQL 8.0**
* **Docker & Docker Compose**
* **Swagger / Springdoc OpenAPI**

---

## Architecture

This project follows a **three-layer architecture** to separate concerns:

1. **Repository Layer**  
   - Responsible for all database access and persistence.  
   - Uses **Spring Data JPA** to map Java objects to database records.  
   - Contains interfaces (e.g., `ItemRepository`) that extend `JpaRepository`, allowing CRUD operations without boilerplate SQL.

2. **Service Layer**  
   - Contains the **business logic** of the application.  
   - Receives requests from the controller, processes data, applies rules, and interacts with the repository.  
   - Defined via a **service interface** (`ItemServiceIF`) and an **implementation class** (`ItemServiceImpl`).

3. **Controller Layer**  
   - Handles **HTTP requests** and **responses**.  
   - Uses Spring’s `@RestController` and request mapping annotations (`@GetMapping`, `@PostMapping`, etc.).  
   - Accepts and validates request data (DTOs), calls the service layer, and formats the API responses.

**Flow of a request:**  
`Client → Controller → Service → Repository → Database → Service → Controller → Client`

**Benefits of this architecture:**  
- Clear **separation of concerns**.  
- **Testability** — each layer can be tested independently.  
- **Maintainability** — changes in one layer have minimal impact on others.  
- **Scalability** — easy to extend functionality without breaking existing code.


---

## API Endpoints

| Method | Endpoint          | Description                     |
| ------ | ----------------- | ------------------------------- |
| POST   | `/api/items`      | Create a new to-do item         |
| GET    | `/api/items`      | Get all to-do items (paginated) |
| GET    | `/api/items/{id}` | Get a to-do item by ID          |
| PUT    | `/api/items/{id}` | Update a to-do item             |
| DELETE | `/api/items/{id}` | Delete a to-do item             |

Example API request/response formats are documented in **Swagger**.

---

## Setup & Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/Seif2001/todo-list-app.git
   cd todo-list-app
   ```

2. **Ensure Prerequisites Installed**

   * Docker
   * Docker Compose
   * Bash

3. **Make startup.sh executable**

   ```bash
   chmod +x startup.sh
   ```

---

## Running the Application

**Run using startup script**:

   ```bash
   ./startup.sh
   ```



---

## Swagger API Documentation

Once the application is running, visit:
**[http://localhost:10279/swagger-ui.html](http://localhost:10279/swagger-ui.html)**

---



## Requirements Check

| Requirement                                                            | Status |
| ---------------------------------------------------------------------- | ------ |
| Java CRUD application for To-do list                                   | ✅ Done |
| MySQL database integration                                             | ✅ Done |
| 5 required APIs (Create, Read One, Read All, Update, Delete)           | ✅ Done |
| HTTP status codes & error handling                                     | ✅ Done |
| Use of Java interface for services                                     | ✅ Done |
| Java app listening internally on port 8080, exposed on host port 10279 | ✅ Done |
| MySQL not exposed to host                                              | ✅ Done |
| MySQL root password = "password"                                       | ✅ Done |
| Data persistence with Docker volume                                    | ✅ Done |
| Startup bash script                                                    | ✅ Done |


