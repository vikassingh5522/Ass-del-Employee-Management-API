package com.deloitte.employee_management.controller;

import com.deloitte.employee_management.dto.EmployeeDTO;
import com.deloitte.employee_management.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    // ==================== 1. GET All Employees (Paginated) ====================

    @Test
    @Order(1)
    void getAllEmployees_ShouldReturnPaginatedResults() throws Exception {
        mockMvc.perform(get("/api/employees")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "id")
                        .param("sortDir", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Employees retrieved successfully"))
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.content", hasSize(6)))
                .andExpect(jsonPath("$.data.totalElements").value(6))
                .andExpect(jsonPath("$.data.totalPages").value(1));
    }

    @Test
    @Order(2)
    void getAllEmployees_WithPagination_ShouldReturnCorrectPage() throws Exception {
        mockMvc.perform(get("/api/employees")
                        .param("page", "0")
                        .param("size", "2")
                        .param("sortBy", "id")
                        .param("sortDir", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content", hasSize(2)))
                .andExpect(jsonPath("$.data.totalElements").value(6))
                .andExpect(jsonPath("$.data.totalPages").value(3))
                .andExpect(jsonPath("$.data.content[0].name").value("Rahul Sharma"));
    }

    @Test
    @Order(3)
    void getAllEmployees_SortByDescending_ShouldReturnSorted() throws Exception {
        mockMvc.perform(get("/api/employees")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "salary")
                        .param("sortDir", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].name").value("Vikram Singh"))
                .andExpect(jsonPath("$.data.content[0].salary").value(110000.0));
    }

    @Test
    @Order(4)
    void getAllEmployees_DefaultParams_ShouldWork() throws Exception {
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.content").isArray());
    }

    // ==================== 2. GET Employee by ID ====================

    @Test
    @Order(5)
    void getEmployeeById_ExistingId_ShouldReturnEmployee() throws Exception {
        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Employee retrieved successfully"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("Rahul Sharma"))
                .andExpect(jsonPath("$.data.email").value("rahul.sharma@deloitte.com"))
                .andExpect(jsonPath("$.data.department").value("IT"))
                .andExpect(jsonPath("$.data.designation").value("Senior"))
                .andExpect(jsonPath("$.data.salary").value(85000.0));
    }

    @Test
    @Order(6)
    void getEmployeeById_NonExistingId_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/api/employees/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Employee not found with id: 999"));
    }

    @Test
    @Order(7)
    void getEmployeeById_InvalidIdType_ShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/employees/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"));
    }

    // ==================== 3. GET Employees by Department ====================

    @Test
    @Order(8)
    void getByDepartment_IT_ShouldReturnITEmployees() throws Exception {
        mockMvc.perform(get("/api/employees/department/IT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Employees in IT department retrieved"))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[*].department", everyItem(is("IT"))));
    }

    @Test
    @Order(9)
    void getByDepartment_HR_ShouldReturnHREmployees() throws Exception {
        mockMvc.perform(get("/api/employees/department/HR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[*].department", everyItem(is("HR"))));
    }

    @Test
    @Order(10)
    void getByDepartment_Finance_ShouldReturnFinanceEmployees() throws Exception {
        mockMvc.perform(get("/api/employees/department/Finance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].name").value("Amit Kumar"));
    }

    @Test
    @Order(11)
    void getByDepartment_NonExisting_ShouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/api/employees/department/Marketing"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    // ==================== 4. GET Active Employees ====================

    @Test
    @Order(12)
    void getActiveEmployees_ShouldReturnAllActive() throws Exception {
        mockMvc.perform(get("/api/employees/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Active employees retrieved"))
                .andExpect(jsonPath("$.data", hasSize(6)))
                .andExpect(jsonPath("$.data[*].isActive", everyItem(is(true))));
    }

    // ==================== 5. Search Employees by Name ====================

    @Test
    @Order(13)
    void searchByName_Rahul_ShouldFindOne() throws Exception {
        mockMvc.perform(get("/api/employees/search")
                        .param("name", "Rahul"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Search results for 'Rahul'"))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].name").value("Rahul Sharma"));
    }

    @Test
    @Order(14)
    void searchByName_CaseInsensitive_ShouldWork() throws Exception {
        mockMvc.perform(get("/api/employees/search")
                        .param("name", "rahul"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].name").value("Rahul Sharma"));
    }

    @Test
    @Order(15)
    void searchByName_PartialMatch_ShouldWork() throws Exception {
        mockMvc.perform(get("/api/employees/search")
                        .param("name", "Kumar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].name").value("Amit Kumar"));
    }

    @Test
    @Order(16)
    void searchByName_NoMatch_ShouldReturnEmpty() throws Exception {
        mockMvc.perform(get("/api/employees/search")
                        .param("name", "NonExistent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    // ==================== 6. GET Employee Count ====================

    @Test
    @Order(17)
    void getEmployeeCount_ShouldReturn6() throws Exception {
        mockMvc.perform(get("/api/employees/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Total employee count retrieved"))
                .andExpect(jsonPath("$.data").value(6));
    }

    // ==================== 7. GET Salary Statistics ====================

    @Test
    @Order(18)
    void getSalaryStats_ShouldReturnStatsByDepartment() throws Exception {
        mockMvc.perform(get("/api/employees/salary-stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Salary statistics retrieved"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(4)))
                .andExpect(jsonPath("$.data[*].department", containsInAnyOrder("IT", "HR", "Finance", "Sales")));
    }

    @Test
    @Order(19)
    void getSalaryStats_ShouldHaveCorrectFields() throws Exception {
        mockMvc.perform(get("/api/employees/salary-stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].department").isNotEmpty())
                .andExpect(jsonPath("$.data[0].averageSalary").isNumber())
                .andExpect(jsonPath("$.data[0].minSalary").isNumber())
                .andExpect(jsonPath("$.data[0].maxSalary").isNumber())
                .andExpect(jsonPath("$.data[0].employeeCount").isNumber());
    }

    // ==================== 8. POST Create Employee ====================

    @Test
    @Order(20)
    void createEmployee_ValidData_ShouldReturn201() throws Exception {
        EmployeeDTO newEmployee = EmployeeDTO.builder()
                .name("Test User")
                .email("test.user@deloitte.com")
                .department("IT")
                .designation("Junior")
                .salary(60000.0)
                .joiningDate("2026-03-10")
                .isActive(true)
                .build();

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEmployee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Employee created successfully"))
                .andExpect(jsonPath("$.data.name").value("Test User"))
                .andExpect(jsonPath("$.data.email").value("test.user@deloitte.com"))
                .andExpect(jsonPath("$.data.department").value("IT"))
                .andExpect(jsonPath("$.data.salary").value(60000.0))
                .andExpect(jsonPath("$.data.id").isNotEmpty());
    }

    @Test
    @Order(21)
    void createEmployee_DuplicateEmail_ShouldReturn409() throws Exception {
        EmployeeDTO duplicate = EmployeeDTO.builder()
                .name("Duplicate User")
                .email("rahul.sharma@deloitte.com")
                .department("IT")
                .designation("Junior")
                .salary(50000.0)
                .joiningDate("2026-01-01")
                .isActive(true)
                .build();

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicate)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message", containsString("already exists")));
    }

    @Test
    @Order(22)
    void createEmployee_MissingName_ShouldReturn400() throws Exception {
        EmployeeDTO invalid = EmployeeDTO.builder()
                .email("no.name@deloitte.com")
                .department("IT")
                .designation("Junior")
                .salary(50000.0)
                .build();

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.data.name").value("Name is required"));
    }

    @Test
    @Order(23)
    void createEmployee_InvalidEmail_ShouldReturn400() throws Exception {
        EmployeeDTO invalid = EmployeeDTO.builder()
                .name("Bad Email")
                .email("not-an-email")
                .department("IT")
                .designation("Junior")
                .salary(50000.0)
                .build();

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.data.email").value("Email should be valid"));
    }

    @Test
    @Order(24)
    void createEmployee_InvalidDepartment_ShouldReturn400() throws Exception {
        EmployeeDTO invalid = EmployeeDTO.builder()
                .name("Bad Dept")
                .email("bad.dept@deloitte.com")
                .department("Marketing")
                .designation("Junior")
                .salary(50000.0)
                .build();

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.department").value("Department must be IT, HR, Finance, or Sales"));
    }

    @Test
    @Order(25)
    void createEmployee_InvalidDesignation_ShouldReturn400() throws Exception {
        EmployeeDTO invalid = EmployeeDTO.builder()
                .name("Bad Designation")
                .email("bad.desig@deloitte.com")
                .department("IT")
                .designation("Director")
                .salary(50000.0)
                .build();

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.designation").value("Designation must be Junior, Senior, or Manager"));
    }

    @Test
    @Order(26)
    void createEmployee_ZeroSalary_ShouldReturn400() throws Exception {
        EmployeeDTO invalid = EmployeeDTO.builder()
                .name("Zero Salary")
                .email("zero.salary@deloitte.com")
                .department("IT")
                .designation("Junior")
                .salary(0.0)
                .build();

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.salary").value("Salary must be at least 1"));
    }

    @Test
    @Order(27)
    void createEmployee_InvalidJoiningDateFormat_ShouldReturn400() throws Exception {
        EmployeeDTO invalid = EmployeeDTO.builder()
                .name("Bad Date")
                .email("bad.date@deloitte.com")
                .department("IT")
                .designation("Junior")
                .salary(50000.0)
                .joiningDate("10-03-2026")
                .build();

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.joiningDate").value("Joining date must be in YYYY-MM-DD format"));
    }

    @Test
    @Order(28)
    void createEmployee_NullSalary_ShouldReturn400() throws Exception {
        String json = "{\"name\":\"No Salary\",\"email\":\"no.salary@deloitte.com\",\"department\":\"IT\",\"designation\":\"Junior\"}";

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.salary").value("Salary is required"));
    }

    // ==================== 9. POST Bulk Create Employees ====================

    @Test
    @Order(29)
    void createMultipleEmployees_ShouldReturn201() throws Exception {
        List<EmployeeDTO> employees = List.of(
                EmployeeDTO.builder()
                        .name("Bulk User One")
                        .email("bulk.one@deloitte.com")
                        .department("HR")
                        .designation("Manager")
                        .salary(70000.0)
                        .joiningDate("2026-01-01")
                        .isActive(true)
                        .build(),
                EmployeeDTO.builder()
                        .name("Bulk User Two")
                        .email("bulk.two@deloitte.com")
                        .department("Finance")
                        .designation("Junior")
                        .salary(55000.0)
                        .joiningDate("2026-02-15")
                        .isActive(true)
                        .build()
        );

        mockMvc.perform(post("/api/employees/bulk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employees)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("2 employees created successfully"))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].name").value("Bulk User One"))
                .andExpect(jsonPath("$.data[1].name").value("Bulk User Two"));
    }

    // ==================== 10. PUT Update Employee ====================

    @Test
    @Order(30)
    void updateEmployee_ValidData_ShouldReturn200() throws Exception {
        EmployeeDTO updated = EmployeeDTO.builder()
                .name("Rahul Sharma Updated")
                .email("rahul.sharma@deloitte.com")
                .department("IT")
                .designation("Manager")
                .salary(95000.0)
                .joiningDate("2023-01-15")
                .isActive(true)
                .build();

        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Employee updated successfully"))
                .andExpect(jsonPath("$.data.name").value("Rahul Sharma Updated"))
                .andExpect(jsonPath("$.data.designation").value("Manager"))
                .andExpect(jsonPath("$.data.salary").value(95000.0));
    }

    @Test
    @Order(31)
    void updateEmployee_NonExistingId_ShouldReturn404() throws Exception {
        EmployeeDTO updated = EmployeeDTO.builder()
                .name("Ghost")
                .email("ghost@deloitte.com")
                .department("IT")
                .designation("Junior")
                .salary(50000.0)
                .build();

        mockMvc.perform(put("/api/employees/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Employee not found with id: 999"));
    }

    @Test
    @Order(32)
    void updateEmployee_DuplicateEmail_ShouldReturn409() throws Exception {
        EmployeeDTO updated = EmployeeDTO.builder()
                .name("Priya Patel")
                .email("amit.kumar@deloitte.com")
                .department("HR")
                .designation("Manager")
                .salary(95000.0)
                .joiningDate("2022-06-20")
                .isActive(true)
                .build();

        mockMvc.perform(put("/api/employees/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message", containsString("already exists")));
    }

    @Test
    @Order(33)
    void updateEmployee_SameEmail_ShouldSucceed() throws Exception {
        EmployeeDTO updated = EmployeeDTO.builder()
                .name("Priya Patel Promoted")
                .email("priya.patel@deloitte.com")
                .department("HR")
                .designation("Manager")
                .salary(100000.0)
                .joiningDate("2022-06-20")
                .isActive(true)
                .build();

        mockMvc.perform(put("/api/employees/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Priya Patel Promoted"))
                .andExpect(jsonPath("$.data.salary").value(100000.0));
    }

    // ==================== 11. PATCH Deactivate Employee ====================

    @Test
    @Order(34)
    void deactivateEmployee_ShouldSetInactive() throws Exception {
        mockMvc.perform(patch("/api/employees/3/deactivate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Employee deactivated successfully"))
                .andExpect(jsonPath("$.data.id").value(3))
                .andExpect(jsonPath("$.data.isActive").value(false));
    }

    @Test
    @Order(35)
    void deactivateEmployee_NonExistingId_ShouldReturn404() throws Exception {
        mockMvc.perform(patch("/api/employees/999/deactivate"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Employee not found with id: 999"));
    }

    @Test
    @Order(36)
    void getActiveEmployees_AfterDeactivation_ShouldExcludeDeactivated() throws Exception {
        mockMvc.perform(get("/api/employees/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[*].id", not(hasItem(3))));
    }

    // ==================== 12. DELETE Employee ====================

    @Test
    @Order(37)
    void deleteEmployee_ExistingId_ShouldReturn204() throws Exception {
        mockMvc.perform(delete("/api/employees/6"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(38)
    void deleteEmployee_NonExistingId_ShouldReturn404() throws Exception {
        mockMvc.perform(delete("/api/employees/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Employee not found with id: 999"));
    }

    @Test
    @Order(39)
    void getEmployeeById_AfterDelete_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/api/employees/6"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(40)
    void getEmployeeCount_AfterCreateAndDelete_ShouldBeCorrect() throws Exception {
        long count = employeeRepository.count();
        mockMvc.perform(get("/api/employees/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(count));
    }
}
