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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_hourContract")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HourContract implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date date;
	private Double valuePerHour;
	private Integer hour;
	
	@ManyToOne
	@JoinColumn(name = "worker_id")
	private Worker worker;

	@JsonIgnore
	public Worker getWorker() {
		return worker;
	}

}
