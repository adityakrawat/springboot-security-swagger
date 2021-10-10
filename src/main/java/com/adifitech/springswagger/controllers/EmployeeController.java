package com.adifitech.springswagger.controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.adifitech.springswagger.model.Employee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "basic")
@RequestMapping("/employees")
public class EmployeeController {

    private Map<String, Employee> employees = createList();

    @GetMapping
	public ResponseEntity<Collection<Employee>> getEmployees() {
		return new ResponseEntity<>(employees.values(), HttpStatus.OK);
	}

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") String id) {
        return new ResponseEntity<>(employees.get(id), HttpStatus.OK); 
    }

    @PostMapping
    public ResponseEntity<Object> addEmployee(@RequestBody Employee employee) {
		employees.put(employee.getEmpId(), employee);
		return new ResponseEntity<>("Employee created.", HttpStatus.CREATED);
	} 

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateEmployee(@PathVariable("id") String id, @RequestBody Employee employee) {
		if(employees.containsKey(id)) {
			employees.remove(id);
		}
		employees.put(employee.getEmpId(), employee);
		return new ResponseEntity<>("Employee updated.", HttpStatus.OK);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Object> updateMapping(@RequestBody Map<String, String> update, @PathVariable("id") String id) {
		Employee emp = employees.get(id);
		if(update.containsKey("designation"))
			emp.setDesignation(update.get("designation"));
		if(update.containsKey("salary"))
			emp.setSalary(Double.parseDouble(update.get("salary")));
	
		return new ResponseEntity<>("Employee "+ update.keySet().toString() + " updated.", HttpStatus.OK); 
	}

    @DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") String id) {
		if(employees.containsKey(id)) {
			employees.remove(id);
		}
		return new ResponseEntity<>("Employee deleted.", HttpStatus.OK);
	}

    private static Map<String, Employee> createList() {
		Map<String, Employee> employees = new HashMap<>();
		Employee emp1 = new Employee();
		emp1.setName("emp1");
		emp1.setDesignation("manager");
		emp1.setEmpId("1");
		emp1.setSalary(Double.valueOf(3000));
        employees.put(emp1.getEmpId(), emp1);

		Employee emp2 = new Employee();
		emp2.setName("emp2");
		emp2.setDesignation("developer");
		emp2.setEmpId("2");
		emp2.setSalary(Double.valueOf(3000));
		employees.put(emp2.getEmpId(), emp2);

        return employees;
	}
}
