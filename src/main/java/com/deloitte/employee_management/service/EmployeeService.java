package com.deloitte.employee_management.service;

import com.deloitte.employee_management.dto.EmployeeDTO;
import com.deloitte.employee_management.dto.SalaryStatsDTO;
import com.deloitte.employee_management.exception.DuplicateResourceException;
import com.deloitte.employee_management.exception.ResourceNotFoundException;
import com.deloitte.employee_management.model.Employee;
import com.deloitte.employee_management.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeDTO createEmployee(EmployeeDTO dto) {
        Optional<Employee> existing = employeeRepository.findByEmail(dto.getEmail());
        if (existing.isPresent()) {
            throw new DuplicateResourceException("Employee with email '" + dto.getEmail() + "' already exists");
        }

        Employee employee = convertToEntity(dto);
        if (employee.getIsActive() == null) {
            employee.setIsActive(true);
        }
        Employee saved = employeeRepository.save(employee);
        return convertToDTO(saved);
    }

    public Page<EmployeeDTO> getAllEmployees(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return employeeRepository.findAll(pageRequest).map(this::convertToDTO);
    }

    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return convertToDTO(employee);
    }

    public EmployeeDTO updateEmployee(Long id, EmployeeDTO dto) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        if (!existing.getEmail().equals(dto.getEmail())) {
            Optional<Employee> emailOwner = employeeRepository.findByEmail(dto.getEmail());
            if (emailOwner.isPresent()) {
                throw new DuplicateResourceException("Employee with email '" + dto.getEmail() + "' already exists");
            }
        }

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setDepartment(dto.getDepartment());
        existing.setDesignation(dto.getDesignation());
        existing.setSalary(dto.getSalary());
        existing.setJoiningDate(dto.getJoiningDate());
        if (dto.getIsActive() != null) {
            existing.setIsActive(dto.getIsActive());
        }

        Employee updated = employeeRepository.save(existing);
        return convertToDTO(updated);
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
    }

    public List<EmployeeDTO> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment(department)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<EmployeeDTO> getActiveEmployees() {
        return employeeRepository.findByIsActiveTrue()
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public EmployeeDTO deactivateEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        employee.setIsActive(false);
        Employee updated = employeeRepository.save(employee);
        return convertToDTO(updated);
    }

    public List<EmployeeDTO> searchEmployeesByName(String name) {
        return employeeRepository.findByNameContainingIgnoreCase(name)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public long getEmployeeCount() {
        return employeeRepository.count();
    }

    public List<EmployeeDTO> createMultipleEmployees(List<EmployeeDTO> dtos) {
        return dtos.stream().map(this::createEmployee).collect(Collectors.toList());
    }

    public List<SalaryStatsDTO> getSalaryStatsByDepartment() {
        List<Object[]> results = employeeRepository.getSalaryStatsByDepartment();
        return results.stream().map(row -> SalaryStatsDTO.builder()
                .department((String) row[0])
                .averageSalary((Double) row[1])
                .minSalary((Double) row[2])
                .maxSalary((Double) row[3])
                .employeeCount((Long) row[4])
                .build()
        ).collect(Collectors.toList());
    }

    private Employee convertToEntity(EmployeeDTO dto) {
        return Employee.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .department(dto.getDepartment())
                .designation(dto.getDesignation())
                .salary(dto.getSalary())
                .joiningDate(dto.getJoiningDate())
                .isActive(dto.getIsActive() != null ? dto.getIsActive() : true)
                .build();
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .department(employee.getDepartment())
                .designation(employee.getDesignation())
                .salary(employee.getSalary())
                .joiningDate(employee.getJoiningDate())
                .isActive(employee.getIsActive())
                .build();
    }
}
