package com.projetoTrabalhador.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.projetoTrabalhador.entities.HourContract;
import com.projetoTrabalhador.mapper.HourContractMapper;
import com.projetoTrabalhador.repository.HourContractRepository;
import com.projetoTrabalhador.repository.WorkerRepository;
import com.projetoTrabalhador.requests.HourContractPostRequestBody;
import com.projetoTrabalhador.requests.HourContractPutRequestBody;
import com.projetoTrabalhador.service.exceptions.DataBaseError;
import com.projetoTrabalhador.service.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HourContractService {

	private final HourContractRepository repository;

	private final WorkerRepository workerRepository;

	public List<HourContract> findAll() {
		return repository.findAll();
	}

	public HourContract findById(long id) {
		Optional<HourContract> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public void insert(HourContractPostRequestBody hourContractPostRequestBody, long id) {

		HourContract contract = HourContractMapper.INSTANCE.toHourContract(hourContractPostRequestBody);
		contract.setWorker(workerRepository.findById(id).get());

		repository.save(contract);
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new DataBaseError(e.getMessage());
		}

	}

	public void update(HourContractPutRequestBody hourContractPutRequestBody) {
		try {
			HourContract savedHourContract = repository.findById(hourContractPutRequestBody.getId()).get();
			HourContract contract = HourContractMapper.INSTANCE.toHourContract(hourContractPutRequestBody);
			
			contract.setId(savedHourContract.getId());
			repository.save(contract);

		} catch (NoSuchElementException e) {
			throw new DataBaseError(e.getMessage());
		}
	}

}
