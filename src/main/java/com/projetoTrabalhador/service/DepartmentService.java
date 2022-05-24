package com.projetoTrabalhador.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.projetoTrabalhador.entities.Department;
import com.projetoTrabalhador.repository.DepartmentRepository;
import com.projetoTrabalhador.service.exceptions.DataBaseError;
import com.projetoTrabalhador.service.exceptions.ResourceNotFoundException;

@Service
public class DepartmentService {
	
	@Autowired
	private DepartmentRepository repository;
	
	public List<Department> findAll(){
		return repository.findAll();
	}
	
	public Department findById(long id) {
		Optional<Department> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Department insert(Department obj) {
		return repository.save(obj);
	}

	public void delete(long id) {

		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new DataBaseError(e.getMessage());
		}
	}

	public Department update(Department obj, long id) {
		try {
			Department depart = repository.findById(id).get();
			updateData(depart, obj);
			return repository.save(depart);
		} catch (NoSuchElementException e) {
			throw new DataBaseError(e.getMessage());
		}
	}

	private void updateData(Department depart, Department obj) {
		depart.setName(obj.getName());
	}



}
