package com.employee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.entity.Employee;
import com.employee.exception.ResourceNotFound;
import com.employee.repository.EmployeeRepository;
import java.util.Optional;
@RestController
//@RequestMapping("/api/v1")
@CrossOrigin(origins="http://localhost:3000")
public class EmployeeController 
{
 @Autowired
  public EmployeeRepository repository;
 
 @GetMapping("/employees")
 public List<Employee>getAllEmployee()
 {
	 return repository.findAll();
 }
 @PostMapping("/employees")
 public Employee saveEmployee(@RequestBody Employee employee)
 {
	 return repository.save(employee);
 }
 /*@GetMapping("/employees/{id}")
 public ResponseEntity<Employee> getEmployeeById(@PathVariable int id)
 {
	 Employee employee=repository.findById(id).orElseThrow(()-> new ResourceNotFound("No rocord found with id"+id));
	 return ResponseEntity.ok(employee);
 }*/
 @GetMapping("/employees/{id}")
 public Optional<Employee> getEmployeeById(@PathVariable int id)
 {
	return repository.findById(id);
	
 }
 /*@PutMapping("employees/{id}")
 public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee employee)
 {
	 Employee employee2=repository.findById(id).orElseThrow(()-> new ResourceNotFound("No rocord found with id"+id));
	 employee2.setName(employee.getName());
	 employee2.setSal(employee.getSal());
	 Employee updateEmployee=repository.save(employee2);
	 return ResponseEntity.ok(updateEmployee);
 }*/
 @PutMapping("employees/{id}")
 Employee updateEmployee(@RequestBody Employee newEmployee, @PathVariable Integer id) 
 {
 return repository.findById(id).map(employee -> {
	  		employee.setName(newEmployee.getName());
	  		employee.setSal(newEmployee.getSal());
	  		return repository.save(employee);})
 .orElseGet(() -> { 
	  newEmployee.setId(id); 
     return repository.save(newEmployee); });
 }

 
 /*@DeleteMapping("employees/{id}")
 public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable int id)
 {
	Employee employee=repository.findById(id).orElseThrow(()-> new ResourceNotFound("No rocord found with id"+id));
	repository.delete(employee);
	Map<String,Boolean>response=new HashMap<>();
	response.put("deleted", Boolean.TRUE);
	return ResponseEntity.ok(response);
 }*/
 @DeleteMapping("employees/{id}")
	void deleteEmployee(@PathVariable Integer id) {
		System.out.println("deleteById...");
		repository.deleteById(id);
	}

 
}
