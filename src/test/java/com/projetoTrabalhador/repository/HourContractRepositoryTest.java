package com.projetoTrabalhador.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.projetoTrabalhador.entities.HourContract;

@DataJpaTest
@DisplayName("test for hourContract repository")
public class HourContractRepositoryTest {
	
	SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd");
	
	@Autowired
	private HourContractRepository hourContractRepository;
	
	@Test
	@DisplayName("save creater HourContract when successful")
	void save_persistHourContract_whenSuccessful() {
		HourContract hourContract = createHourContract();
		HourContract savedContract = this.hourContractRepository.save(hourContract);
		
		Assertions.assertThat(savedContract).isNotNull();
		Assertions.assertThat(savedContract.getId()).isNotNull();
		Assertions.assertThat(savedContract.getDate()).isEqualTo(hourContract.getDate());
		Assertions.assertThat(savedContract.getHour()).isEqualTo(hourContract.getHour());
		Assertions.assertThat(savedContract.getValuePerHour()).isEqualTo(hourContract.getValuePerHour());
		
	}
	
	private HourContract createHourContract(){
		try {
			return HourContract.builder().date(sdf.parse("2022/08/02")).valuePerHour(20.0).hour(2).build();
		} catch (ParseException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
}
