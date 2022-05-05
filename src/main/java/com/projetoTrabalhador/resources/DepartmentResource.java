package com.projetoTrabalhador.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetoTrabalhador.entities.Department;
import com.projetoTrabalhador.service.DepartmentService;

@RestController
@RequestMapping(value = "/departments")
public class DepartmentResource {

	@Autowired
	private DepartmentService repository;
	
	@RequestMapping
	public ResponseEntity<List<Department>> findAll(){
		
		List<Department> list = repository.findAll();
		return ResponseEntity.ok().body(list);
	}
}
