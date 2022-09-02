package com.projetoTrabalhador.integration;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.projetoTrabalhador.entities.Department;
import com.projetoTrabalhador.entities.Worker;
import com.projetoTrabalhador.repository.DepartmentRepository;
import com.projetoTrabalhador.repository.WorkerRepository;
import com.projetoTrabalhador.requests.DepartmentPostRequestBody;
import com.projetoTrabalhador.util.DepartmentCreator;
import com.projetoTrabalhador.util.DepartmentPostRequestBodyCreator;
import com.projetoTrabalhador.util.WorkerCreator;
import com.projetoTrabalhador.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DepartmentControllerIT {
	
	@Autowired
	@Qualifier(value = "testRestTemplateRoleUser")
	private TestRestTemplate testRestTemplateRoleUser;
	
	@Autowired
	@Qualifier(value = "testRestTemplateRoleAdmin")
	private TestRestTemplate testRestTemplateRoleAdmin;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private WorkerRepository workerRepository;
	
	private static final Worker USER = WorkerCreator.createWorkerUSER_ToBeSaved();
	
	private static final Worker ADMIN = WorkerCreator.createWorkerADMIN_ToBeSaved();
	
	@TestConfiguration
	@Lazy
	static class Config {
		
		@Bean(name = "testRestTemplateRoleUser")
		public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port) {
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
					.rootUri("http://localhost:" + port)
					.basicAuthentication("welliston", "trabalhador");
			return new TestRestTemplate(restTemplateBuilder);
		}
		
		@Bean(name = "testRestTemplateRoleAdmin")
		public TestRestTemplate testRestTemplateRoleAdminCreator(@Value("${local.server.port}") int port) {
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
					.rootUri("http://localhost:" + port)
					.basicAuthentication("gui", "trabalhador");
			return new TestRestTemplate(restTemplateBuilder);
		}
	
	}
	
	
	@Test
	@DisplayName("List return list of department inside page object when successful")
	void list_ReturnsListOfDepartmentInsidePageObject_whenSuccessful() {
		workerRepository.save(USER);
		
		Department savedDepartment = departmentRepository.save(DepartmentCreator.createDepartmentToBeSaved());
		
		String expectedName = savedDepartment.getName();
		
		PageableResponse<Department> departmentPage = testRestTemplateRoleUser.exchange("/departments", HttpMethod.GET,null,
				new ParameterizedTypeReference<PageableResponse<Department>>() {
		}).getBody();
		
		Assertions.assertThat(departmentPage).isNotNull();
		
		Assertions.assertThat(departmentPage.toList()).isNotEmpty();
		
		Assertions.assertThat(departmentPage.toList().get(0).getName()).isEqualTo(expectedName);
		
	}
	
	@Test
	@DisplayName("ListAll return list of department when successful")
	void listAll_ReturnsListOfDepartment_whenSuccessful() {
		workerRepository.save(USER);
		
		Department savedDepartment = departmentRepository.save(DepartmentCreator.createDepartmentToBeSaved());
	      String expectedName = savedDepartment.getName();

	    List<Department> department = testRestTemplateRoleUser.exchange("/departments/all", HttpMethod.GET,null,
					new ParameterizedTypeReference<List<Department>>() {
					}).getBody();

	    Assertions.assertThat(department).isNotNull().isNotEmpty();
	     Assertions.assertThat(department.get(0).getName()).isEqualTo(expectedName);
	}
	
	@Test
	@DisplayName("findById return department when successful")
	void findById_ReturnsDepartment_whenSuccessful() {
		workerRepository.save(USER);
		
		Department savedDepartment = departmentRepository.save(DepartmentCreator.createDepartmentToBeSaved());
	    Long expectedId = savedDepartment.getId();

	    Department department = testRestTemplateRoleUser.getForObject("/departments/{id}", Department.class, expectedId);
	    Assertions.assertThat(department).isNotNull();
	    Assertions.assertThat(department.getId()).isNotNull().isEqualTo(expectedId);
	}
	
	@Test
	@DisplayName("findByName return list of department when successful")
	void findByName_ReturnsDepartment_whenSuccessful() {
		workerRepository.save(USER);
		
		Department savedDepartment = departmentRepository.save(DepartmentCreator.createDepartmentToBeSaved());
		
        String expectedName = savedDepartment.getName();
		
        String url = String.format("/departments/find?name=%s", expectedName);
        
        List<Department> department = testRestTemplateRoleUser.exchange(url, HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Department>>() {
				}).getBody();
		
		Assertions.assertThat(department)
		.isNotNull()
		.isNotEmpty();
		
		Assertions.assertThat(department.get(0).getName()).isEqualTo(expectedName);
	}
	
	@Test
	@DisplayName("findByName return an empty list of department when name is not found")
	void findByName_ReturnsEmptyListOfDepartment_whenDepartmentIsNotFound() {
		workerRepository.save(USER);
        
        List<Department> department = testRestTemplateRoleUser.exchange("/departments/find?name=cpu", HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Department>>() {
				}).getBody();
		
		Assertions.assertThat(department).isNotNull().isEmpty();
		
	}
	
	@Test
	@DisplayName("save return department when successful")
	void save_ReturnsDepartment_whenSuccessful() {
		workerRepository.save(USER);

		DepartmentPostRequestBody departmentPostRequestBody = DepartmentPostRequestBodyCreator.createDepartmentPostRequestBody();
		
		ResponseEntity<Department> departmentResponseEntity = testRestTemplateRoleUser.postForEntity("/departments", departmentPostRequestBody, Department.class);
		
		Assertions.assertThat(departmentResponseEntity).isNotNull();
		Assertions.assertThat(departmentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		Assertions.assertThat(departmentResponseEntity.getBody()).isNotNull();
		Assertions.assertThat(departmentResponseEntity.getBody().getId()).isNotNull();	
	}
	
	@Test
	@DisplayName("replace update department when successful")
	void replace_UpdateDepartment_whenSuccessful() {
		workerRepository.save(USER);
		
		Department savedDepartment = departmentRepository.save(DepartmentCreator.createDepartmentToBeSaved());
		
		savedDepartment.setName("new department");
		
		ResponseEntity<Void> departmentResponseEntity = testRestTemplateRoleUser.exchange("/departments/", HttpMethod.PUT,
				new HttpEntity<>(savedDepartment),Void.class);
		
		Assertions.assertThat(departmentResponseEntity).isNotNull();
		Assertions.assertThat(departmentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}
	
	@Test
	@DisplayName("delete removes department when successful")
	void delete_RemovesDepartment_whenSuccessful() {
		workerRepository.save(ADMIN);
		
        Department savedDepartment = departmentRepository.save(DepartmentCreator.createDepartmentToBeSaved());
		
		ResponseEntity<Void> departmentResponseEntity = testRestTemplateRoleAdmin.exchange("/departments/admin/{id}", HttpMethod.DELETE,
				null,Void.class,savedDepartment.getId());
		
		Assertions.assertThat(departmentResponseEntity).isNotNull();
		Assertions.assertThat(departmentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		
	}
	
	@Test
	@DisplayName("delete Return 403 when User Is Not Admin")
	void delete_Return403_whenUserIsNotAdmin() {
		workerRepository.save(USER);
		
        Department savedDepartment = departmentRepository.save(DepartmentCreator.createDepartmentToBeSaved());
		
		ResponseEntity<Void> departmentResponseEntity = testRestTemplateRoleUser.exchange("/departments/admin/{id}", HttpMethod.DELETE,
				null,Void.class,savedDepartment.getId());
		
		Assertions.assertThat(departmentResponseEntity).isNotNull();
		Assertions.assertThat(departmentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
		
	}
	
	
}
