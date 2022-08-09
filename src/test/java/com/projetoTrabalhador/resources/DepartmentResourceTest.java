package com.projetoTrabalhador.resources;

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

import com.projetoTrabalhador.entities.Department;
import com.projetoTrabalhador.service.DepartmentService;
import com.projetoTrabalhador.util.DepartmentCreator;

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
	
	
}
