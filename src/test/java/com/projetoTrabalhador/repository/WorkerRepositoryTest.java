package com.projetoTrabalhador.repository;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.projetoTrabalhador.entities.Worker;
import com.projetoTrabalhador.util.WorkerCreator;

@DataJpaTest
@DisplayName("test for worker repository")
public class WorkerRepositoryTest {
	
	@Autowired
	private WorkerRepository workerRepository;
	
	
	@Test
	@DisplayName("save persist Worker whenSuccessful")
	void save_persistWorker_WhenSuccessful() {
		Worker worker = WorkerCreator.createWorkerADMIN_ToBeSaved();
		Worker savedWorker = this.workerRepository.save(worker);
		
		Assertions.assertThat(savedWorker).isNotNull();
		Assertions.assertThat(savedWorker.getId()).isNotNull();
		Assertions.assertThat(savedWorker.getName()).isEqualTo(worker.getName());
	}
	
	@Test
	@DisplayName("update replace worker whenSuccessful")
	void update_replaceWorker_WhenSuccessful() {
		Worker worker = WorkerCreator.createWorkerADMIN_ToBeSaved();
		Worker savedWorker = this.workerRepository.save(worker);
		
		savedWorker = WorkerCreator.createValidUpdatedWorker();
		
		Worker updatedWorker = this.workerRepository.save(savedWorker);
		
		Assertions.assertThat(updatedWorker).isNotNull();
		Assertions.assertThat(updatedWorker.getId()).isNotNull();
        Assertions.assertThat(updatedWorker).isEqualTo(savedWorker);
	
	}
	
	@Test
	@DisplayName("delete removes worker whenSuccessful")
	void delete_removesWorker_WhenSuccessful() {
		Worker savedWorker = this.workerRepository.save(WorkerCreator.createWorkerADMIN_ToBeSaved());
		
		this.workerRepository.delete(savedWorker);
		
		Optional<Worker> workerDeleted = this.workerRepository.findById(savedWorker.getId());
		
		Assertions.assertThat(workerDeleted).isEmpty();
	}
	
	@Test
	@DisplayName("findbyname return list of Worker whenSuccessful")
	void findByName_returnListOfWorker_WhenSuccessful() {
		Worker workerToBeSaved = WorkerCreator.createWorkerADMIN_ToBeSaved();
		Worker savedWorker = this.workerRepository.save(workerToBeSaved);
		String name = savedWorker.getName();
		List<Worker> workers = this.workerRepository.findByNameContainingIgnoreCase(name);
		
		Assertions.assertThat(workers).isNotEmpty().contains(savedWorker);	
	}
	
	@Test
	@DisplayName("findbyname return empty list when worker is not found")
	void findByName_returnListEmpty_WhenWorkerIsNotFound() {
		List<Worker> worker = this.workerRepository.findByNameContainingIgnoreCase("zuculu");
		
		Assertions.assertThat(worker).isEmpty();
	}
	
	@Test
	@DisplayName("findById return worker whenSuccessful")
	void findById_ReturnWorker_WhenSuccessful() {
		Worker workerToBeSaved = WorkerCreator.createWorkerADMIN_ToBeSaved();
		Worker savedWorker = this.workerRepository.save(workerToBeSaved);
		
		Worker worker = this.workerRepository.findById(savedWorker.getId()).get();
		
		Assertions.assertThat(worker).isNotNull();
		Assertions.assertThat(worker).isEqualTo(savedWorker);
	}
	
	@Test
	@DisplayName("listAll return list of Worker whenSuccessful")
	void listAll_returnListOfWorker_WhenSuccessful() {
		Worker workerToBeSaved = WorkerCreator.createWorkerADMIN_ToBeSaved();
		Worker savedWorker = this.workerRepository.save(workerToBeSaved);
		List<Worker> worker = this.workerRepository.findAll();
		
		Assertions.assertThat(worker).isNotEmpty().contains(savedWorker);
	}
	
	@Test
	@DisplayName("save throwConstraintViolationException when worker name is empty")
	void save_throwConstraintViolationException() {
		Worker worker = new Worker();
		
//		Assertions.assertThatThrownBy(() -> this.workerRepository.save(worker))
//		.isInstanceOf(ConstraintViolationException.class);
		
		Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
		.isThrownBy(() -> this.workerRepository.save(worker));
		
	}
	
}
