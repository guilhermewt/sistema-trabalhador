package com.projetoTrabalhador.util;

import com.projetoTrabalhador.entities.HourContract;

public class HourContractCreator {

	public static HourContract hourContractToBeSaved(){
		return HourContract.builder()
				.date(DateConvert.convertData("2018/08/20"))
				.valuePerHour(50.0)
				.hour(20)
				.build();
	}
	
	public static HourContract createValidhourContract() {
		return HourContract.builder()
				.id(1l)
				.date(DateConvert.convertData("2018/08/20"))
				.valuePerHour(50.0)
				.hour(20)
				.build();
	}
	
	public static HourContract hourContractUpdated(){
		return HourContract.builder()
				.id(1l)
				.date(DateConvert.convertData("2024/04/24"))
				.valuePerHour(54.0)
				.hour(24)
				.build();
	}
}
