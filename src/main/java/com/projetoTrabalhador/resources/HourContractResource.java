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
	
	@GetMapping(value = "/find")
	public ResponseEntity<Page<HourContract>> findAll(Pageable pageable){
		return ResponseEntity.ok(service.findAll(pageable));
	}
	
	@GetMapping
	public ResponseEntity<List<HourContract>> findAllNonPageable(){
		return ResponseEntity.ok(service.findAllNonPageable());
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<HourContract> findById(@PathVariable long id){
	    return ResponseEntity.ok(service.findByIdOrElseThrowResourceNotFoundException(id));
	}
	
	
	@PostMapping(value="/{id}")
	public ResponseEntity<HourContract> insert(@RequestBody @Valid HourContractPostRequestBody hourContractPostRequestBody, @PathVariable long id){
		return new ResponseEntity<HourContract>(service.insert(hourContractPostRequestBody, id), HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping
	public ResponseEntity<Void> update(@RequestBody HourContractPutRequestBody hourContractPutRequestBody){
		 service.update(hourContractPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
