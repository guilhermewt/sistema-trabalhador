package com.projetoTrabalhador.service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.projetoTrabalhador.entities.HourContract;
import com.projetoTrabalhador.entities.Worker;
import com.projetoTrabalhador.repository.HourContractRepository;
import com.projetoTrabalhador.repository.WorkerRepository;
import com.projetoTrabalhador.service.exceptions.BadRequestException;
import com.projetoTrabalhador.util.HourContractCreator;
import com.projetoTrabalhador.util.HourContractPostRequestBodyCreator;
import com.projetoTrabalhador.util.HourContractPutRequestBodyCreator;
import com.projetoTrabalhador.util.WorkerCreator;

@ExtendWith(SpringExtension.class)
public class HourContractServiceTest {
	
	@InjectMocks
	private HourContractService hourContractService;
	
	@Mock
	private HourContractRepository hourContractRepositoryMock;
	
	@Mock
	private WorkerRepository workerRepositoryMock;
	
	@BeforeEach
	void setUp() throws ParseException {
		PageImpl<HourContract> hourContractPage = new PageImpl<>(List.of(HourContractCreator.createValidhourContract()));
		BDDMockito.when(hourContractRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(hourContractPage);
		
		BDDMockito.when(hourContractRepositoryMock.findAll()).thenReturn(List.of(HourContractCreator.createValidhourContract()));
		
		BDDMockito.when(hourContractRepositoryMock.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.of(HourContractCreator.createValidhourContract()));
		
		BDDMockito.when(hourContractRepositoryMock.save(ArgumentMatchers.any(HourContract.class)))
		.thenReturn(HourContractCreator.createValidhourContract());
		
		BDDMockito.doNothing().when(hourContractRepositoryMock).delete(ArgumentMatchers.any(HourContract.class));
		
		BDDMockito.when(workerRepositoryMock.findById(1l))
		.thenReturn(Optional.of(WorkerCreator.createValidWorker()));
		
		BDDMockito.when(workerRepositoryMock.save(ArgumentMatchers.any(Worker.class)))
		.thenReturn(WorkerCreator.createValidWorker());
		
	}
	
	@Test
	@DisplayName("list return list of hourContract inside Page Object whenSuccessful")
	void list_ReturnListOfHourContractInsidePageObject_WhenSuccessful() throws ParseException {
		HourContract expectedHourContract = HourContractCreator.createValidhourContract();
		Page<HourContract> hourContractPage = hourContractService.findAll(PageRequest.of(1, 1));
		
		Assertions.assertThat(hourContractPage).isNotNull();
		Assertions.assertThat(hourContractPage.toList()).isNotEmpty().hasSize(1);
		Assertions.assertThat(hourContractPage.toList().get(0)).isEqualTo(expectedHourContract);
		
	}
	
	@Test
	@DisplayName("list return list of HourContract when successful")
	void list_ReturnListOfHourContract_whenSuccessful() throws ParseException {
		HourContract expectedHourContract = HourContractCreator.createValidhourContract();
		List<HourContract> hourContract = hourContractService.findAllNonPageable();
		
		Assertions.assertThat(hourContract).isNotNull().hasSize(1);
		Assertions.assertThat(hourContract.get(0)).isEqualTo(expectedHourContract);
	}
	
	@Test
	@DisplayName("findById return hourContract whenSuccessful")
	void findById_ReturnHourContract_WhenSuccessful() throws ParseException {
		Long expectedId = HourContractCreator.createValidhourContract().getId();
		HourContract hourContract = this.hourContractService.findByIdOrElseThrowResourceNotFoundException(1);
		
		Assertions.assertThat(hourContract).isNotNull();
		Assertions.assertThat(hourContract.getId()).isEqualTo(expectedId);
	}
	@Test
	@DisplayName("throw BadRequestException when hourContract is not found")
	void findByIdOrElseThrowBadRequestException_throwBadRequestException_whenSuccessful() {
		BDDMockito.when(hourContractRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
		Assertions.assertThatExceptionOfType(BadRequestException.class)
		.isThrownBy(() -> hourContractService.findByIdOrElseThrowResourceNotFoundException(1));
		
	}
	
	@Test
	@DisplayName("save return hourContract whenSuccessfull")
	void save_ReturnsHourContract_whensuccessful() throws ParseException {
		Worker worker = workerRepositoryMock.save(WorkerCreator.createValidWorker());		
		HourContract hourContract = hourContractService.insert(HourContractPostRequestBodyCreator.createHourContractPostRequestBody(),worker.getId());
		Assertions.assertThat(hourContract).isNotNull().isEqualTo(HourContractCreator.createValidhourContract());	
	}
	
	@Test
	@DisplayName("update replace hourContract whenSuccessful")
	void update_replaceHourContract_WhenSuccessful() throws ParseException {
		Assertions.assertThatCode(() -> hourContractService.update(HourContractPutRequestBodyCreator.createHourContractPutRequestBody()))
		.doesNotThrowAnyException();
	}
	
	@Test
	@DisplayName("delete removes hourContract whenSuccessful")
	void delete_removesHourContract_whenSuccessful() {
		Assertions.assertThatCode(() -> hourContractService.delete(1l));		
	}
	
}
