package com.projetoTrabalhador.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetoTrabalhador.entities.Worker;
import com.projetoTrabalhador.service.WorkerService;

@RestController
@RequestMapping(value = "/worker")
public class WorkerResource {

	@Autowired
	private WorkerService repository;
	
	@RequestMapping
	public ResponseEntity<List<Worker>> findAll(){
		
		List<Worker> list = repository.findAll();
		return ResponseEntity.ok().body(list);
	}
}
