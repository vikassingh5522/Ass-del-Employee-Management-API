package com.deloitte.employee_management.controller;

import com.deloitte.employee_management.dto.ApiResponse;
import com.deloitte.employee_management.dto.EmployeeDTO;
import com.deloitte.employee_management.dto.SalaryStatsDTO;
import com.deloitte.employee_management.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Employee Management API.
 * Provides CRUD operations and advanced features for managing employee records.
 * Base path: /api/employees
 */
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // POST /api/employees - Create a new employee
    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeDTO>> createEmployee(
            @Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO created = employeeService.createEmployee(employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Employee created successfully", created));
    }

    // GET /api/employees - Get all employees with pagination and sorting
    @GetMapping
    public ResponseEntity<ApiResponse<Page<EmployeeDTO>>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Page<EmployeeDTO> employees = employeeService.getAllEmployees(page, size, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponse.success("Employees retrieved successfully", employees));
    }

    // GET /api/employees/department/{dept} - Get employees by department
    @GetMapping("/department/{dept}")
    public ResponseEntity<ApiResponse<List<EmployeeDTO>>> getByDepartment(
            @PathVariable String dept) {
        List<EmployeeDTO> employees = employeeService.getEmployeesByDepartment(dept);
        return ResponseEntity.ok(ApiResponse.success("Employees in " + dept + " department retrieved", employees));
    }

    // GET /api/employees/active - Get all active employees
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<EmployeeDTO>>> getActiveEmployees() {
        List<EmployeeDTO> employees = employeeService.getActiveEmployees();
        return ResponseEntity.ok(ApiResponse.success("Active employees retrieved", employees));
    }

    // GET /api/employees/search?name= - Search employees by name (case-insensitive)
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<EmployeeDTO>>> searchByName(
            @RequestParam String name) {
        List<EmployeeDTO> employees = employeeService.searchEmployeesByName(name);
        return ResponseEntity.ok(ApiResponse.success("Search results for '" + name + "'", employees));
    }

    // GET /api/employees/count - Get total employee count
    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> getEmployeeCount() {
        long count = employeeService.getEmployeeCount();
        return ResponseEntity.ok(ApiResponse.success("Total employee count retrieved", count));
    }

    // POST /api/employees/bulk - Create multiple employees at once
    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<List<EmployeeDTO>>> createMultipleEmployees(
            @Valid @RequestBody List<EmployeeDTO> employeeDTOs) {
        List<EmployeeDTO> created = employeeService.createMultipleEmployees(employeeDTOs);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(created.size() + " employees created successfully", created));
    }

    // GET /api/employees/salary-stats - Get salary statistics grouped by department
    @GetMapping("/salary-stats")
    public ResponseEntity<ApiResponse<List<SalaryStatsDTO>>> getSalaryStats() {
        List<SalaryStatsDTO> stats = employeeService.getSalaryStatsByDepartment();
        return ResponseEntity.ok(ApiResponse.success("Salary statistics retrieved", stats));
    }

    // GET /api/employees/{id} - Get a single employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDTO>> getEmployeeById(@PathVariable Long id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(ApiResponse.success("Employee retrieved successfully", employee));
    }

    // PUT /api/employees/{id} - Update an existing employee by ID
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDTO>> updateEmployee(
            @PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO updated = employeeService.updateEmployee(id, employeeDTO);
        return ResponseEntity.ok(ApiResponse.success("Employee updated successfully", updated));
    }

    // DELETE /api/employees/{id} - Delete an employee by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.success("Employee deleted successfully", null));
    }

    // PATCH /api/employees/{id}/deactivate - Deactivate an employee by ID
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<EmployeeDTO>> deactivateEmployee(@PathVariable Long id) {
        EmployeeDTO deactivated = employeeService.deactivateEmployee(id);
        return ResponseEntity.ok(ApiResponse.success("Employee deactivated successfully", deactivated));
    }
}
