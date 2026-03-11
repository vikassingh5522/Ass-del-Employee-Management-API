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

## Live Demo

**Base URL:** `https://b93a8384-a44c-4f90-86fd-7347511dc7db-00-n8dmnxli2s84.kirk.replit.dev`

---

## Quick Test APIs

> Click the **GET** links directly in your browser, or use the **curl** commands for POST/PUT/PATCH/DELETE.

### 1. Get All Employees (Paginated)
```
GET https://b93a8384-a44c-4f90-86fd-7347511dc7db-00-n8dmnxli2s84.kirk.replit.dev/api/employees?page=0&size=10&sortBy=id&sortDir=asc
```

### 2. Get Employee by ID
```
GET https://b93a8384-a44c-4f90-86fd-7347511dc7db-00-n8dmnxli2s84.kirk.replit.dev/api/employees/1
```

### 3. Get Employees by Department
```
GET https://b93a8384-a44c-4f90-86fd-7347511dc7db-00-n8dmnxli2s84.kirk.replit.dev/api/employees/department/IT
GET https://b93a8384-a44c-4f90-86fd-7347511dc7db-00-n8dmnxli2s84.kirk.replit.dev/api/employees/department/HR
GET https://b93a8384-a44c-4f90-86fd-7347511dc7db-00-n8dmnxli2s84.kirk.replit.dev/api/employees/department/Finance
GET https://b93a8384-a44c-4f90-86fd-7347511dc7db-00-n8dmnxli2s84.kirk.replit.dev/api/employees/department/Sales
```

### 4. Get Active Employees
```
GET https://b93a8384-a44c-4f90-86fd-7347511dc7db-00-n8dmnxli2s84.kirk.replit.dev/api/employees/active
```

### 5. Search Employees by Name
```
GET https://b93a8384-a44c-4f90-86fd-7347511dc7db-00-n8dmnxli2s84.kirk.replit.dev/api/employees/search?name=Rahul
```

### 6. Get Employee Count
```
GET https://b93a8384-a44c-4f90-86fd-7347511dc7db-00-n8dmnxli2s84.kirk.replit.dev/api/employees/count
```

### 7. Get Salary Statistics by Department
```
GET https://b93a8384-a44c-4f90-86fd-7347511dc7db-00-n8dmnxli2s84.kirk.replit.dev/api/employees/salary-stats
```

### 8. Create New Employee (POST)
```bash
curl -X POST https://b93a8384-a44c-4f90-86fd-7347511dc7db-00-n8dmnxli2s84.kirk.replit.dev/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "email": "test.user@deloitte.com",
    "department": "IT",
    "designation": "Junior",
    "salary": 60000,
    "joiningDate": "2026-03-10",
    "isActive": true
  }'
```

### 9. Bulk Create Employees (POST)
```bash
curl -X POST https://b93a8384-a44c-4f90-86fd-7347511dc7db-00-n8dmnxli2s84.kirk.replit.dev/api/employees/bulk \
  -H "Content-Type: application/json" \
  -d '[
    {
      "name": "User One",
      "email": "user.one@deloitte.com",
      "department": "HR",
      "designation": "Manager",
      "salary": 70000,
      "joiningDate": "2026-01-01",
      "isActive": true
    },
    {
      "name": "User Two",
      "email": "user.two@deloitte.com",
      "department": "Finance",
      "designation": "Senior",
      "salary": 55000,
      "joiningDate": "2026-02-15",
      "isActive": true
    }
  ]'
```

### 10. Update Employee (PUT)
```bash
curl -X PUT https://b93a8384-a44c-4f90-86fd-7347511dc7db-00-n8dmnxli2s84.kirk.replit.dev/api/employees/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Rahul Sharma Updated",
    "email": "rahul.sharma@deloitte.com",
    "department": "IT",
    "designation": "Manager",
    "salary": 95000,
    "joiningDate": "2023-01-15",
    "isActive": true
  }'
```

### 11. Deactivate Employee (PATCH)
```bash
curl -X PATCH https://b93a8384-a44c-4f90-86fd-7347511dc7db-00-n8dmnxli2s84.kirk.replit.dev/api/employees/1/deactivate
```

### 12. Delete Employee (DELETE)
```bash
curl -X DELETE https://b93a8384-a44c-4f90-86fd-7347511dc7db-00-n8dmnxli2s84.kirk.replit.dev/api/employees/6
```

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

src/test/java/com/deloitte/employee_management/
├── EmployeeManagementApplicationTests.java  ← Context load test
└── controller/
    └── EmployeeControllerTest.java          ← 40 integration tests
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

### Run Tests
```bash
./mvnw test
```

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
```json
{
  "name": "Test User",
  "email": "test.user@deloitte.com",
  "department": "IT",
  "designation": "Junior",
  "salary": 60000,
  "joiningDate": "2026-03-10",
  "isActive": true
}
```

### Success Response (201)
```json
{
  "status": "success",
  "message": "Employee created successfully",
  "data": {
    "id": 7,
    "name": "Test User",
    "email": "test.user@deloitte.com",
    "department": "IT",
    "designation": "Junior",
    "salary": 60000.0,
    "joiningDate": "2026-03-10",
    "isActive": true
  }
}
```

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

### Duplicate Email Response (409)
```json
{
  "status": "error",
  "message": "Employee with email 'rahul.sharma@deloitte.com' already exists",
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

## Test Coverage

The project includes **40 integration tests** covering all endpoints:

| Endpoint | Tests | Scenarios |
|----------|-------|-----------|
| GET /api/employees | 4 | Pagination, sorting, default params |
| GET /api/employees/{id} | 3 | Valid ID, not found, invalid ID type |
| GET /api/employees/department/{dept} | 4 | IT, HR, Finance, non-existing dept |
| GET /api/employees/active | 2 | All active, after deactivation |
| GET /api/employees/search | 4 | Exact, case-insensitive, partial, no match |
| GET /api/employees/count | 2 | Initial count, after changes |
| GET /api/employees/salary-stats | 2 | All departments, correct fields |
| POST /api/employees | 9 | Valid, duplicate email, missing fields, invalid values |
| POST /api/employees/bulk | 1 | Bulk creation |
| PUT /api/employees/{id} | 4 | Valid update, not found, duplicate email, same email |
| PATCH /api/employees/{id}/deactivate | 2 | Success, not found |
| DELETE /api/employees/{id} | 3 | Success, not found, verify deleted |

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
- H2 Console for database inspection (`/h2-console`)
- 40 integration tests with full endpoint coverage

---

## Pre-loaded Sample Data

| ID | Name          | Department | Designation | Salary  |
|----|---------------|------------|-------------|---------|
| 1  | Rahul Sharma  | IT         | Senior      | 85,000  |
| 2  | Priya Patel   | HR         | Manager     | 95,000  |
| 3  | Amit Kumar    | Finance    | Junior      | 45,000  |
| 4  | Sneha Reddy   | Sales      | Senior      | 72,000  |
| 5  | Vikram Singh  | IT         | Manager     | 110,000 |
| 6  | Neha Gupta    | HR         | Junior      | 42,000  |

---

## Developed By

**Vikas Singh** — All Rights Reserved

| Field        | Details                        |
|--------------|--------------------------------|
| Name         | Vikas Singh                   |
| Email        | vikas.kumar.singh.job123@gmail.com |
| College      | D. Y. Patil College of Engineering and Innovation, Varale, Talegaon, Pune |
| GitHub       | [vikassingh5522](https://github.com/vikassingh5522) |

---

> Built with Spring Boot and  vikas singh | Deloitte Graduate Hiring Assessment 2026
