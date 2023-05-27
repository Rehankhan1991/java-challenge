package jp.co.axa.apidemo.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import jp.co.axa.apidemo.services.dto.EmployeeList;
import jp.co.axa.apidemo.services.dto.EmployeeList.Employee;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class EmployeeControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Test
	void getEmployeesRecievedTest() throws Exception {
		mvc.perform(get("/api/v1/employees")
				.header("USERNAME", "AGHFF12").header("PASSWORD", "123456")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	void getEmployeesHeadersNotRecievedTest() throws Exception {
		mvc.perform(get("/api/v1/employees"))
				.andExpect(status().is4xxClientError());
	}

	@Test
	void getEmployeesRecievedWrongUserPasswordTest() throws Exception {
		mvc.perform(get("/api/v1/employees")
				.header("USERNAME", "AGHFF12").header("PASSWORD", "12345")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	void saveEmployeeTest() throws Exception {
		EmployeeList.Employee employee =  new Employee(null, "James, Brown", 20000000, "IT");
		mvc.perform(post("/api/v1/employees/post")
				.header("USERNAME", "AGHFF12").header("PASSWORD", "123456")
				.contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(employee))).andExpect(status().isOk());
		
	}
	
	@Test
	void getEmployeeTest() throws Exception {
		mvc.perform(get("/api/v1/employees/2")
				.header("USERNAME", "AGHFF12").header("PASSWORD", "123456")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	void updateEmployeeTest() throws Exception {
		EmployeeList.Employee employee =  new Employee(25L, "James, Brown", 20000000, "IT");
		mvc.perform(put("/api/v1/employees/put")
				.header("USERNAME", "AGHFF12").header("PASSWORD", "123456")
				.contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(employee))).andExpect(status().isOk());
		
	}
	
	@Test
	void deleteEmployeeTest() throws Exception {
		mvc.perform(delete("/api/v1/employees/49/delete")
				.header("USERNAME", "AGHFF12").header("PASSWORD", "123456")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
	}

}
