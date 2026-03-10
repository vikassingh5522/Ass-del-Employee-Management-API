package com.deloitte.employee_management.config;

import com.deloitte.employee_management.model.Employee;
import com.deloitte.employee_management.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) {
        List<Employee> sampleEmployees = List.of(
                Employee.builder()
                        .name("Rahul Sharma")
                        .email("rahul.sharma@deloitte.com")
                        .department("IT")
                        .designation("Senior")
                        .salary(85000.0)
                        .joiningDate("2023-01-15")
                        .isActive(true)
                        .build(),
                Employee.builder()
                        .name("Priya Patel")
                        .email("priya.patel@deloitte.com")
                        .department("HR")
                        .designation("Manager")
                        .salary(95000.0)
                        .joiningDate("2022-06-20")
                        .isActive(true)
                        .build(),
                Employee.builder()
                        .name("Amit Kumar")
                        .email("amit.kumar@deloitte.com")
                        .department("Finance")
                        .designation("Junior")
                        .salary(45000.0)
                        .joiningDate("2024-03-10")
                        .isActive(true)
                        .build(),
                Employee.builder()
                        .name("Sneha Reddy")
                        .email("sneha.reddy@deloitte.com")
                        .department("Sales")
                        .designation("Senior")
                        .salary(72000.0)
                        .joiningDate("2023-08-05")
                        .isActive(true)
                        .build(),
                Employee.builder()
                        .name("Vikram Singh")
                        .email("vikram.singh@deloitte.com")
                        .department("IT")
                        .designation("Manager")
                        .salary(110000.0)
                        .joiningDate("2021-11-01")
                        .isActive(true)
                        .build(),
                Employee.builder()
                        .name("Neha Gupta")
                        .email("neha.gupta@deloitte.com")
                        .department("HR")
                        .designation("Junior")
                        .salary(42000.0)
                        .joiningDate("2024-07-18")
                        .isActive(true)
                        .build()
        );

        employeeRepository.saveAll(sampleEmployees);
        System.out.println("Sample employee data initialized successfully! (" + sampleEmployees.size() + " employees loaded)");
    }
}
