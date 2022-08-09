package com.projetoTrabalhador.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	
	@GetMapping
	public ResponseEntity<Page<Department>> findAll(Pageable pageable){
		return ResponseEntity.ok(service.findAll(pageable));
	}
	
	@GetMapping(value = "/find")
	public ResponseEntity<List<Department>> findByName(@RequestParam String name){
		return ResponseEntity.ok(service.findByName(name));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Department> findById(@PathVariable long id){
		return ResponseEntity.ok().body(service.findByIdOrThrowResourceNotFoundException(id));
	}
	
	@PostMapping
	public ResponseEntity<Department> insert(@RequestBody @Valid DepartmentPostRequestBody departmentPostRequestBody){
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
