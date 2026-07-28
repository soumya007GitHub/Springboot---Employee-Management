package com.soumya.empl.controller;

import com.soumya.empl.dto.EmployeeDTO;
import com.soumya.empl.dto.EmployeePatchDTO;
import com.soumya.empl.entity.Employee;
import com.soumya.empl.mapper.Mapper;
import com.soumya.empl.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/empl")
public class EmployeeController {
    private final EmployeeService service;
    private final Mapper mapper;
    @Autowired
    public EmployeeController(EmployeeService service, Mapper mapper){
        this.service = service;
        this.mapper = mapper;
    }
    @GetMapping
    public ResponseEntity<?> getAllEmployees(){
        List<Employee> allEmployeesFromDB = service.allEmployees();
        if(allEmployeesFromDB.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Employees found");
        }
        List<EmployeeDTO> allEmployees = new ArrayList<EmployeeDTO>();
        for (Employee emp : allEmployeesFromDB) {
            allEmployees.add(mapper.employeeToEmployeeDTO(emp));
        }
        return ResponseEntity.status(HttpStatus.OK).body(allEmployees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable Long id){
        Optional<Employee> op = service.getEmp(id);
        if(op.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Employee found with such id "+id);
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.employeeToEmployeeDTO(op.get()));
    }

    @PostMapping
    public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeDTO empl){
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.employeeToEmployeeDTO(service.addEmpl(empl)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDTO){
        Employee emp = service.updateEmpl(id, employeeDTO);
        if(emp == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(mapper.employeeToEmployeeDTO(emp));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchUpdateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeePatchDTO patchDTO){
        Employee emp = service.patchEmpl(id, patchDTO);
        if(emp == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(mapper.employeeToEmployeeDTO(emp));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        if(service.deleteEmpl(id)){
            return ResponseEntity.status(HttpStatus.OK).body("Deleted employee successfully with id "+id);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to delete employee with id "+id);
    }
}
