package com.projetoTrabalhador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projetoTrabalhador.entities.HourContract;

@Repository
public interface HourContractRepository extends JpaRepository<HourContract, Long>{

}
