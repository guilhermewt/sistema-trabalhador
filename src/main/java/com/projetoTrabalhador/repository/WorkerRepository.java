package com.projetoTrabalhador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projetoTrabalhador.entities.Worker;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long>{

}
