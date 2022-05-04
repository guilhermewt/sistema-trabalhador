package com.projetoTrabalhador.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.projetoTrabalhador.entities.Worker;
import com.projetoTrabalhador.repository.WorkerRepository;

@Configuration
public class configuration implements CommandLineRunner{

	@Autowired
	private WorkerRepository workerRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		Worker worker = new Worker(null,"gustavo henrique",3000.0);
		Worker worker2 = new Worker(null,"pedro castro",5000.0);
		Worker worker3 = new Worker(null,"fernando canesin",1500.0);
		workerRepository.saveAll(Arrays.asList(worker,worker2,worker3));
		
	}

}
