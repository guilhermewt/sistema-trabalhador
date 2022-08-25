package com.projetoTrabalhador.util;

import com.projetoTrabalhador.requests.WorkerPostRequestBody;

public class WorkerPostRequestBodyCreator {
	
	public static WorkerPostRequestBody createWorkerPostResquestBody() {
		return WorkerPostRequestBody.builder()
				.name(WorkerCreator.createValidWorker().getName())
				.userName(WorkerCreator.createValidWorker().getUsername())
				.baseSalary(WorkerCreator.createValidWorker().getBaseSalary())
				.password(WorkerCreator.createValidWorker().getPassword())
				.authorities("ROLE_ADMIN,ROLE_USER")
				.build();
	}
}
