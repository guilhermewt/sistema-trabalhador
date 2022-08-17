package com.projetoTrabalhador.util;

import java.text.ParseException;

import com.projetoTrabalhador.requests.HourContractPostRequestBody;

public class HourContractPostRequestBodyCreator {
	
	public static HourContractPostRequestBody createHourContractPostRequestBody() throws ParseException {
		return HourContractPostRequestBody.builder()
				.date(HourContractCreator.hourContractToBeSaved().getDate())
				.valuePerHour(HourContractCreator.hourContractToBeSaved().getValuePerHour())
				.hour(HourContractCreator.hourContractToBeSaved().getHour())
				.build();
	}
}
