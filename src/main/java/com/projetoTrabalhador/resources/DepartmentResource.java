package com.projetoTrabalhador.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projetoTrabalhador.dto.CalculateContractTimeDTO;
import com.projetoTrabalhador.entities.Department;
import com.projetoTrabalhador.entities.Worker;
import com.projetoTrabalhador.service.DepartmentService;

@RestController
@RequestMapping(value = "/departments")
public class DepartmentResource {

	@Autowired
	private DepartmentService service;
	
	@RequestMapping
	public ResponseEntity<List<Department>> findAll(){
		
		List<Department> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public ResponseEntity<Department> findById(@PathVariable long id){
		Department obj = service.findById(id);
	    return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Department> insert(@RequestBody Department obj){
		Department depart = service.insert(obj);
		return ResponseEntity.ok().body(depart);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Department> update(@RequestBody Department obj, @PathVariable long id){
	    Department depart = service.update(obj, id);
	    return ResponseEntity.ok().body(depart);
	}

}
