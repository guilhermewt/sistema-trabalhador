package com.projetoTrabalhador.entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_worker")
public class Worker implements Serializable,UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	private Set<HourContract> contracts = new HashSet<>();
	
	public Worker() {
	}

	public Worker(Long id, String name, String userName, Double baseSalary, String password, String authorities,Department department) {
		super();
		this.id = id;
		this.name = name;
		this.userName = userName;
		this.baseSalary = baseSalary;
		this.password = password;
		this.authorities = authorities;
		this.department = department;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(Double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<HourContract> getContracts() {
		return contracts;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.stream(authorities.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
	}
	
	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}
	

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
