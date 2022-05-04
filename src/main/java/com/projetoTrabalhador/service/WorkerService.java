package com.projetoTrabalhador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projetoTrabalhador.entities.Worker;
import com.projetoTrabalhador.repository.WorkerRepository;

@Service
public class WorkerService {
	
	@Autowired
	private WorkerRepository repository;
	
	public List<Worker> findAll(){
		return repository.findAll();
	}
}
