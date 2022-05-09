package com.projetoTrabalhador.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetoTrabalhador.entities.HourContract;
import com.projetoTrabalhador.service.HourContractService;

@RestController
@RequestMapping(value = "/contracts")
public class HourContractResource {

	@Autowired
	private HourContractService repository;
	
	@RequestMapping
	public ResponseEntity<List<HourContract>> findAll(){
		
		List<HourContract> list = repository.findAll();
		return ResponseEntity.ok().body(list);
	}
}
