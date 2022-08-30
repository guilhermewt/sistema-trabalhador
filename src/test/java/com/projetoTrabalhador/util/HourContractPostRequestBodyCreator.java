package com.projetoTrabalhador.util;

import com.projetoTrabalhador.requests.HourContractPostRequestBody;

public class HourContractPostRequestBodyCreator {
	
	public static HourContractPostRequestBody createHourContractPostRequestBody(){
		return HourContractPostRequestBody.builder()
				.date(HourContractCreator.hourContractToBeSaved().getDate())
				.valuePerHour(HourContractCreator.hourContractToBeSaved().getValuePerHour())
				.hour(HourContractCreator.hourContractToBeSaved().getHour())
				.build();
	}
}
