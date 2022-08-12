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

import com.projetoTrabalhador.entities.Department;
import com.projetoTrabalhador.requests.DepartmentPostRequestBody;
import com.projetoTrabalhador.requests.DepartmentPutRequestBody;
import com.projetoTrabalhador.service.DepartmentService;
import com.projetoTrabalhador.util.DepartmentCreator;
import com.projetoTrabalhador.util.DepartmentPostRequestBodyCreator;
import com.projetoTrabalhador.util.DepartmentPutRequestBodyCreator;

@ExtendWith(SpringExtension.class)
public class DepartmentResourceTest {
	
	@InjectMocks
	private DepartmentResource departmentResource;
	
	@Mock
	private DepartmentService departmentServiceMock;
	
	@BeforeEach
	void setUp() {
		PageImpl<Department> departmentPage = new PageImpl<>(List.of(DepartmentCreator.createValidDepartment()));
		BDDMockito.when(departmentServiceMock.findAll(ArgumentMatchers.any())).thenReturn(departmentPage);
		
		BDDMockito.when(departmentServiceMock.listAllNonPageable())
		.thenReturn(List.of(DepartmentCreator.createValidDepartment()));
		
		BDDMockito.when(departmentServiceMock.findByIdOrThrowResourceNotFoundException(ArgumentMatchers.anyLong()))
		.thenReturn(DepartmentCreator.createValidDepartment());
		
		BDDMockito.when(departmentServiceMock.findByName(ArgumentMatchers.anyString()))
		.thenReturn(List.of(DepartmentCreator.createValidDepartment()));
		
		BDDMockito.when(departmentServiceMock.insert(ArgumentMatchers.any(DepartmentPostRequestBody.class)))
		.thenReturn(DepartmentCreator.createValidDepartment());
		
		BDDMockito.doNothing().when(departmentServiceMock).update(ArgumentMatchers.any(DepartmentPutRequestBody.class));
		
		BDDMockito.doNothing().when(departmentServiceMock).delete(ArgumentMatchers.anyLong());
		
		          
	}
	
	@Test
	@DisplayName("List return list of department inside page object when successful")
	void list_ReturnsListOfDepartmentInsidePageObject_whenSuccessful() {
		String expectedName = DepartmentCreator.createValidDepartment().getName();
		
		Page<Department> departmentPage = departmentResource.findAll(null).getBody();
		
		Assertions.assertThat(departmentPage).isNotNull();
		
		Assertions.assertThat(departmentPage.toList()).isNotEmpty().hasSize(1);
		
		Assertions.assertThat(departmentPage.toList().get(0).getName()).isEqualTo(expectedName);
	}
	
	@Test
	@DisplayName("ListAll return list of department when successful")
	void listAll_ReturnsListOfDepartment_whenSuccessful() {
		String expectedName = DepartmentCreator.createValidDepartment().getName();
		
		List<Department> department = departmentResource.listAll().getBody();
		
		Assertions.assertThat(department)
		.isNotNull()
		.isNotEmpty()
		.hasSize(1);
		
		Assertions.assertThat(department.get(0).getName()).isEqualTo(expectedName);
	}
	
	@Test
	@DisplayName("findById return department when successful")
	void findById_ReturnsDepartment_whenSuccessful() {
		Long expectedId = DepartmentCreator.createValidDepartment().getId();	
		Department department = departmentResource.findById(1).getBody();
		
		Assertions.assertThat(department).isNotNull();
		
		Assertions.assertThat(department.getId()).isNotNull().isEqualTo(expectedId);
	}
	
	@Test
	@DisplayName("findByName return list of department when successful")
	void findByName_ReturnsDepartment_whenSuccessful() {
        String expectedName = DepartmentCreator.createValidDepartment().getName();
		
		List<Department> department = departmentResource.findByName("informatica").getBody();
		
		Assertions.assertThat(department)
		.isNotNull()
		.isNotEmpty()
		.hasSize(1);
		
		Assertions.assertThat(department.get(0).getName()).isEqualTo(expectedName);
	}
	
	@Test
	@DisplayName("findByName return an empty list of department when name is not found")
	void findByName_ReturnsEmptyListOfDepartment_whenDepartmentIsNotFound() {
		BDDMockito.when(departmentServiceMock.findByName(ArgumentMatchers.anyString()))
		.thenReturn(Collections.emptyList());
		
		List<Department> department = departmentResource.findByName("informatica").getBody();
		
		Assertions.assertThat(department).isNotNull().isEmpty();
	}
	
	@Test
	@DisplayName("save return department when successful")
	void save_ReturnsDepartment_whenSuccessful() {

		Department department = departmentResource.insert(DepartmentPostRequestBodyCreator.createDepartmentPostRequestBody()).getBody();
		
		Assertions.assertThat(department).isNotNull().isEqualTo(DepartmentCreator.createValidDepartment());
		
	}
	
	@Test
	@DisplayName("replace update department when successful")
	void replace_UpdateDepartment_whenSuccessful() {
        
        /*primeiro jeito*/
		Assertions.assertThatCode(() -> departmentResource.update(DepartmentPutRequestBodyCreator.createDepartmentPutRequestBody()))
		.doesNotThrowAnyException();
		
		/*segundo jeito*/
		ResponseEntity<Void> entity = departmentResource.update(DepartmentPutRequestBodyCreator.createDepartmentPutRequestBody());
		
		Assertions.assertThat(entity).isNotNull();
		
		Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		
	}
	
	@Test
	@DisplayName("delete removes department when successful")
	void delete_RemovesDepartment_whenSuccessful() {
        
        /*primeiro jeito*/
		Assertions.assertThatCode(() -> departmentResource.delete(1))
		.doesNotThrowAnyException();
		
		/*segundo jeito*/
		ResponseEntity<Void> entity = departmentResource.delete(1);		
		Assertions.assertThat(entity).isNotNull();
		
		Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		
	}

}
