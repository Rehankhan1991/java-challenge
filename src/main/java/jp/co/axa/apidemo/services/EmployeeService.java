package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.services.dto.EmployeeList;
import jp.co.axa.apidemo.services.dto.EmployeeList.Employee;

public interface EmployeeService {

    public EmployeeList retrieveEmployees(int offset, int limit);

    public Employee getEmployee(Long employeeId);

    public void deleteEmployee(Long employeeId);

    public void saveOrUpdateEmp(Employee employee, String method);
}