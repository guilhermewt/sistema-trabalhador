package com.projetoTrabalhador.resources;

import java.util.Collections;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.projetoTrabalhador.entities.Worker;
import com.projetoTrabalhador.requests.WorkerPostRequestBody;
import com.projetoTrabalhador.requests.WorkerPutRequestBody;
import com.projetoTrabalhador.service.WorkerService;
import com.projetoTrabalhador.util.WorkerCreator;
import com.projetoTrabalhador.util.WorkerPostRequestBodyCreator;
import com.projetoTrabalhador.util.WorkerPutRequestBodyCreator;

@ExtendWith(SpringExtension.class)
public class WorkerResourceTest {
	
	@InjectMocks
	private WorkerResource workerResource;
	
	@Mock
	private WorkerService workerServiceMock;
	
	@BeforeEach
	void setUp() {
		PageImpl<Worker> workerPage = new PageImpl<>(List.of(WorkerCreator.createValidWorker()));
		BDDMockito.when(workerServiceMock.findAll(ArgumentMatchers.any())).thenReturn(workerPage);
		
		BDDMockito.when(workerServiceMock.findNonPageable()).thenReturn(List.of(WorkerCreator.createValidWorker()));
        
		BDDMockito.when(workerServiceMock.findByIdOrElseThrowResourceNotFoundException(ArgumentMatchers.anyLong()))
		.thenReturn(WorkerCreator.createValidWorker());
		
		BDDMockito.when(workerServiceMock.findByName(ArgumentMatchers.anyString()))
		.thenReturn(List.of(WorkerCreator.createValidWorker()));
		
		BDDMockito.when(workerServiceMock.findByName(ArgumentMatchers.anyString()))
		.thenReturn(Collections.emptyList());
		
		BDDMockito.when(workerServiceMock.insert(ArgumentMatchers.any(WorkerPostRequestBody.class), ArgumentMatchers.anyLong()))
		.thenReturn(WorkerCreator.createValidWorker());
		
		BDDMockito.doNothing().when(workerServiceMock).update(ArgumentMatchers.any(WorkerPutRequestBody.class));
		
		BDDMockito.doNothing().when(workerServiceMock).delete(ArgumentMatchers.anyLong());
				
	}
	
	@Test
	@DisplayName("list all return list of workers inside Page object whenSuccessful")
	void listAll_ReturnListOfWorkerInsidePageObject_WhenSuccessful() {
	   Worker worker = WorkerCreator.createValidWorker();
	   Page<Worker> workerPage = this.workerResource.findAll(null).getBody();
	   
	   Assertions.assertThat(workerPage).isNotNull();
	   Assertions.assertThat(workerPage.toList()).isNotEmpty().hasSize(1);
	   Assertions.assertThat(workerPage.toList()).contains(worker);	 
	}
	
	@Test
	@DisplayName("list all return list of Workers whenSuccessful")
	void listAll_returnListOfWorker_WhenSuccessful() {
		Worker worker = WorkerCreator.createValidWorker();
		List<Worker> workerList = this.workerResource.findAllNonPageable().getBody();
		
		Assertions.assertThat(workerList).isNotNull().isNotEmpty().hasSize(1);
		Assertions.assertThat(workerList.get(0)).isEqualTo(worker);	
	}
	
	@Test
	@DisplayName("findById return worker whenSuccessful")
	void findById_ReturnWorker_whenSuccessful() {
		Long expectedId = WorkerCreator.createValidWorker().getId();
		Worker worker = this.workerResource.findById(null, 1).getBody();
		
		Assertions.assertThat(worker).isNotNull();
		Assertions.assertThat(worker.getId()).isNotNull().isEqualTo(expectedId);
	}
	
	@Test
	@DisplayName("findByName return list of worker whenSuccessful")
	void findByName_ReturnListOfWorker_WhenSuccessful() {
		String expectedWorker = WorkerCreator.createValidWorker().getName();
		List<Worker> worker = this.workerResource.findByName(expectedWorker).getBody();
		
		Assertions.assertThat(worker).isNotNull().isNotEmpty().hasSize(1);
		Assertions.assertThat(worker.get(0).getName()).isEqualTo(expectedWorker);
     }
	
	@Test
	@DisplayName("findByName Return empty list of worker")
	void findByName_ReturnEmptyListOfWorker() {
		List<Worker> worker = this.workerResource.findByName("list vazia").getBody();
		
		Assertions.assertThat(worker).isNotNull().isEmpty();
	}
	
	@Test
	@DisplayName("save persist worker whenSuccessful")
	void save_persistWorker_WhenSuccessful() {
		Worker worker = WorkerCreator.createValidWorker();
		Worker savedWorker = this.workerResource.insert(WorkerPostRequestBodyCreator.createWorkerPostResquestBody(), 0).getBody();
		
		Assertions.assertThat(savedWorker).isNotNull();
		Assertions.assertThat(savedWorker).isEqualTo(worker);
	}
	
	@Test
	@DisplayName("replace update worker whenSuccessful")
	void replace_updateWorker_WhenSuccessful() {
//		Assertions.assertThatCode(() -> this.workerResource.update(WorkerPutRequestBodyCreator.createWorkerPutRequestBody()))
//		.doesNotThrowAnyException();
		
		ResponseEntity<Void> entity = this.workerResource.update(WorkerPutRequestBodyCreator.createWorkerPutRequestBody());
		
		Assertions.assertThat(entity).isNotNull();
		Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}
	
	@Test
	@DisplayName("delete removes whenSuccessful")
	void delete_removesWorkerWhenSuccessful() {
//		Assertions.assertThatCode(() -> this.workerResource.delete(1)).doesNotThrowAnyException();
		
		ResponseEntity<Void> entity = this.workerResource.delete(1);
		
		Assertions.assertThat(entity).isNotNull();
		Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}

}
