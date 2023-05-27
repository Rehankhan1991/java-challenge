package jp.co.axa.apidemo.services.implementations;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.javafaker.Faker;

import jp.co.axa.apidemo.exceptions.APIResponseException;
import jp.co.axa.apidemo.exceptions.ValidationException;
import jp.co.axa.apidemo.services.dto.EmployeeList;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeServiceImplTest {

	@Autowired
	private EmployeeServiceImpl service;

	@Test
	@Order(1)
	void retrieveEmployeesTest() {
		// Fetching With Limit 10 offset 0
		EmployeeList list = service.retrieveEmployees(0, 10);
		assertNotNull(list);
		assertEquals(10, list.employeeList.size());
		assertTrue(50<=list.totalRecords);
		// Fetching With Limit 20 offset 0
		assertEquals(20, service.retrieveEmployees(0, 20).employeeList.size());
		// Fetching next page Limit 10 offset 10
		assertEquals(10, service.retrieveEmployees(10, 10).employeeList.size());
	}

	@Test
	@Order(2)
	void getEmployeeTest() {
		EmployeeList.Employee emp = service.getEmployee(1L);
		assertNotNull(emp);
		assertEquals(Long.valueOf(1), emp.getId());
		assertNotNull(emp.getName());
		assertNotNull(emp.getSalary());
		assertNotNull(emp.getDepartment());
	}

	@Test
	@Order(3)
	void getEmployeeThrowExceptionTest() {
		assertThrows(APIResponseException.class, () -> service.getEmployee(250L));
	}

	@Test
	@Order(4)
	void saveEmpTest() {
		EmployeeList list = service.retrieveEmployees(0, 10);
		assertNotNull(list);
		assertTrue(50<=list.totalRecords);
		assertDoesNotThrow(() -> service.saveOrUpdateEmp(
				new EmployeeList.Employee(null, new Faker().name().fullName(), 20000000, "IT"), "post"));
		assertTrue(51<=service.retrieveEmployees(0, 10).totalRecords);
	}

	@Test
	@Order(5)
	void updateEmpTest() {
		EmployeeList list = service.retrieveEmployees(0, 10);
		assertNotNull(list);
		assertTrue(50<=list.totalRecords);
		assertDoesNotThrow(() -> service
				.saveOrUpdateEmp(new EmployeeList.Employee(25L, new Faker().name().fullName(), 20000000, "IT"), "put"));
		assertEquals(list.totalRecords, service.retrieveEmployees(0, 10).totalRecords);
	}

	@Test
	@Order(6)
	void updateEmpTestNoIdPassedThrowsException() {
		assertThatExceptionOfType(ValidationException.class).describedAs("id Cannot Be null").isThrownBy(
				() -> service.saveOrUpdateEmp(new EmployeeList.Employee(null, new Faker().name().fullName(), 20000000, "IT"),
						"put"));
	}

	@Test
	@Order(7)
	void updateEmpTestNoRequestBodyPassedThrowsException() {
		assertThatExceptionOfType(ValidationException.class).as("RequestBody Cannot Be null").isThrownBy(
				() -> service.saveOrUpdateEmp(null, "put"));
	}
	
	@Test
	@Order(8)
	void deleteEmpTest() {
		EmployeeList list = service.retrieveEmployees(0, 10);
		assertNotNull(list);
		assertTrue(50<=list.totalRecords);
		assertDoesNotThrow(() -> service.deleteEmployee(1L));
		assertTrue(list.totalRecords > service.retrieveEmployees(0, 10).totalRecords);
	}
}
