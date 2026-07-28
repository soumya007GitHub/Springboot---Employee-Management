package com.soumya.empl.service;

import com.soumya.empl.dto.EmployeeDTO;
import com.soumya.empl.dto.EmployeePatchDTO;
import com.soumya.empl.entity.Employee;
import com.soumya.empl.mapper.Mapper;
import com.soumya.empl.repository.EmployeeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepo repo;
    private final Mapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    public EmployeeService(EmployeeRepo repo, Mapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }
    public List<Employee> allEmployees(){
        logger.info("Fetched all employee details");
        return repo.findAll();
    }
    public Optional<Employee> getEmp(Long id){
        Optional<Employee> emp = repo.findById(id);
        if(emp.isEmpty()){
            logger.warn("No employee found with ID: {}", id);
            return emp;
        }
        logger.warn("Found employee with ID: {} AND EMAIL: {}", emp.get().getId(), emp.get().getEmail());
        return emp;
    }
    public Employee addEmpl(EmployeeDTO empl){
        Employee e = repo.save(mapper.employeedtoToEmployee(empl));
        logger.info("Employee created with ID: {}, EMAIL: {}", e.getId(), e.getEmail());
        return e;
    }
    public Employee updateEmpl(Long id, EmployeeDTO dto){
        Optional<Employee> optional = repo.findById(id);

        if (optional.isEmpty()) {
            logger.warn("No employee found with EMAIL: {}", dto.getEmail());
            return null;
        }

        Employee emp = optional.get();

        emp.setName(dto.getName());
        emp.setEmail(dto.getEmail());
        emp.setPhone(dto.getPhone());
        emp.setDob(dto.getDob());
        logger.info("Added employee with ID: {}, EMAIL: {}", emp.getId(), emp.getEmail());
        return repo.save(emp);
    }
    public Employee patchEmpl(Long id, EmployeePatchDTO dto){
        Optional<Employee> op = repo.findById(id);
        if(op.isEmpty()){
            logger.warn("No employee found with EMAIL: {}", dto.getEmail());
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
        logger.info("Updated details for employee with ID: {} and EMAIL: {}", emp.getId(), emp.getEmail());
        return repo.save(emp);
    }
    public boolean deleteEmpl(Long id){
        if(!repo.existsById(id)){
            logger.warn("No employee found with ID: {}", id);
            return false;
        }
        repo.deleteById(id);
        logger.info("Deleted employee with ID: {}", id);
        return true;
    }
}
