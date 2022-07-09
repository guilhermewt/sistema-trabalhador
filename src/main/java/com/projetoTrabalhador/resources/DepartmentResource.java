package com.projetoTrabalhador.resources;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projetoTrabalhador.entities.Department;
import com.projetoTrabalhador.requests.DepartmentPostRequestBody;
import com.projetoTrabalhador.requests.DepartmentPutRequestBody;
import com.projetoTrabalhador.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/departments")
@RequiredArgsConstructor
public class DepartmentResource {

	private final DepartmentService service;
	
	@RequestMapping
	public ResponseEntity<List<Department>> findAll(){
		
		return ResponseEntity.ok().body(service.findAll());
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public ResponseEntity<Department> findById(@PathVariable long id){
	    
		return ResponseEntity.ok().body(service.findByIdOrThrowResourceNotFoundException(id));
	}
	
	@PostMapping
	public ResponseEntity<Department> insert(@RequestBody DepartmentPostRequestBody departmentPostRequestBody){

		return ResponseEntity.ok().body(service.insert(departmentPostRequestBody));
	}
	
	@DeleteMapping
	public ResponseEntity<Void> delete(@PathVariable long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping
	public ResponseEntity<Department> update(@RequestBody DepartmentPutRequestBody departmentPutRequestBody){
	    service.update(departmentPutRequestBody);
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
