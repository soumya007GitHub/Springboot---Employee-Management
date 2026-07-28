package com.soumya.empl.service;

import com.soumya.empl.dto.EmployeeDTO;
import com.soumya.empl.dto.EmployeePatchDTO;
import com.soumya.empl.entity.Employee;
import com.soumya.empl.mapper.Mapper;
import com.soumya.empl.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepo repo;
    private final Mapper mapper;

    @Autowired
    public EmployeeService(EmployeeRepo repo, Mapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }
    public List<Employee> allEmployees(){
        return repo.findAll();
    }
    public Optional<Employee> getEmp(Long id){
        return repo.findById(id);
    }
    public Employee addEmpl(EmployeeDTO empl){
        return repo.save(mapper.employeedtoToEmployee(empl));
    }
    public Employee updateEmpl(Long id, EmployeeDTO dto){
        Optional<Employee> optional = repo.findById(id);

        if (optional.isEmpty()) {
            return null;
        }

        Employee emp = optional.get();

        emp.setName(dto.getName());
        emp.setEmail(dto.getEmail());
        emp.setPhone(dto.getPhone());
        emp.setDob(dto.getDob());

        return repo.save(emp);
    }
    public Employee patchEmpl(Long id, EmployeePatchDTO dto){
        Optional<Employee> op = repo.findById(id);
        if(op.isEmpty()){
            return null;
        }
        Employee emp = op.get();
        if(dto.getName() != null){
            emp.setName(dto.getName());
        }
        if(dto.getEmail() != null){
            emp.setEmail(dto.getEmail());
        }
        if(dto.getPhone() != null){
            emp.setPhone(dto.getPhone());
        }
        if(dto.getDob() != null){
            emp.setDob(dto.getDob());
        }

        return repo.save(emp);
    }
    public boolean deleteEmpl(Long id){
        if(!repo.existsById(id)){
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}
