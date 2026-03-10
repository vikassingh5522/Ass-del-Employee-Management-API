# Employee Management REST API

A production-ready RESTful API built with Spring Boot for managing employee records. Developed as part of the Deloitte Graduate Hiring Assessment.

---

## Developer Info

| Field        | Details                        |
|--------------|--------------------------------|
| Name         | vikas singh                   |
| Email        | vikas.kumar.singh.job123@gmail.com      |
| College      | D. Y. Patil College of Engineering and Innovation, Varale, Talegaon, Pune|
| Skill Track  | Java & API Development         |

---

## Tech Stack

- **Java 17** — Core language
- **Spring Boot 3.5.11** — Application framework
- **Spring Data JPA** — Database access layer
- **H2 Database** — In-memory database (no installation required)
- **Maven** — Build tool and dependency management
- **Lombok** — Reduces boilerplate code
- **Jakarta Validation** — Input validation

---

## Project Structure

```
src/main/java/com/deloitte/employee_management/
├── EmployeeManagementApplication.java   ← Main entry point
├── model/
│   └── Employee.java                    ← JPA Entity
├── dto/
│   ├── ApiResponse.java                 ← Standard response wrapper
│   ├── EmployeeDTO.java                 ← Data Transfer Object
│   └── SalaryStatsDTO.java             ← Salary statistics DTO
├── repository/
│   └── EmployeeRepository.java          ← Database access layer
├── service/
│   └── EmployeeService.java             ← Business logic
├── controller/
│   └── EmployeeController.java          ← REST endpoints
├── exception/
│   ├── ResourceNotFoundException.java   ← Custom 404 exception
│   ├── DuplicateResourceException.java  ← Custom 409 exception
│   └── GlobalExceptionHandler.java      ← Centralized error handling
└── config/
    ├── CorsConfig.java                  ← CORS configuration
    └── DataInitializer.java             ← Sample data loader
```

---

## How to Run

### On Local Machine
1. Clone the repository
2. Make sure Java 17+ is installed
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
4. API is available at: `http://localhost:8080/api/employees`

### On Replit
1. Open the project in Replit
2. Click the **Run** button
3. The server starts automatically on port 8080

---

## API Endpoints

### Basic CRUD Operations

| Method | Endpoint                | Description          | Status Codes |
|--------|------------------------|----------------------|-------------|
| POST   | `/api/employees`       | Create new employee  | 201, 400, 409 |
| GET    | `/api/employees`       | Get all employees (paginated) | 200 |
| GET    | `/api/employees/{id}`  | Get employee by ID   | 200, 404 |
| PUT    | `/api/employees/{id}`  | Update employee      | 200, 400, 404, 409 |
| DELETE | `/api/employees/{id}`  | Delete employee      | 204, 404 |

### Extra Features

| Method | Endpoint                           | Description                    | Status Codes |
|--------|------------------------------------|--------------------------------|-------------|
| GET    | `/api/employees/department/{dept}` | Filter by department           | 200 |
| GET    | `/api/employees/active`            | Get active employees only      | 200 |
| PATCH  | `/api/employees/{id}/deactivate`   | Deactivate an employee         | 200, 404 |
| GET    | `/api/employees/search?name=`      | Search by name                 | 200 |
| GET    | `/api/employees/count`             | Get total employee count       | 200 |
| POST   | `/api/employees/bulk`              | Create multiple employees      | 201, 400, 409 |
| GET    | `/api/employees/salary-stats`      | Salary stats by department     | 200 |

### Pagination Parameters (GET /api/employees)

| Parameter | Default | Description          |
|-----------|---------|----------------------|
| page      | 0       | Page number (0-based) |
| size      | 10      | Items per page        |
| sortBy    | id      | Sort field            |
| sortDir   | asc     | Sort direction (asc/desc) |

---

## Sample JSON

### Create Employee (POST /api/employees)



### Validation Error Response (400)
```json
{
  "status": "error",
  "message": "Validation failed",
  "data": {
    "name": "Name is required",
    "email": "Email should be valid",
    "salary": "Salary must be at least 1"
  }
}
```

### Not Found Response (404)
```json
{
  "status": "error",
  "message": "Employee not found with id: 99",
  "data": null
}
```

---

## Allowed Values

| Field       | Allowed Values                  |
|-------------|---------------------------------|
| department  | IT, HR, Finance, Sales          |
| designation | Junior, Senior, Manager         |
| salary      | Must be greater than 0          |
| joiningDate | Format: YYYY-MM-DD              |

---

## Features

- Full CRUD operations with proper HTTP status codes
- Standardized API response format (`ApiResponse` wrapper)
- Input validation with detailed error messages
- Global exception handling with `@ControllerAdvice`
- Pagination and sorting support
- Duplicate email detection (409 Conflict)
- Department-wise salary statistics
- Bulk employee creation
- CORS enabled for cross-origin requests
- Sample data auto-loaded on startup (6 employees)
- H2 Console for database inspection
