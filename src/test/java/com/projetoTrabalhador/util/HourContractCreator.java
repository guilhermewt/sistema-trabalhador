package com.projetoTrabalhador.util;

import java.text.ParseException;

import com.projetoTrabalhador.entities.HourContract;

public class HourContractCreator {

	public static HourContract hourContractToBeSaved() throws ParseException {
		return HourContract.builder()
				.date(DateConvert.convertData("2022/08/15"))
				.valuePerHour(22.0)
				.hour(5)
				.build();
	}
	
	public static HourContract createValidhourContract() throws ParseException {
		return HourContract.builder()
				.id(1l)
				.date(DateConvert.convertData("2022/08/15"))
				.valuePerHour(22.0)
				.hour(5)
				.build();
	}
	
	public static HourContract hourContractUpdated() throws ParseException {
		return HourContract.builder()
				.id(1l)
				.date(DateConvert.convertData("2024/08/17"))
				.valuePerHour(24.0)
				.hour(4)
				.build();
	}
}
