package com.projetoTrabalhador.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
 
@Entity
@Table(name = "tb_department")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of= {"id","name"})
@ToString
@SuperBuilder
public class Department implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "the department name cannot be empty")
	private String name;

	@OneToMany(mappedBy = "department")
	@Builder.Default
	private Set<Worker> worker = new HashSet<>();

	public Department(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	@JsonIgnore
	public Set<Worker> getWorker() {
		return worker;
	}

}
