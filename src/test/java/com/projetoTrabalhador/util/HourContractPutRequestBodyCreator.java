package com.projetoTrabalhador.util;

import java.text.ParseException;

import com.projetoTrabalhador.requests.HourContractPutRequestBody;

public class HourContractPutRequestBodyCreator {
	
	public static HourContractPutRequestBody createHourContractPutRequestBody() throws ParseException {
		return HourContractPutRequestBody.builder()
				.id(HourContractCreator.hourContractUpdated().getId())
				.date(HourContractCreator.hourContractToBeSaved().getDate())
				.valuePerHour(HourContractCreator.hourContractToBeSaved().getValuePerHour())
				.hour(HourContractCreator.hourContractToBeSaved().getHour())
				.build();
	}
}
