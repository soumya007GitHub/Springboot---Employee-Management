package com.soumya.empl.controller;

import com.soumya.empl.dto.EmployeeDTO;
import com.soumya.empl.dto.EmployeePatchDTO;
import com.soumya.empl.entity.Employee;
import com.soumya.empl.mapper.Mapper;
import com.soumya.empl.response.ApiResponse;
import com.soumya.empl.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Tag(
        name = "Employee Management APIs",
        description = "CRUD operations for managing employees"
)
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
    public ResponseEntity<?> getAllEmployees(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size){
        Page<Employee> allEmployeesFromDB = service.allEmployees(page, size);
        if(allEmployeesFromDB.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(
                            false,
                            "No Employees found",
                            null
                    )
            );
        }
        List<EmployeeDTO> allEmployees = new ArrayList<EmployeeDTO>();
        for (Employee emp : allEmployeesFromDB) {
            allEmployees.add(mapper.employeeToEmployeeDTO(emp));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        true,
                        "All Employee details",
                        allEmployees
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable Long id){
        Optional<Employee> op = service.getEmp(id);
        if(op.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(
                            false,
                            "No employee found",
                            null
                    )
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        true,
                        "Employee found",
                        mapper.employeeToEmployeeDTO(op.get())
                )
        );
    }

    @PostMapping
    public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeDTO empl){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(
                        true,
                        "Added new employee successfully",
                        mapper.employeeToEmployeeDTO(service.addEmpl(empl))
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDTO){
        Employee emp = service.updateEmpl(id, employeeDTO);
        if(emp == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(
                            false,
                            "No Employee found",
                            null
                    )
            );
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiResponse<>(
                        true,
                        "Updated employee details successfully for "+employeeDTO.getName(),
                        mapper.employeeToEmployeeDTO(emp)
                )
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchUpdateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeePatchDTO patchDTO){
        Employee emp = service.patchEmpl(id, patchDTO);
        if(emp == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(
                            false,
                            "Employee not found",
                            null
                    )
            );
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiResponse<>(
                        true,
                        "Updated employee details successfully for "+patchDTO.getName(),
                        mapper.employeeToEmployeeDTO(emp)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        if(service.deleteEmpl(id)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse<>(
                            true,
                            "Deleted employee successfully with id "+id,
                            id
                    )
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponse<>(
                        false,
                        "Failed to delete employee with id "+id,
                        id
                )
        );
    }
}
