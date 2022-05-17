package com.projetoTrabalhador.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projetoTrabalhador.entities.HourContract;
import com.projetoTrabalhador.entities.Worker;
import com.projetoTrabalhador.repository.HourContractRepository;
import com.projetoTrabalhador.repository.WorkerRepository;
import com.projetoTrabalhador.service.exceptions.ResourceNotFoundException;

@Service
public class HourContractService {
	
	@Autowired
	private HourContractRepository repository;
	
	@Autowired
	private WorkerRepository workerRepository;
	
	public List<HourContract> findAll(){
		return repository.findAll();
	}
	
	public HourContract findById(long id){
		Optional<HourContract> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public HourContract insert(HourContract obj, long id) {
		Worker worker = workerRepository.findById(id).get();
		obj.setWorker(worker);
		return repository.save(obj);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public HourContract update(HourContract obj, long id) {
		HourContract hc = repository.findById(id).get();
		updateData(hc,obj);
		return repository.save(hc);
	}

	private void updateData(HourContract hc, HourContract obj) {
		hc.setDate(obj.getDate());
		hc.setValuePerHour(obj.getValuePerHour());
		hc.setHour(obj.getHour());
		
	}
}
