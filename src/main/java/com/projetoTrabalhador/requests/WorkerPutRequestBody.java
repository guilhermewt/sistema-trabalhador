package com.projetoTrabalhador.requests;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.projetoTrabalhador.entities.Department;
import com.projetoTrabalhador.entities.HourContract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of= {"id","name"})
@ToString
@SuperBuilder
public class WorkerPutRequestBody {
	
	private Long id;
	private String name;
	private String userName;
	private Double baseSalary;
	private String password;
	private String authorities;
	
	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;
	
	@OneToMany(mappedBy="worker", cascade = CascadeType.ALL)
	@Builder.Default
	private Set<HourContract> contracts = new HashSet<>();

	public WorkerPutRequestBody(Long id, String name, String userName, Double baseSalary, String password,
			String authorities) {
		super();
		this.id = id;
		this.name = name;
		this.userName = userName;
		this.baseSalary = baseSalary;
		this.password = password;
		this.authorities = authorities;
	}
	
	
	
	
}
