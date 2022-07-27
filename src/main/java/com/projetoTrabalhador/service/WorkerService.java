package com.projetoTrabalhador.service;

import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projetoTrabalhador.entities.HourContract;
import com.projetoTrabalhador.entities.Worker;
import com.projetoTrabalhador.mapper.WorkerMapper;
import com.projetoTrabalhador.repository.DepartmentRepository;
import com.projetoTrabalhador.repository.WorkerRepository;
import com.projetoTrabalhador.requests.WorkerPostRequestBody;
import com.projetoTrabalhador.requests.WorkerPutRequestBody;
import com.projetoTrabalhador.service.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkerService implements UserDetailsService {

	private final WorkerRepository repository;

	private final DepartmentRepository departmentRepository;

	public List<Worker> findAll() {
		return repository.findAll();
	}

	public Worker findByIdOrElseThrowResourceNotFoundException(long id) {
		return repository.findById(id).orElseThrow(() -> new BadRequestException("worker not found"));
	}

	public void insert(WorkerPostRequestBody workerPostRequestBody, long id) {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		workerPostRequestBody.setPassword(passwordEncoder.encode(workerPostRequestBody.getPassword()));
		workerPostRequestBody.setDepartment(departmentRepository.findById(id).get());

		repository.save(WorkerMapper.INSTANCE.toWorker(workerPostRequestBody));
	}

	public void delete(long id) {

		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new BadRequestException(e.getMessage());
		} catch (ConstraintViolationException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public void update(WorkerPutRequestBody workerPutRequestBody) {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		try {
			Worker worker = WorkerMapper.INSTANCE.toWorker(workerPutRequestBody);

			Worker workerSaved = repository.findById(workerPutRequestBody.getId()).get();
			worker.setId(workerSaved.getId());
			worker.setPassword(passwordEncoder.encode(workerPutRequestBody.getPassword()));

			repository.save(worker);
		} catch (NoSuchElementException e) {
			throw new BadRequestException(e.getMessage());
		}

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
	public UserDetails loadUserByUsername(String username) {
		return Optional.ofNullable(repository.findByuserName(username))
				.orElseThrow(() -> new UsernameNotFoundException("worker user not found"));
	}
}
