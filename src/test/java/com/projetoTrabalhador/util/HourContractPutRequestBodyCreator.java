package com.projetoTrabalhador.util;

import java.text.ParseException;

import com.projetoTrabalhador.requests.HourContractPutRequestBody;

public class HourContractPutRequestBodyCreator {
	
	public static HourContractPutRequestBody createHourContractPutRequestBody() throws ParseException {
		return HourContractPutRequestBody.builder()
				.id(HourContractCreator.hourContractUpdated().getId())
				.date(HourContractCreator.hourContractUpdated().getDate())
				.valuePerHour(HourContractCreator.hourContractUpdated().getValuePerHour())
				.hour(HourContractCreator.hourContractUpdated().getHour())
				.build();
	}
}
