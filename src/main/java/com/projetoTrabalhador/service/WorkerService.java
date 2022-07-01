package com.projetoTrabalhador.service;

import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projetoTrabalhador.entities.Department;
import com.projetoTrabalhador.entities.HourContract;
import com.projetoTrabalhador.entities.Worker;
import com.projetoTrabalhador.repository.DepartmentRepository;
import com.projetoTrabalhador.repository.WorkerRepository;
import com.projetoTrabalhador.service.exceptions.DataBaseError;
import com.projetoTrabalhador.service.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkerService implements UserDetailsService{

	@Autowired
	private WorkerRepository repository;

	@Autowired
	private DepartmentRepository departmentRepository;

	public List<Worker> findAll() {
		return repository.findAll();
	}

	public Worker findById(long id) {
		Optional<Worker> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Worker insert(Worker obj, long id) {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		obj.setPassword(passwordEncoder.encode(obj.getPassword()));
		Department depart = departmentRepository.findById(id).get();
		obj.setDepartment(depart);	
		
		return repository.save(obj);
	}

	public void delete(long id) {

		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new DataBaseError(e.getMessage());
		}
	}

	public Worker update(Worker obj, long id) {
		try {
			Worker worker = repository.findById(id).get();
			updateData(worker, obj);
			return repository.save(worker);
		} catch (NoSuchElementException e) {
			throw new DataBaseError(e.getMessage());
		}
	}

	private void updateData(Worker worker, Worker obj) {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		worker.setName(obj.getName());
		worker.setUserName(obj.getUsername());
		worker.setBaseSalary(obj.getBaseSalary());
		worker.setPassword(passwordEncoder.encode(obj.getPassword()));
		
	}

	public double income(long id, int year, int month) {
		Worker worker = repository.findById(id).get();
		Double sum = worker.getBaseSalary();

		Set<HourContract> contracts = worker.getContracts();

		Calendar cal = Calendar.getInstance();
		for (HourContract c : contracts) {
			cal.setTime(c.getDate());
			int c_year = cal.get(Calendar.YEAR);
			int c_month = 1 + cal.get(Calendar.MONTH);

			if (year == c_year && month == c_month) {
				sum += c.getValuePerHour() * c.getHour();
			}

		}
		return sum;
	}

	@Override
	public UserDetails loadUserByUsername(String username){
		return Optional.ofNullable(repository.findByuserName(username))
				.orElseThrow(() -> new UsernameNotFoundException("worker user not found"));
	}
}
