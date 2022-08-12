package com.projetoTrabalhador.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.projetoTrabalhador.entities.Department;
import com.projetoTrabalhador.mapper.DepartmentMapper;
import com.projetoTrabalhador.repository.DepartmentRepository;
import com.projetoTrabalhador.requests.DepartmentPostRequestBody;
import com.projetoTrabalhador.requests.DepartmentPutRequestBody;
import com.projetoTrabalhador.service.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentService {
	
	private final DepartmentRepository repository;
	
	public Page<Department> findAll(Pageable pageable){	
		return repository.findAll(pageable);
	}
	
	public List<Department> listAllNonPageable(){
		return repository.findAll();
	}
	
	public List<Department> findByName(String name){	
		return repository.findByNameContainingIgnoreCase(name);
	}
	
	
	public Department findByIdOrThrowResourceNotFoundException(long id) {	
		return repository.findById(id).orElseThrow(() -> new BadRequestException("department not found"));
	}

	@Transactional
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
