package com.projetoTrabalhador.service;

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

import com.projetoTrabalhador.entities.Department;
import com.projetoTrabalhador.entities.HourContract;
import com.projetoTrabalhador.entities.Worker;
import com.projetoTrabalhador.repository.DepartmentRepository;
import com.projetoTrabalhador.repository.HourContractRepository;
import com.projetoTrabalhador.repository.WorkerRepository;
import com.projetoTrabalhador.service.exceptions.BadRequestException;
import com.projetoTrabalhador.util.DepartmentCreator;
import com.projetoTrabalhador.util.HourContractCreator;
import com.projetoTrabalhador.util.WorkerCreator;
import com.projetoTrabalhador.util.WorkerPostRequestBodyCreator;
import com.projetoTrabalhador.util.WorkerPutRequestBodyCreator;

@ExtendWith(SpringExtension.class)
public class WorkerServiceTest {
	
	@InjectMocks
	private WorkerService workerService;
	
	@Mock
	private WorkerRepository workerRepositoryMock;
	
	@Mock
	private DepartmentRepository departmentRepositoryMock;
	
	@Mock
	private HourContractRepository hourContractRepositoryMock;
	
	@BeforeEach
	void setUp() {
		
		PageImpl<Worker> workerPage = new PageImpl<>(List.of(WorkerCreator.createValidWorker()));
		BDDMockito.when(workerRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
		.thenReturn(workerPage);
		
		BDDMockito.when(workerRepositoryMock.findAll()).thenReturn(List.of(WorkerCreator.createValidWorker()));
		
		BDDMockito.when(workerRepositoryMock.findByNameContainingIgnoreCase(ArgumentMatchers.anyString()))
		.thenReturn(List.of(WorkerCreator.createValidWorker()));
		
		BDDMockito.when(workerRepositoryMock.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.of(WorkerCreator.createValidWorker()));
		
		BDDMockito.when(workerRepositoryMock.save(ArgumentMatchers.any(Worker.class)))
		.thenReturn(WorkerCreator.createValidWorker());
		
		BDDMockito.when(departmentRepositoryMock.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.of(DepartmentCreator.createValidDepartment()));
		
		BDDMockito.when(departmentRepositoryMock.save(ArgumentMatchers.any(Department.class)))
		.thenReturn(DepartmentCreator.createValidDepartment());
		
		BDDMockito.doNothing().when(workerRepositoryMock).deleteById(ArgumentMatchers.anyLong());
		
		BDDMockito.when(hourContractRepositoryMock.save(ArgumentMatchers.any(HourContract.class)))
		.thenReturn(HourContractCreator.createValidhourContract());
		
	}
	
	@Test
	@DisplayName("listAll return list of worker inside Page whenSuccessful")
	void listAll_ReturnListOfWorkerInsidePage_WhenSuccessful() {
		Worker worker = WorkerCreator.createValidWorker();
		Page<Worker> workerPage = this.workerService.findAll(PageRequest.of(1, 1));
		
		Assertions.assertThat(workerPage).isNotNull();
		Assertions.assertThat(workerPage.toList()).isNotEmpty().contains(worker);
	}
	
	@Test
	@DisplayName("findAll return list of worker whenSuccessful")
	void listAll_ReturnListOfWorker_WhenSuccessful() {
		Worker worker = WorkerCreator.createValidWorker();
		List<Worker> savedWorker = this.workerService.findNonPageable();
		
		Assertions.assertThat(savedWorker).isNotNull();
		Assertions.assertThat(savedWorker).isNotEmpty().contains(worker);
	}
	
	@Test
	@DisplayName("findByName return list of Worker whenSuccessful")
	void findByName_returnListOfWorker_WhenSuccessful() {
		Worker savedWorker = WorkerCreator.createValidWorker();
		List<Worker> worker = this.workerService.findByName(savedWorker.getName());
		
		Assertions.assertThat(worker).isNotNull().isNotEmpty();
		Assertions.assertThat(worker).contains(savedWorker);	
	}
	
	
	@Test
	@DisplayName("findById return worker whenSuccessful")
	void findById_ReturnWorker_WhenSuccessful() {
		Long expectedId = WorkerCreator.createValidWorker().getId();
		Worker savedWorker = this.workerService.findByIdOrElseThrowResourceNotFoundException(1);
		
		Assertions.assertThat(savedWorker).isNotNull();
		Assertions.assertThat(savedWorker.getId()).isEqualTo(expectedId);
	}
	
	@Test
	@DisplayName("throw BadRequestException_When Worker Is Not Found")
	void throw_BadRequestException_WhenWorkerIsNotFound() {
		BDDMockito.when(workerRepositoryMock.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.empty());
		
		Assertions.assertThatExceptionOfType(BadRequestException.class)
		.isThrownBy(() -> this.workerService.findByIdOrElseThrowResourceNotFoundException(1l));
	}

	@Test
	@DisplayName("save persist worker whenSuccessful")
	void save_persistWorkerWhenSuccessful() {
		Department department = this.departmentRepositoryMock.save(DepartmentCreator.createValidDepartment());
		Worker workerToBeSaved = WorkerCreator.createValidWorker();
		
		Worker worker = this.workerService.insert(WorkerPostRequestBodyCreator.createWorkerPostResquestBody(), department.getId());
		
		Assertions.assertThat(worker).isNotNull().isEqualTo(workerToBeSaved);	
	}
	
	@Test
	@DisplayName("delete removes worker whenSuccessful")
	void delete_removesWorker_WhenSuccessful() {	
		Assertions.assertThatCode(() -> this.workerRepositoryMock.deleteById(1l))
		.doesNotThrowAnyException();
	}
	
	@Test
	@DisplayName("replace update worker whenSuccessful")
	void replace_updateWorker_WhenSuccessful() {
		Assertions.assertThatCode(() -> this.workerService.update(WorkerPutRequestBodyCreator.createWorkerPutRequestBody()))
		.doesNotThrowAnyException();
	}
}
