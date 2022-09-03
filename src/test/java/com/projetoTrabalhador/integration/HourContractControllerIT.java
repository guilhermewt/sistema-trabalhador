package com.projetoTrabalhador.integration;

import java.text.ParseException;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
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

import com.projetoTrabalhador.entities.HourContract;
import com.projetoTrabalhador.entities.Worker;
import com.projetoTrabalhador.repository.HourContractRepository;
import com.projetoTrabalhador.repository.WorkerRepository;
import com.projetoTrabalhador.util.HourContractCreator;
import com.projetoTrabalhador.util.WorkerCreator;
import com.projetoTrabalhador.wrapper.PageableResponse;

@SpringBootTest( webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class HourContractControllerIT {
	
	@Autowired
	@Qualifier(value = "testRestTemplateRoleUser")
	private TestRestTemplate testRestTemplateRoleUser;
	
	@Autowired
	private HourContractRepository hourContractRepository;
	
	@Autowired
	private WorkerRepository workerRepository;
	
	private static final Worker USER = WorkerCreator.createWorkerUSER_ToBeSaved();
	
	@TestConfiguration
	@Lazy
	static class Config{
		@Bean(name = "testRestTemplateRoleUser")
		public TestRestTemplate testRestTemplateUserCreator(@Value("${local.server.port}") int port) {
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
					.rootUri("http://localhost:" + port)
					.basicAuthentication("welliston", "trabalhador");
			return new TestRestTemplate(restTemplateBuilder);
		}
	}
	
	
	@Test
	@DisplayName("list return list of hourContract inside page object whenSuccessful")
	void list_returnListOfHourContractInsidePageObject() throws ParseException {
		workerRepository.save(USER);
		
		HourContract savedhourContract = hourContractRepository.save(HourContractCreator.hourContractToBeSaved());
		
		PageableResponse<HourContract> hourContractPage = testRestTemplateRoleUser.exchange("/contracts", HttpMethod.GET,null,
				new ParameterizedTypeReference<PageableResponse<HourContract>>() {
		}).getBody();
		
		Assertions.assertThat(hourContractPage).isNotNull();
		Assertions.assertThat(hourContractPage.toList()).isNotEmpty();
		Assertions.assertThat(hourContractPage.toList().get(0)).isEqualTo(savedhourContract);		
	}
	
	
	@Test
	@DisplayName("list return list of HourContract whenSuccessful")
	void list_returnListOfHourContract_WhenSucceful() throws ParseException{
		workerRepository.save(USER);
		
		HourContract savedHourContract = hourContractRepository.save(HourContractCreator.hourContractToBeSaved());
		
		List<HourContract> hourContract = testRestTemplateRoleUser.exchange("/contracts/find", HttpMethod.GET,null,
				new ParameterizedTypeReference<List<HourContract>>() {
				}).getBody();
		
		Assertions.assertThat(hourContract).isNotNull();
		Assertions.assertThat(hourContract.get(0)).isEqualTo(savedHourContract);
	}
	
	@Test
	@DisplayName("findById return hourContract whenSuccessful")
	void findById_reeturnHourContract_WhenSuccessful() throws ParseException{
		workerRepository.save(USER);
		
		HourContract savedHourContract = hourContractRepository.save(HourContractCreator.hourContractToBeSaved());
		
		Long expectedId = savedHourContract.getId();
		
		HourContract hourContract = testRestTemplateRoleUser.getForObject("/contracts/{id}", HourContract.class,expectedId);
		
		Assertions.assertThat(hourContract).isNotNull();
		Assertions.assertThat(hourContract.getId()).isEqualTo(expectedId);
		
	}
	
	@Test
	@DisplayName("update replace hourContract whenSuccessful")
	void update_replaceHourContract_WhenSuccessful() throws ParseException{
		workerRepository.save(USER);
		
		HourContract hourContract = hourContractRepository.save(HourContractCreator.hourContractToBeSaved());
		
		hourContract = hourContractRepository.save(HourContractCreator.hourContractUpdated());
				

		ResponseEntity<Void> hourContractResponseEntity = testRestTemplateRoleUser.exchange("/contracts/", HttpMethod.PUT,
				new HttpEntity<>(hourContract),Void.class);
		
		Assertions.assertThat(hourContractResponseEntity).isNotNull();
		Assertions.assertThat(hourContractResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}
	
	@Test
	@DisplayName("delete removes hourContract whenSuccessful")
	void delete_removesHourContract_WhenSucceful() throws ParseException{
		workerRepository.save(USER);
		
		HourContract hourContract = hourContractRepository.save(HourContractCreator.hourContractToBeSaved());
		
		Long expectedId = hourContract.getId();
		
		ResponseEntity<Void> hourContractResponseEntity = testRestTemplateRoleUser.exchange("/contracts/{id}", HttpMethod.DELETE,
				null,Void.class,expectedId);
		
		Assertions.assertThat(hourContractResponseEntity).isNotNull();
		Assertions.assertThat(hourContractResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}
	
}
