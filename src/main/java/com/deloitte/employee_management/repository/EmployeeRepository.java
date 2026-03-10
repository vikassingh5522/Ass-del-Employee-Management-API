package com.deloitte.employee_management.repository;

import com.deloitte.employee_management.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByDepartment(String department);

    List<Employee> findByIsActiveTrue();

    List<Employee> findByNameContainingIgnoreCase(String name);

    Optional<Employee> findByEmail(String email);

    @Query("SELECT e.department, AVG(e.salary), MIN(e.salary), MAX(e.salary), COUNT(e) " +
           "FROM Employee e GROUP BY e.department")
    List<Object[]> getSalaryStatsByDepartment();
}
