package com.projetoTrabalhador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projetoTrabalhador.entities.Worker;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long>{

	Worker findByuserName(String userName);
	
	List<Worker> findByNameContainingIgnoreCase(String name);
}
