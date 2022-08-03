package com.projetoTrabalhador.repository;

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
	@DisplayName("save creater Department when successful")
	void save_persistDeparment_whenSuccessful(){
		Department departmentToBeSaved = createDepartment();
		Department departmentSaved = this.departmentRepository.save(departmentToBeSaved);
		Assertions.assertThat(departmentSaved).isNotNull();
		Assertions.assertThat(departmentSaved.getId()).isNotNull();
		Assertions.assertThat(departmentSaved.getName()).isEqualTo(departmentToBeSaved.getName());
	}
	
	private Department createDepartment() {
		return Department.builder().name("informatica").build();
	}

}
