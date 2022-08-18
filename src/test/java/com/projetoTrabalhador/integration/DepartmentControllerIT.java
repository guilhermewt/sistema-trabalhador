package com.projetoTrabalhador.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import com.projetoTrabalhador.entities.Department;
import com.projetoTrabalhador.repository.DepartmentRepository;
import com.projetoTrabalhador.util.DepartmentCreator;
import com.projetoTrabalhador.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class DepartmentControllerIT {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	
	@Test
	@DisplayName("List return list of department inside page object when successful")
	void list_ReturnsListOfDepartmentInsidePageObject_whenSuccessful() {
		Department savedDepartment = departmentRepository.save(DepartmentCreator.createDepartmentToBeSaved());
		
		String expectedName = savedDepartment.getName();
		
		PageableResponse<Department> departmentPage = testRestTemplate.exchange("/departments", HttpMethod.GET,null,
				new ParameterizedTypeReference<PageableResponse<Department>>() {
				}).getBody();
		
		Assertions.assertThat(departmentPage).isNotNull();
		
		Assertions.assertThat(departmentPage.toList()).isNotEmpty();
		
		Assertions.assertThat(departmentPage.toList().get(0).getName()).isEqualTo(expectedName);
		
	}
}
