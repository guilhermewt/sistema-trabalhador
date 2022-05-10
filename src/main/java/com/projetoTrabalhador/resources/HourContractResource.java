package com.projetoTrabalhador.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projetoTrabalhador.entities.HourContract;
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
	
	@RequestMapping(value="/{id}", method = RequestMethod.POST)
	public ResponseEntity<HourContract> insert(@RequestBody HourContract obj, @PathVariable long id){
		
		HourContract contract = service.insert(obj, id);
		return ResponseEntity.ok().body(contract);
	}
}
