package com.projetoTrabalhador.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.projetoTrabalhador.service.WorkerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final WorkerService workerService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").permitAll();
//		http.csrf().disable()
//				// .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()
//				.authorizeRequests()
//				.antMatchers("/workers/admin/**").hasRole("ADMIN")
//				.antMatchers("/workers/**").hasRole("USER")
//				.antMatchers("/departments/**").hasRole("USER")
//				.antMatchers("/contracts/**").hasRole("USER")
//				.anyRequest()
//				.authenticated().and()
//				.httpBasic();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//		log.info("password Encoder {}", passwordEncoder.encode("trabalhador"));
//		auth.inMemoryAuthentication()
//		.withUser("guilherme2")
//		.password(passwordEncoder.encode("trabalhador")).roles("USER", "ADMIN")
//		.and()
//		.withUser("trabalhador2")
//		.password(passwordEncoder.encode("trabalhador")).roles("USER");
//
//		auth.userDetailsService(workerService).passwordEncoder(passwordEncoder);
	}
}
