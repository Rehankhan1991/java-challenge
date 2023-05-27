package jp.co.axa.apidemo.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jp.co.axa.apidemo.services.EmployeeService;
import jp.co.axa.apidemo.services.dto.EmployeeList;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/employees")
@Slf4j
public class EmployeeController {

	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	// To retrieve employees from DataBase with limit 10 records per page
	@GetMapping
	@Operation(summary = "Get Employees", description = "Fetch Employees object with pagination")
	public Response getEmployees(@RequestParam(required = false, defaultValue = "1") int page) {
		log.info("EmployeeController: getAllEmployees");
		final int limit = 10;
		final int offset = (page - 1) * limit;
		// Yields multiple response
		EmployeeList employees = employeeService.retrieveEmployees(offset, limit);
		return Response.newResponse(employees.getEmployeeList(), offset, limit, employees.totalRecords);
	}

	@GetMapping("/{employeeId}")
	@Operation(summary = "Get Employee", description = "Fetch Employee Detail object with ID")
	public Response<EmployeeList.Employee> getEmployee(@PathVariable(name = "employeeId") Long employeeId) {
		log.info("EmployeeController: getEmployee");
		// Yields single response
		return Response.newResponse(employeeService.getEmployee(employeeId));
	}

	@PostMapping("/post")
	@Operation(summary = "Save Employee", description = "Save Employee Detail")
	public void saveEmployee(EmployeeList.Employee employee) {
		log.info("EmployeeController: saveEmployee");
		employeeService.saveOrUpdateEmp(employee, "post");
		log.info("Employee Saved Successfully");
	}

	@DeleteMapping("/{employeeId}/delete")
	@Operation(summary = "Delete Employee", description = "Delete Employee Detail")
	public void deleteEmployee(@PathVariable(name = "employeeId") Long employeeId) {
		log.info("EmployeeController: deleteEmployee");
		employeeService.deleteEmployee(employeeId);
		log.info("Employee Deleted Successfully");
	}

	@PutMapping("/put")
	@Operation(summary = "Update Employee", description = "Update Employee Detail")
	public void updateEmployee(@RequestBody EmployeeList.Employee employee) {
		log.info("EmployeeController: updateEmployee");
		employeeService.saveOrUpdateEmp(employee, "put");
		log.info("Employee updated Successfully");
	}

}
