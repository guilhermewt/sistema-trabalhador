package com.projetoTrabalhador.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.projetoTrabalhador.entities.Department;
import com.projetoTrabalhador.entities.Worker;
import com.projetoTrabalhador.repository.DepartmentRepository;
import com.projetoTrabalhador.repository.WorkerRepository;

@Configuration
public class configuration implements CommandLineRunner{

	@Autowired
	private WorkerRepository workerRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		
		
		Department dep1 = new Department(null,"Design");
		Department dep2 = new Department(null,"financeiro");
		Department dep3 = new Department(null,"administrativo");
		
		departmentRepository.saveAll(Arrays.asList(dep1,dep2,dep3));
		
		Worker worker = new Worker(null,"gustavo henrique",3000.0,dep1);
		Worker worker2 = new Worker(null,"pedro castro",5000.0,dep2);
		Worker worker3 = new Worker(null,"fernando canesin",1500.0,dep1);
		workerRepository.saveAll(Arrays.asList(worker,worker2,worker3));
		
	}

}
