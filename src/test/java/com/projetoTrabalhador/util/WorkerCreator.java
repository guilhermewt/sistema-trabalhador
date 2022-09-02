package com.projetoTrabalhador.util;

import com.projetoTrabalhador.entities.Worker;

public class WorkerCreator {
	
	public static Worker createValidWorker() {
		return Worker.builder()
				.id(1l)
				.name("guilherme")
				.userName("gui")
				.baseSalary(1.200)
				.password("{bcrypt}$2a$10$kVStg9UcqLzpufBXvWsJ0uZmI6yuRtFo6/mFedY3w5bTb90VwjfuS")
				.build();
	}
	
	public static Worker createWorkerADMIN_ToBeSaved() {
		return Worker.builder()
				.name("guilherme2")
				.userName("gui")
				.baseSalary(1.200)
				.password("{bcrypt}$2a$10$kVStg9UcqLzpufBXvWsJ0uZmI6yuRtFo6/mFedY3w5bTb90VwjfuS")
				.authorities("ROLE_ADMIN,ROLE_USER")
				.build();
	}
	
	public static Worker createValidUpdatedWorker() {
		return Worker.builder()
				.id(1l)
				.name("guilherme silva")
				.userName("gui silva")
				.baseSalary(1.400)
				.password("{bcrypt}$2a$10$kVStg9UcqLzpufBXvWsJ0uZmI6yuRtFo6/mFedY3w5bTb90VwjfuS")
				.authorities("ROLE_ADMIN,ROLE_USER")
				.build();
	}
	
	public static Worker createWorkerUSER_ToBeSaved() {
		return Worker.builder()
				.name("trabalhador2 academy")
				.userName("welliston")
				.baseSalary(1.200)
				.password("{bcrypt}$2a$10$kVStg9UcqLzpufBXvWsJ0uZmI6yuRtFo6/mFedY3w5bTb90VwjfuS")
				.authorities("ROLE_USER")
				.build();
	}

}
