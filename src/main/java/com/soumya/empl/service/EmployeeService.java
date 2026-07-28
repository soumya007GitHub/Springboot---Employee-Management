package com.soumya.empl.service;

import com.soumya.empl.dto.EmployeeDTO;
import com.soumya.empl.entity.Employee;
import com.soumya.empl.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepo repo;

    @Autowired
    public EmployeeService(EmployeeRepo repo){
        this.repo = repo;
    }
    public List<Employee> allEmployees(){
        return repo.findAll();
    }
    public Optional<Employee> getEmp(Long id){
        return repo.findById(id);
    }
    public Employee addEmpl(EmployeeDTO empl){
        return repo.save(new Employee(empl.getName(), empl.getEmail(), empl.getPhone(), empl.getDob()));
    }
    public Employee updateEmpl(Long id, Map<String, String> newDetails){
        Optional<Employee> op = repo.findById(id);
        if(op.isEmpty()){
            return null;
        }
        Employee emp = op.get();
        if(newDetails.containsKey("name")){
            emp.setName(newDetails.get("name"));
        }
        if(newDetails.containsKey("email")){
            emp.setEmail(newDetails.get("email"));
        }
        if(newDetails.containsKey("phone")){
            emp.setPhone(newDetails.get("phone"));
        }
        if(newDetails.containsKey("dob")){
            emp.setDob(LocalDate.parse(newDetails.get("dob")));
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
