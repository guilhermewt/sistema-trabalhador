package com.projetoTrabalhador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projetoTrabalhador.entities.Department;
import com.projetoTrabalhador.repository.DepartmentRepository;

@Service
public class DepartmentService {
	
	@Autowired
	private DepartmentRepository repository;
	
	public List<Department> findAll(){
		return repository.findAll();
	}
}
