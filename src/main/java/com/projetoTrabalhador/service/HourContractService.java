package com.projetoTrabalhador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projetoTrabalhador.entities.HourContract;
import com.projetoTrabalhador.entities.Worker;
import com.projetoTrabalhador.repository.HourContractRepository;
import com.projetoTrabalhador.repository.WorkerRepository;

@Service
public class HourContractService {
	
	@Autowired
	private HourContractRepository repository;
	
	@Autowired
	private WorkerRepository workerRepository;
	
	public List<HourContract> findAll(){
		return repository.findAll();
	}
	
	public HourContract insert(HourContract obj, long id) {
		Worker worker = workerRepository.findById(id);
		obj.setWorker(worker);
		return repository.save(obj);
	}
}
