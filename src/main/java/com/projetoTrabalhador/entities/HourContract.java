package com.projetoTrabalhador.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tb_hourContract")
@NoArgsConstructor
@Data
@SuperBuilder
public class HourContract implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @NotNull(message = "the HourContract date cannot cannot be empty")
	private Date date;
	
	@NotNull(message = "the HourContract valuePerHour cannot be empty")
	private Double valuePerHour;
	
	@NotNull(message = "the HourContract hour cannot be empty")
	private Integer hour;
	
	@ManyToOne
	@JoinColumn(name = "worker_id")
	private Worker worker;
	
	public HourContract(Long id, Date date, Double valuePerHour, Integer hour) {
		super();
		this.id = id;
		this.date = date;
		this.valuePerHour = valuePerHour;
		this.hour = hour;
	}

	@JsonIgnore
	public Worker getWorker() {
		return worker;
	}

}
