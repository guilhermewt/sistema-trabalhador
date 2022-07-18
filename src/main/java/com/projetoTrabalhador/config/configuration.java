package com.projetoTrabalhador.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.projetoTrabalhador.entities.Department;
import com.projetoTrabalhador.entities.HourContract;
import com.projetoTrabalhador.entities.Worker;
import com.projetoTrabalhador.repository.DepartmentRepository;
import com.projetoTrabalhador.repository.HourContractRepository;
import com.projetoTrabalhador.repository.WorkerRepository;

@Configuration
public class configuration implements CommandLineRunner{

	@Autowired
	private WorkerRepository workerRepository;

	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private HourContractRepository hourContractRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		Department dep1 = new Department(null,"Design");
		Department dep2 = new Department(null,"financeiro");
		Department dep3 = new Department(null,"administrativo");
		
		departmentRepository.saveAll(Arrays.asList(dep1,dep2,dep3));
		
		Worker worker = new Worker(null,"guilherme henrique","guilherme",1200.0,"{bcrypt}$2a$10$kVStg9UcqLzpufBXvWsJ0uZmI6yuRtFo6/mFedY3w5bTb90VwjfuS","ROLE_ADMIN,ROLE_USER");
		Worker worker2 = new Worker(null,"trabalhador","trabalhador",3200.0,"{bcrypt}$2a$10$kVStg9UcqLzpufBXvWsJ0uZmI6yuRtFo6/mFedY3w5bTb90VwjfuS","ROLE_USER");
        worker.setDepartment(dep1);
		worker2.setDepartment(dep1);
        //		Worker worker2 = new Worker(null,"pedro castro",null,5000.0,null,dep2);
//		Worker worker3 = new Worker(null,"fernando canesin",1500.0,dep1);
		
//		workerRepository.saveAll(Arrays.asList(worker,worker2,worker3));
		workerRepository.saveAll(Arrays.asList(worker,worker2));
		

		HourContract contra1 = new HourContract(null, sdf.parse("2018/08/20"), 50.0, 20, worker);
		HourContract contra2 = new HourContract(null, sdf.parse("2018/06/13"), 30.0, 18, worker);
		HourContract contra3 = new HourContract(null, sdf.parse("2018/08/25"), 80.0, 10, worker);
		
		hourContractRepository.saveAll(Arrays.asList(contra1,contra2,contra3));
		
	}

}
