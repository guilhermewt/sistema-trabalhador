package com.projetoTrabalhador.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projetoTrabalhador.entities.HourContract;
import com.projetoTrabalhador.requests.HourContractPostRequestBody;
import com.projetoTrabalhador.requests.HourContractPutRequestBody;
import com.projetoTrabalhador.service.HourContractService;

@RestController
@RequestMapping(value = "/contracts")
public class HourContractResource {

	@Autowired
	private HourContractService service;
	
	@RequestMapping
	public ResponseEntity<List<HourContract>> findAll(){
		
		List<HourContract> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public ResponseEntity<HourContract> findById(@PathVariable long id){
		HourContract obj = service.findById(id);
	    return ResponseEntity.ok().body(obj);
	}
	
	
	@RequestMapping(value="/{id}", method = RequestMethod.POST)
	public ResponseEntity<HourContract> insert(@RequestBody HourContractPostRequestBody hourContractPostRequestBody, @PathVariable long id){
		service.insert(hourContractPostRequestBody, id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable long id){
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<HourContract> update(@RequestBody HourContractPutRequestBody hourContractPutRequestBody){
		 service.update(hourContractPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
