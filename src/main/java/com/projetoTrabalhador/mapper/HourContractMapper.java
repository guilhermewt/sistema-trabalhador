package com.projetoTrabalhador.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.projetoTrabalhador.entities.HourContract;
import com.projetoTrabalhador.requests.HourContractPostRequestBody;
import com.projetoTrabalhador.requests.HourContractPutRequestBody;

@Mapper(componentModel = "spring")
public abstract class HourContractMapper {
	
	public static HourContractMapper INSTANCE = Mappers.getMapper(HourContractMapper.class);
	
	public abstract HourContract toHourContract(HourContractPostRequestBody hourContractPostRequestBody);
	
	public abstract HourContract toHourContract(HourContractPutRequestBody hourContractPutRequestBody);
	
}
