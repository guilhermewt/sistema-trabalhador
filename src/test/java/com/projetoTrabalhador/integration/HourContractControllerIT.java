package com.projetoTrabalhador.integration;

import java.text.ParseException;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.projetoTrabalhador.entities.HourContract;
import com.projetoTrabalhador.repository.HourContractRepository;
import com.projetoTrabalhador.util.HourContractCreator;
import com.projetoTrabalhador.wrapper.PageableResponse;

@SpringBootTest( webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class HourContractControllerIT {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private HourContractRepository hourContractRepository;
	
	
	@Test
	@DisplayName("list return list of hourContract inside page object whenSuccessful")
	void list_returnListOfHourContractInsidePageObject() throws ParseException {
		HourContract savedhourContract = HourContractCreator.createValidhourContract();
		
		PageableResponse<HourContract> hourContractPage = testRestTemplate.exchange("/contracts", HttpMethod.GET,null,
				new ParameterizedTypeReference<PageableResponse<HourContract>>() {
		}).getBody();
		
		Assertions.assertThat(hourContractPage).isNotNull();
		Assertions.assertThat(hourContractPage.toList()).isNotEmpty();
		Assertions.assertThat(hourContractPage.toList().get(0)).isEqualTo(savedhourContract);		
	}
	
	
	@Test
	@DisplayName("list return list of HourContract whenSuccessful")
	void list_returnListOfHourContract_WhenSucceful() throws ParseException{
		HourContract savedHourContract = HourContractCreator.createValidhourContract();
		
		List<HourContract> hourContract = testRestTemplate.exchange("/contracts/find", HttpMethod.GET,null,
				new ParameterizedTypeReference<List<HourContract>>() {
				}).getBody();
		
		Assertions.assertThat(hourContract).isNotNull();
		Assertions.assertThat(hourContract.get(0)).isEqualTo(savedHourContract);
	}
	
	@Test
	@DisplayName("findById return hourContract whenSuccessful")
	void findById_reeturnHourContract_WhenSuccessful() throws ParseException{
		Long expectedId = HourContractCreator.createValidhourContract().getId();
		
		HourContract hourContract = testRestTemplate.getForObject("/contracts/{id}", HourContract.class,expectedId);
		
		Assertions.assertThat(hourContract).isNotNull();
		Assertions.assertThat(hourContract.getId()).isEqualTo(expectedId);
		
	}
	
	@Test
	@DisplayName("update replace hourContract whenSuccessful")
	void update_replaceHourContract_WhenSuccessful() throws ParseException{
		HourContract savedHourContract = HourContractCreator.hourContractUpdated();
		
		ResponseEntity<Void> hourContractResponseEntity = testRestTemplate.exchange("/contracts/", HttpMethod.PUT,
				new HttpEntity<>(savedHourContract),Void.class);
		
		Assertions.assertThat(hourContractResponseEntity).isNotNull();
		Assertions.assertThat(hourContractResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}
	
	@Test
	@DisplayName("delete removes hourContract whenSuccessful")
	void delete_removesHourContract_WhenSucceful() throws ParseException{
		Long expectedId = HourContractCreator.createValidhourContract().getId();
		
		ResponseEntity<Void> hourContractResponseEntity = testRestTemplate.exchange("/contracts/{id}", HttpMethod.DELETE,
				null,Void.class,expectedId);
		
		Assertions.assertThat(hourContractResponseEntity).isNotNull();
		Assertions.assertThat(hourContractResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}
}
