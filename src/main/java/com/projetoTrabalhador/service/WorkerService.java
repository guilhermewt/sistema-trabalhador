package com.projetoTrabalhador.service;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projetoTrabalhador.entities.HourContract;
import com.projetoTrabalhador.entities.Worker;
import com.projetoTrabalhador.repository.WorkerRepository;

@Service
public class WorkerService {
	
	@Autowired
	private WorkerRepository repository;

	
	public List<Worker> findAll(){
		return repository.findAll();
	}
	
	public Worker findById(long id){
		return repository.findById(id);
	}
	
	public double income(long id,int year, int month) {
		Worker worker = repository.findById(id);
		Double sum = worker.getBaseSalary();
		
		Set<HourContract> contracts = worker.getContracts();
		
		Calendar cal = Calendar.getInstance();
		for(HourContract c : contracts) {
			cal.setTime(c.getDate());
			int c_year = cal.get(Calendar.YEAR);
			int c_month = 1 + cal.get(Calendar.MONTH);
			
			if(year == c_year && month == c_month) {
				sum += c.getValuePerHour() * c.getHour();
			}
			
		}
		return sum;
	}
}
