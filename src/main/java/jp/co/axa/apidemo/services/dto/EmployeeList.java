package jp.co.axa.apidemo.services.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeList {

	public List<Employee> employeeList;
	public long totalRecords;
	
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Employee {
	    private Long id;

	    private String name;

	    private Integer salary;

	    private String department;		
	}
	
}
