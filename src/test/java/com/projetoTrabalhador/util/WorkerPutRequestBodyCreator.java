package com.projetoTrabalhador.util;

import com.projetoTrabalhador.requests.WorkerPutRequestBody;

public class WorkerPutRequestBodyCreator {

	public static WorkerPutRequestBody createWorkerPutRequestBody() {
		return WorkerPutRequestBody.builder()
				.id(WorkerCreator.createValidUpdatedWorker().getId())
				.name(WorkerCreator.createValidUpdatedWorker().getName())
				.userName(WorkerCreator.createValidUpdatedWorker().getUsername())
				.baseSalary(WorkerCreator.createValidUpdatedWorker().getBaseSalary())
				.password(WorkerCreator.createValidUpdatedWorker().getPassword())
				.authorities("ROLE_ADMIN,ROLE_USER")
				.build();
	}
}
