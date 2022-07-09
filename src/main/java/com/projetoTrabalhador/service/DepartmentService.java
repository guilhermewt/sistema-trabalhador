package com.projetoTrabalhador.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projetoTrabalhador.entities.Department;
import com.projetoTrabalhador.mapper.DepartmentMapper;
import com.projetoTrabalhador.repository.DepartmentRepository;
import com.projetoTrabalhador.requests.DepartmentPostRequestBody;
import com.projetoTrabalhador.requests.DepartmentPutRequestBody;
import com.projetoTrabalhador.service.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentService {
	
	private final DepartmentRepository repository;
	
	public List<Department> findAll(){
		
		return repository.findAll();
	}
	
	
	public Department findByIdOrThrowResourceNotFoundException(long id) {
		
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
	}

	
	public Department insert(DepartmentPostRequestBody departmentPostRequestBody) {
		return repository.save(DepartmentMapper.INSTANCE.toDepartment(departmentPostRequestBody));
	}

	
	public void delete(long id) {
		
		repository.delete(findByIdOrThrowResourceNotFoundException(id));
	}

	
	public void update(DepartmentPutRequestBody departmentPutRequestBody) {
		
		Department savedDepartment = findByIdOrThrowResourceNotFoundException(departmentPutRequestBody.getId());
		Department department = DepartmentMapper.INSTANCE.toDepartment(departmentPutRequestBody);
		department.setId(savedDepartment.getId());
		repository.save(department);
	}

}
