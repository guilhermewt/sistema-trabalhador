package com.projetoTrabalhador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projetoTrabalhador.entities.HourContract;
import com.projetoTrabalhador.repository.HourContractRepository;

@Service
public class HourContractService {
	
	@Autowired
	private HourContractRepository repository;
	
	public List<HourContract> findAll(){
		return repository.findAll();
	}
}
