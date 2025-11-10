package com.example.ems.backend.service.impl;

import com.example.ems.backend.dto.EmployeeDto;
import com.example.ems.backend.entity.Employee;
import com.example.ems.backend.mapper.EmployeeMapper;
import com.example.ems.backend.repository.EmployeeRepository;
import com.example.ems.backend.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
      Employee employee = employeeRepository.findById(employeeId)
              .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));
      return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map((EmployeeMapper::mapToEmployeeDto))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updateEmployeeDto) {
       Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

       employee.setFirstName(updateEmployeeDto.getFirstName());
       employee.setLastName(updateEmployeeDto.getLastName());
       employee.setEmail(updateEmployeeDto.getEmail());
       Employee updatedEmployee = employeeRepository.save(employee);

       return EmployeeMapper.mapToEmployeeDto(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));
        employeeRepository.delete(employee);
    }

}
