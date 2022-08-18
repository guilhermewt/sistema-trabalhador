package com.projetoTrabalhador.util;

import java.text.ParseException;

import com.projetoTrabalhador.entities.HourContract;
import com.projetoTrabalhador.entities.Worker;

public class WorkerCreator {
	
	public static Worker validWorker() {
		return Worker.builder()
				.id(1l)
				.name("guilherme")
				.userName("gui")
				.baseSalary(1.200)
				.password("1234567")
				.authorities("ROLES_ADMIN")
				.build();
	}

}
