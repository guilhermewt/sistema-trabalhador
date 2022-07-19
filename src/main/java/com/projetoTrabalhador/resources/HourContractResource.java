package com.projetoTrabalhador.resources;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetoTrabalhador.entities.HourContract;
import com.projetoTrabalhador.requests.HourContractPostRequestBody;
import com.projetoTrabalhador.requests.HourContractPutRequestBody;
import com.projetoTrabalhador.service.HourContractService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/contracts")
@RequiredArgsConstructor
public class HourContractResource {

	private final HourContractService service;
	
	@GetMapping
	public ResponseEntity<List<HourContract>> findAll(){
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<HourContract> findById(@PathVariable long id){
	    return ResponseEntity.ok(service.findByIdOrElseThrowResourceNotFoundException(id));
	}
	
	
	@PostMapping(value="/{id}")
	public ResponseEntity<HourContract> insert(@RequestBody HourContractPostRequestBody hourContractPostRequestBody, @PathVariable long id){
		service.insert(hourContractPostRequestBody, id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping
	public ResponseEntity<HourContract> update(@RequestBody HourContractPutRequestBody hourContractPutRequestBody){
		 service.update(hourContractPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
