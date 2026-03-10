package com.deloitte.employee_management.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Department is required")
    @Pattern(regexp = "^(IT|HR|Finance|Sales)$", message = "Department must be IT, HR, Finance, or Sales")
    private String department;

    @NotBlank(message = "Designation is required")
    @Pattern(regexp = "^(Junior|Senior|Manager)$", message = "Designation must be Junior, Senior, or Manager")
    private String designation;

    @NotNull(message = "Salary is required")
    @Min(value = 1, message = "Salary must be at least 1")
    private Double salary;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Joining date must be in YYYY-MM-DD format")
    private String joiningDate;

    private Boolean isActive;
}
