package com.deloitte.employee_management.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryStatsDTO {

    private String department;
    private Double averageSalary;
    private Double minSalary;
    private Double maxSalary;
    private Long employeeCount;
}
