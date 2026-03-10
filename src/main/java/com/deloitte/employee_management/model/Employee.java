package com.deloitte.employee_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Department is required")
    @Pattern(regexp = "^(IT|HR|Finance|Sales)$", message = "Department must be IT, HR, Finance, or Sales")
    @Column(nullable = false)
    private String department;

    @NotBlank(message = "Designation is required")
    @Pattern(regexp = "^(Junior|Senior|Manager)$", message = "Designation must be Junior, Senior, or Manager")
    @Column(nullable = false)
    private String designation;

    @NotNull(message = "Salary is required")
    @Min(value = 1, message = "Salary must be at least 1")
    @Column(nullable = false)
    private Double salary;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Joining date must be in YYYY-MM-DD format")
    private String joiningDate;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isActive = true;
}
