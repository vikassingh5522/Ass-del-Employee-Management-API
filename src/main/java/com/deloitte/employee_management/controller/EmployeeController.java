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

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeDTO>> createEmployee(
            @Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO created = employeeService.createEmployee(employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Employee created successfully", created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<EmployeeDTO>>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Page<EmployeeDTO> employees = employeeService.getAllEmployees(page, size, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponse.success("Employees retrieved successfully", employees));
    }

    @GetMapping("/department/{dept}")
    public ResponseEntity<ApiResponse<List<EmployeeDTO>>> getByDepartment(
            @PathVariable String dept) {
        List<EmployeeDTO> employees = employeeService.getEmployeesByDepartment(dept);
        return ResponseEntity.ok(ApiResponse.success("Employees in " + dept + " department retrieved", employees));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<EmployeeDTO>>> getActiveEmployees() {
        List<EmployeeDTO> employees = employeeService.getActiveEmployees();
        return ResponseEntity.ok(ApiResponse.success("Active employees retrieved", employees));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<EmployeeDTO>>> searchByName(
            @RequestParam String name) {
        List<EmployeeDTO> employees = employeeService.searchEmployeesByName(name);
        return ResponseEntity.ok(ApiResponse.success("Search results for '" + name + "'", employees));
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> getEmployeeCount() {
        long count = employeeService.getEmployeeCount();
        return ResponseEntity.ok(ApiResponse.success("Total employee count retrieved", count));
    }

    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<List<EmployeeDTO>>> createMultipleEmployees(
            @Valid @RequestBody List<EmployeeDTO> employeeDTOs) {
        List<EmployeeDTO> created = employeeService.createMultipleEmployees(employeeDTOs);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(created.size() + " employees created successfully", created));
    }

    @GetMapping("/salary-stats")
    public ResponseEntity<ApiResponse<List<SalaryStatsDTO>>> getSalaryStats() {
        List<SalaryStatsDTO> stats = employeeService.getSalaryStatsByDepartment();
        return ResponseEntity.ok(ApiResponse.success("Salary statistics retrieved", stats));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDTO>> getEmployeeById(@PathVariable Long id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(ApiResponse.success("Employee retrieved successfully", employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDTO>> updateEmployee(
            @PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO updated = employeeService.updateEmployee(id, employeeDTO);
        return ResponseEntity.ok(ApiResponse.success("Employee updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.success("Employee deleted successfully", null));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<EmployeeDTO>> deactivateEmployee(@PathVariable Long id) {
        EmployeeDTO deactivated = employeeService.deactivateEmployee(id);
        return ResponseEntity.ok(ApiResponse.success("Employee deactivated successfully", deactivated));
    }
}
