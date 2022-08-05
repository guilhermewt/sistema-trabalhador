package com.projetoTrabalhador.repository;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.projetoTrabalhador.entities.Department;

@DataJpaTest
@DisplayName("test for department repository")
public class DepartmentRepositoryTest {
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	
	@Test
	@DisplayName("save persist Department when successful")
	void save_persistDeparment_whenSuccessful(){
		Department departmentToBeSaved = createDepartment();
		
		Department departmentSaved = this.departmentRepository.save(departmentToBeSaved);
		
		Assertions.assertThat(departmentSaved).isNotNull();
		
		Assertions.assertThat(departmentSaved.getId()).isNotNull();
		
		Assertions.assertThat(departmentSaved.getName()).isEqualTo(departmentToBeSaved.getName());
	}
	
	@Test
	@DisplayName("update Department when successful")
	void save_updateDeparment_whenSuccessful(){
		Department departmentToBeSaved = createDepartment();
		
		Department departmentSaved = this.departmentRepository.save(departmentToBeSaved);
		
		departmentSaved.setName("tecnologia");
		
		Department departmentUpdate = this.departmentRepository.save(departmentSaved);
		
		Assertions.assertThat(departmentUpdate).isNotNull();
		
		Assertions.assertThat(departmentUpdate.getId()).isNotNull();
		
		Assertions.assertThat(departmentUpdate.getName()).isEqualTo(departmentSaved.getName());
	}
	
	@Test
	@DisplayName("delete removes Department when successful")
	void delete_removesDeparment_whenSuccessful(){
		Department departmentToBeSaved = createDepartment();
		
		Department departmentSaved = this.departmentRepository.save(departmentToBeSaved);
		
		this.departmentRepository.delete(departmentSaved);
		
		Optional<Department> departmentOptional = this.departmentRepository.findById(departmentSaved.getId());
		
		Assertions.assertThat(departmentOptional).isEmpty();
	}
	
	@Test
	@DisplayName("find by name Return list of anime whenSuccessful")
	void findByName_ReturnsListOfDepartment_WhenSuccessful() {
		Department departmentToBeSaved = createDepartment();
		Department departmentSaved = this.departmentRepository.save(departmentToBeSaved);
		
		String name = departmentSaved.getName();
		
		List<Department> departments = this.departmentRepository.findByNameContainingIgnoreCase(name);
		
		Assertions.assertThat(departments).isNotEmpty().contains(departmentSaved);
		
	}
	
	@Test
	@DisplayName("find by name Return Empty list of anime when is not found")
	void findByName_ReturnsEmptyListOfDepartment() {
		List<Department> departments = this.departmentRepository.findByNameContainingIgnoreCase("goru");
		Assertions.assertThat(departments).isEmpty();
	}
	
	@Test
	@DisplayName("save ThrowConstraintViolationException when Department is empty")
	void save_ThrowsConstraintViolationException_whenNameIsEmpty(){
		Department department = new Department();
		
//		Assertions.assertThatThrownBy(() -> this.departmentRepository.save(department))
//		.isInstanceOf(ConstraintViolationException.class);
		
		Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
		                   .isThrownBy(() -> this.departmentRepository.save(department))
		                   .withMessageContaining("the department name cannot be empty");
		
	}
	
	private Department createDepartment() {
		return Department.builder().name("informatica").build();
	}

}
