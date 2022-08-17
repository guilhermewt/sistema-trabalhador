package com.projetoTrabalhador.resources;

import java.text.ParseException;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.projetoTrabalhador.entities.HourContract;
import com.projetoTrabalhador.requests.HourContractPostRequestBody;
import com.projetoTrabalhador.requests.HourContractPutRequestBody;
import com.projetoTrabalhador.service.HourContractService;
import com.projetoTrabalhador.util.HourContractCreator;
import com.projetoTrabalhador.util.HourContractPostRequestBodyCreator;
import com.projetoTrabalhador.util.HourContractPutRequestBodyCreator;

@ExtendWith(SpringExtension.class)
public class HourContractResourceTest {
	
	@InjectMocks
	private HourContractResource hourContractResource;
		
	@Mock
	private HourContractService hourContractServiceMock;
	
	@BeforeEach
	void setUp() throws ParseException {
		PageImpl<HourContract> hourContractPage = new PageImpl<>(List.of(HourContractCreator.createValidhourContract()));
		BDDMockito.when(hourContractServiceMock.findAll(ArgumentMatchers.any())).thenReturn(hourContractPage);
		
		BDDMockito.when(hourContractServiceMock.findAllNonPageable()).thenReturn(List.of(HourContractCreator.createValidhourContract()));
		
		BDDMockito.when(hourContractServiceMock.findByIdOrElseThrowResourceNotFoundException(ArgumentMatchers.anyLong()))
		.thenReturn(HourContractCreator.createValidhourContract());
		
		BDDMockito.when(hourContractServiceMock.insert(ArgumentMatchers.any(HourContractPostRequestBody.class), ArgumentMatchers.anyLong()))
		.thenReturn(HourContractCreator.createValidhourContract());
		
		BDDMockito.doNothing().when(hourContractServiceMock).update(ArgumentMatchers.any(HourContractPutRequestBody.class));
		
		BDDMockito.doNothing().when(hourContractServiceMock).delete(ArgumentMatchers.anyLong());
	}
	
	@Test
	@DisplayName("list return list of hourContract inside Page Object whenSuccessful")
	void list_ReturnListOfHourContractInsidePageObject_WhenSuccessful() throws ParseException {
		HourContract expectedHourContract = HourContractCreator.createValidhourContract();
		Page<HourContract> hourContractPage = hourContractResource.findAll(null).getBody();
		
		Assertions.assertThat(hourContractPage).isNotNull();
		Assertions.assertThat(hourContractPage.toList()).isNotEmpty().hasSize(1);
		Assertions.assertThat(hourContractPage.toList().get(0)).isEqualTo(expectedHourContract);
		
	}
	
	@Test
	@DisplayName("list return list of HourContract when successful")
	void list_ReturnListOfHourContract_whenSuccessful() throws ParseException {
		HourContract expectedHourContract = HourContractCreator.createValidhourContract();
		List<HourContract> hourContract = hourContractResource.findAllNonPageable().getBody();
		
		Assertions.assertThat(hourContract).isNotNull().hasSize(1);
		Assertions.assertThat(hourContract.get(0)).isEqualTo(expectedHourContract);
	}
	
	@Test
	@DisplayName("findById return hourContract whenSuccessful")
	void findById_ReturnHourContract_WhenSuccessful() throws ParseException {
		Long expectedId = HourContractCreator.createValidhourContract().getId();
		HourContract hourContract = this.hourContractResource.findById(1).getBody();
		
		Assertions.assertThat(hourContract).isNotNull();
		Assertions.assertThat(hourContract.getId()).isEqualTo(expectedId);
	}
	
	@Test
	@DisplayName("save return hourContract whenSuccessfull")
	void save_ReturnsHourContract_whensuccessful() throws ParseException {
		HourContract hourContract = hourContractServiceMock.insert(HourContractPostRequestBodyCreator.createHourContractPostRequestBody(),1);
		Assertions.assertThat(hourContract).isNotNull().isEqualTo(HourContractCreator.createValidhourContract());	
	}
	
	
	@Test
	@DisplayName("update replace hourContract whenSuccessful")
	void update_replaceHourContract_WhenSuccessful() throws ParseException {
		Assertions.assertThatCode(() -> hourContractResource.update(HourContractPutRequestBodyCreator.createHourContractPutRequestBody())).doesNotThrowAnyException();
		
//		ResponseEntity<Void> entity = this.hourContractResource.update(HourContractPutRequestBodyCreator.createHourContractPutRequestBody());
//	    Assertions.assertThat(entity).isNotNull();
//	    Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}
	
	@Test
	@DisplayName("delete removes hourContract whenSuccessful")
	void delete_removesHourContract_whenSuccessful() {
		Assertions.assertThatCode(() -> hourContractResource.delete(1)).doesNotThrowAnyException();
		
//		ResponseEntity<Void> entity = hourContractResource.delete(1);
//		Assertions.assertThat(entity).isNotNull();
//		Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}
	
}
