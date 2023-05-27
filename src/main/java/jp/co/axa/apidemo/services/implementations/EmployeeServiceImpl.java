package jp.co.axa.apidemo.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exceptions.APIResponseException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.services.EmployeeService;
import jp.co.axa.apidemo.services.dto.EmployeeList;
import jp.co.axa.apidemo.utils.Validate;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final CacheManager cacheManager;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository, CacheManager cacheManager) {
		this.employeeRepository = employeeRepository;
		this.cacheManager = cacheManager;
	}

	@Cacheable(value = "employees")
	public EmployeeList retrieveEmployees(int offset, int limit) {

		// To judge how many pages to iterate for total records
		Long totalEmployees = employeeRepository.count();

		// Fetching data with limit to avoid big responses
		// we can decide our own limit considering resources
		Optional<List<Employee>> employees = employeeRepository.findEmployeesWithLimit(offset, limit);

		return new EmployeeList(convertMultiToUI(employees.orElse(new ArrayList<>())), totalEmployees);
	}

	@Cacheable(value = "employee")
	public EmployeeList.Employee getEmployee(Long employeeId) {
		Optional<Employee> optEmp = employeeRepository.findById(employeeId);
		if (!optEmp.isPresent()) {
			throw new APIResponseException(HttpStatus.NOT_FOUND, "Data Not Found for employeeId " + employeeId);
		}
		return convertSingleToUI(optEmp.get());
	}

	@Caching(evict = { @CacheEvict(value = "employee", allEntries = true),
			@CacheEvict(value = "employees", allEntries = true) })
	public void saveOrUpdateEmp(EmployeeList.Employee employee, String methodType) {
		// Validate and throw exception if Payload is null
		Validate.checkPayload(employee, "RequestBody");
		if("put".equals(methodType)) {
			// Validate Id is null in case of update
			Validate.checkPayload(employee.getId(), "Id");
		}

		if (!StringUtils.isEmpty(employee.getId())) {
			// An Exception will be thrown if no Record found by employeeId
			getEmployee(employee.getId());
		}
		
		// If no exception thrown update or save employee after data validation
		employeeRepository.save(convertSingleToDB(employee));
	}

	@Caching(evict = { @CacheEvict(value = "employee", allEntries = true),
			@CacheEvict(value = "employees", allEntries = true) })
	public void deleteEmployee(Long employeeId) {
		employeeRepository.deleteById(employeeId);
	}

	// Convert To UI Friendly Response
	private EmployeeList.Employee convertSingleToUI(Employee emp) {
		return EmployeeList.Employee.builder().id(emp.getId()).name(emp.getName()).salary(emp.getSalary())
				.department(emp.getDepartment()).build();
	}

	// Convert To UI Friendly Response
	private List<EmployeeList.Employee> convertMultiToUI(List<Employee> list) {
		return list.stream().map(e -> EmployeeList.Employee.builder().id(e.getId()).name(e.getName())
				.salary(e.getSalary()).department(e.getDepartment()).build()).collect(Collectors.toList());
	}

	// Convert To integration layer specifications
	private Employee convertSingleToDB(EmployeeList.Employee employee) {
		return Employee.builder().id(employee.getId()).name(employee.getName()).salary(employee.getSalary())
				.department(employee.getDepartment()).build();
	}
	
	@Scheduled(fixedRate = 60*1000)
	public void evictAllCaches() {
	    cacheManager.getCacheNames().stream()
	      .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
	}
}