package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/rest")
public class EmployeeController {
	@Autowired
	EmployeeRepository employeeRepository;
	
	@GetMapping("/fetch/AllEmployees")
	public Flux<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}
	
	@GetMapping("/fetch/employee/{id}")
	public Mono<ResponseEntity<Employee>> getEmployee(@PathVariable("id") String id) {
		return employeeRepository.findById(id)
			     .map(ResponseEntity::ok)
			     .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}	

	@PostMapping("/create/employee")
	public Mono<Employee> addEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	@PostMapping("/update/employee/{id}")
	public Mono<ResponseEntity<Employee>> updateEmployee(@PathVariable("id") String id, @RequestBody Employee employee) {
		return employeeRepository.findById(id).flatMap(currentEmployee -> {
			currentEmployee.setCountry(employee.getCountry());
			currentEmployee.setName(employee.getName());
			currentEmployee.setHighestScore(employee.getHighestScore());
			return employeeRepository.save(currentEmployee);
		})
		.map(updatedCricketer -> new ResponseEntity<Employee>(updatedCricketer, HttpStatus.OK))
		.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@PostMapping("/delete/employee/{id}")
	public Mono<ResponseEntity<Void>> deleteEmployee(@PathVariable("id") String id) {
		return employeeRepository.deleteById(id).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)
				));
	}
	
}
