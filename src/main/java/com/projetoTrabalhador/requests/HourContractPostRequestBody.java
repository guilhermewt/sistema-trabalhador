package com.projetoTrabalhador.requests;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projetoTrabalhador.entities.Worker;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class HourContractPostRequestBody implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Date date;
	private Double valuePerHour;
	private Integer hour;


	private Worker worker;
	
	public HourContractPostRequestBody(Date date, Double valuePerHour, Integer hour) {
		super();
		this.date = date;
		this.valuePerHour = valuePerHour;
		this.hour = hour;
	}

	@JsonIgnore
	public Worker getWorker() {
		return worker;
	}

}
