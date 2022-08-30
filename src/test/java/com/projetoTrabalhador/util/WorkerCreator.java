package com.projetoTrabalhador.util;

import com.projetoTrabalhador.entities.Worker;

public class WorkerCreator {
	
	public static Worker createValidWorker() {
		return Worker.builder()
				.id(1l)
				.name("guilherme")
				.userName("gui")
				.baseSalary(1.200)
				.password("1234567")
				.build();
	}
	
	public static Worker createWorkerToBeSaved() {
		return Worker.builder()
				.name("guilherme")
				.userName("gui")
				.baseSalary(1.200)
				.password("1234567")
				.authorities("ROLE_ADMIN,ROLE_USER")
				.build();
	}
	
	public static Worker createValidUpdatedWorker() {
		return Worker.builder()
				.id(1l)
				.name("guilherme 2")
				.userName("gui 2")
				.baseSalary(1.400)
				.password("12345678")
				.authorities("ROLE_ADMIN,ROLE_USER")
				.build();
	}

}
