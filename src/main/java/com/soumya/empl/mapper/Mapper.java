package com.soumya.empl.mapper;

import com.soumya.empl.dto.EmployeeDTO;
import com.soumya.empl.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public Employee employeedtoToEmployee(EmployeeDTO dto){
        return new Employee(
                dto.getName(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getDob()
                );
    }
    public EmployeeDTO employeeToEmployeeDTO(Employee emp){
        return new EmployeeDTO(
                emp.getName(),
                emp.getEmail(),
                emp.getPhone(),
                emp.getDob()
        );
    }
}
