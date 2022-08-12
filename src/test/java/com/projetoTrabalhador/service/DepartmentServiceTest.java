package com.projetoTrabalhador.service;

import java.util.Collections;
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
import com.projetoTrabalhador.repository.DepartmentRepository;
import com.projetoTrabalhador.service.exceptions.BadRequestException;
import com.projetoTrabalhador.util.DepartmentCreator;
import com.projetoTrabalhador.util.DepartmentPostRequestBodyCreator;
import com.projetoTrabalhador.util.DepartmentPutRequestBodyCreator;

@ExtendWith(SpringExtension.class)
public class DepartmentServiceTest {
	
	@InjectMocks
	private DepartmentService departmentService;
	
	@Mock
	private DepartmentRepository departmentRepositoryMock;
	
	@BeforeEach
	void setUp() {
		PageImpl<Department> departmentPage = new PageImpl<>(List.of(DepartmentCreator.createValidDepartment()));
		BDDMockito.when(departmentRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(departmentPage);
		
		BDDMockito.when(departmentRepositoryMock.findAll())
		.thenReturn(List.of(DepartmentCreator.createValidDepartment()));
		
		BDDMockito.when(departmentRepositoryMock.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.of(DepartmentCreator.createValidDepartment()));
		
		BDDMockito.when(departmentRepositoryMock.findByNameContainingIgnoreCase(ArgumentMatchers.anyString()))
		.thenReturn(List.of(DepartmentCreator.createValidDepartment()));
		
		BDDMockito.when(departmentRepositoryMock.save(ArgumentMatchers.any(Department.class)))
		.thenReturn(DepartmentCreator.createValidDepartment());
		
		
		BDDMockito.doNothing().when(departmentRepositoryMock).delete(ArgumentMatchers.any(Department.class));
		  
	}
	
	@Test
	@DisplayName("findAll return list of department inside page object when successful")
	void findAll_ReturnsListOfDepartmentInsidePageObject_whenSuccessful() {
		String expectedName = DepartmentCreator.createValidDepartment().getName();
		
		Page<Department> departmentPage = departmentService.findAll(PageRequest.of(1, 1));
		
		Assertions.assertThat(departmentPage).isNotNull();
		
		Assertions.assertThat(departmentPage.toList()).isNotEmpty().hasSize(1);
		
		Assertions.assertThat(departmentPage.toList().get(0).getName()).isEqualTo(expectedName);
	}
	
	@Test
	@DisplayName("ListAll return list of department when successful")
	void listAllNonPageable_ReturnsListOfDepartment_whenSuccessful() {
		String expectedName = DepartmentCreator.createValidDepartment().getName();
		
		List<Department> department = departmentService.listAllNonPageable();
		
		Assertions.assertThat(department)
		.isNotNull()
		.isNotEmpty()
		.hasSize(1);
		
		Assertions.assertThat(department.get(0).getName()).isEqualTo(expectedName);
	}
	
	@Test
	@DisplayName("findById return department when successful")
	void findByIdOrThrowResourceNotFoundException_ReturnsDepartment_whenSuccessful() {
		Long expectedId = DepartmentCreator.createValidDepartment().getId();	
		Department department = departmentService.findByIdOrThrowResourceNotFoundException(1);
		
		Assertions.assertThat(department).isNotNull();
		
		Assertions.assertThat(department.getId()).isNotNull().isEqualTo(expectedId);
	}
	
	@Test
	@DisplayName("findByIdOrThrowBadRequestException throw  BadRequestException when department is not found")
	void findByIdOrThrowBadRequestException_throwBadRequestException_WhenDepartmentIsNotFound() {
		
		BDDMockito.when(departmentRepositoryMock.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.empty());
		
		Assertions.assertThatExceptionOfType(BadRequestException.class)
        .isThrownBy(() -> departmentService.findByIdOrThrowResourceNotFoundException(1));
	}
	
	@Test
	@DisplayName("findByName return list of department when successful")
	void findByName_ReturnsDepartment_whenSuccessful() {
        String expectedName = DepartmentCreator.createValidDepartment().getName();
		
		List<Department> department = departmentService.findByName("informatica");
		
		Assertions.assertThat(department)
		.isNotNull()
		.isNotEmpty()
		.hasSize(1);
		
		Assertions.assertThat(department.get(0).getName()).isEqualTo(expectedName);
	}
	
	@Test
	@DisplayName("findByName return an empty list of department when name is not found")
	void findByName_ReturnsEmptyListOfDepartment_whenDepartmentIsNotFound() {
		BDDMockito.when(departmentRepositoryMock.findByNameContainingIgnoreCase(ArgumentMatchers.anyString()))
		.thenReturn(Collections.emptyList());
		
		List<Department> department = departmentService.findByName("informatica");
		
		Assertions.assertThat(department).isNotNull().isEmpty();
	}
	
	@Test
	@DisplayName("save return department when successful")
	void save_ReturnsDepartment_whenSuccessful() {

		Department department = departmentService.insert(DepartmentPostRequestBodyCreator.createDepartmentPostRequestBody());
		
		Assertions.assertThat(department).isNotNull().isEqualTo(DepartmentCreator.createValidDepartment());
		
	}
	
	@Test
	@DisplayName("replace update department when successful")
	void replace_UpdateDepartment_whenSuccessful() {
        
        /*primeiro jeito*/
		Assertions.assertThatCode(() -> departmentService.update(DepartmentPutRequestBodyCreator.createDepartmentPutRequestBody()))
		.doesNotThrowAnyException();
		
	}
	
	@Test
	@DisplayName("delete removes department when successful")
	void delete_RemovesDepartment_whenSuccessful() {
        
        /*primeiro jeito*/
		Assertions.assertThatCode(() -> departmentService.delete(1))
		.doesNotThrowAnyException();
		
	}

}
