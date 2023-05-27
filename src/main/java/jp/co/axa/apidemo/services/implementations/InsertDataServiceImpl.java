/**
 * 
 */
package jp.co.axa.apidemo.services.implementations;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;

import jp.co.axa.apidemo.exceptions.APIResponseException;
import jp.co.axa.apidemo.services.dto.EmployeeList;
import jp.co.axa.apidemo.services.dto.EmployeeList.Employee;

/**
 * @author Rehan Khan
 *
 */
@Service
public class InsertDataServiceImpl {

	@Autowired
	private EmployeeServiceImpl service;

	@PostConstruct
	public String run() throws APIResponseException, NoSuchAlgorithmException {
		Random r = SecureRandom.getInstanceStrong();
		String[] list = { "Business", "Customer Care", "Surrender", "Claims", "IT" };
		for (int i = 0; i < 50; i++) {
			service.saveOrUpdateEmp(new EmployeeList.Employee(null, new Faker().name().fullName(),
					r.nextInt() & Integer.MAX_VALUE, list[r.nextInt(list.length)]), "post");
		}
		return "Inserted";
	}
}
