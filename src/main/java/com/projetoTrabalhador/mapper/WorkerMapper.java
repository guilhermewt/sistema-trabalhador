package com.projetoTrabalhador.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.projetoTrabalhador.entities.Worker;
import com.projetoTrabalhador.requests.WorkerPostRequestBody;
import com.projetoTrabalhador.requests.WorkerPutRequestBody;

@Mapper(componentModel = "spring")
public abstract class WorkerMapper {
	
	public static final WorkerMapper INSTANCE = Mappers.getMapper(WorkerMapper.class);
	
	public abstract Worker toWorker(WorkerPostRequestBody workerPostRequestBody);
	
	public abstract Worker toWorker(WorkerPutRequestBody workerPutRequestBody);
	
}
