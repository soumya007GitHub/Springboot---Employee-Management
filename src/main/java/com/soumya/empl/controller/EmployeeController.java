package com.soumya.empl.controller;

import com.soumya.empl.dto.EmployeeDTO;
import com.soumya.empl.entity.Employee;
import com.soumya.empl.service.EmployeeService;
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
    @Autowired
    public EmployeeController(EmployeeService service){
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<?> getAllEmployees(){
        List<Employee> allEmployeesFromDB = service.allEmployees();
        if(allEmployeesFromDB.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Employees found");
        }
        List<EmployeeDTO> allEmployees = new ArrayList<EmployeeDTO>();
        for (Employee emp : allEmployeesFromDB) {
            allEmployees.add(new EmployeeDTO(emp.getName(), emp.getEmail(), emp.getPhone(), emp.getDob()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(allEmployees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable Long id){
        Optional<Employee> op = service.getEmp(id);
        if(op.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Employee found with such id "+id);
        }
        Employee employeeFromDB = op.get();
        return ResponseEntity.status(HttpStatus.OK).body(new EmployeeDTO(employeeFromDB.getName(), employeeFromDB.getEmail(), employeeFromDB.getPhone(), employeeFromDB.getDob()));
    }

    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody Map<String, String> details){
        if(details.isEmpty() || !details.containsKey("name") || !details.containsKey("email") || !details.containsKey("phone") || !details.containsKey("dob")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All fields(Name, Email, Phone, DOB) are mandatory");
        }
        Employee emp = service.addEmpl(new EmployeeDTO(details.get("name"), details.get("email"), details.get("phone"), LocalDate.parse(details.get("dob"))));
        return ResponseEntity.status(HttpStatus.CREATED).body(new EmployeeDTO(emp.getName(), emp.getEmail(), emp.getPhone(), emp.getDob()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody Map<String, String> newDetails){
        if(newDetails.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request body can't be empty");
        }
        Employee emp = service.updateEmpl(id, newDetails);
        if(emp == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new EmployeeDTO(emp.getName(), emp.getEmail(), emp.getPhone(), emp.getDob()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        if(service.deleteEmpl(id)){
            return ResponseEntity.status(HttpStatus.OK).body("Deleted employee successfully with id "+id);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to delete employee with id "+id);
    }
}
