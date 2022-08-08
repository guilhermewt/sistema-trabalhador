package com.projetoTrabalhador.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

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
	
	@Test
	@DisplayName("save update HourContract when successful")
	void save_updateHourContract_whenSuccessful() {
		HourContract hourContractToBeSaved = createHourContract();
		HourContract hourContractSaved = this.hourContractRepository.save(hourContractToBeSaved);
		
		hourContractSaved.setValuePerHour(70.0);
	    
		HourContract hourContractUpdate = this.hourContractRepository.save(hourContractSaved);
		
		Assertions.assertThat(hourContractUpdate).isNotNull();
		Assertions.assertThat(hourContractUpdate.getId()).isNotNull();
		Assertions.assertThat(hourContractUpdate.getValuePerHour()).isEqualTo(hourContractSaved.getValuePerHour());
	}
	
	@Test
	@DisplayName("delete removes hourContract when successfull")
	void delete_removesHourContract_whenSuccessful() {
		HourContract hourContractToBeSaved = createHourContract();
		HourContract hourContractSaved = this.hourContractRepository.save(hourContractToBeSaved);
		
		hourContractRepository.delete(hourContractSaved);
		
	    Optional<HourContract> hourContract = this.hourContractRepository.findById(hourContractSaved.getId());
	    
	    Assertions.assertThat(hourContract).isEmpty();
	}
	
	@Test
	@DisplayName("save throw ConstrationViolationException when hourContract is empty")
	void save_ThrowConstrationViolation_WhenNameIsEmpty() {
		HourContract contract = new HourContract();
//		Assertions.assertThatThrownBy(() -> this.hourContractRepository.save(contract))
//		.isInstanceOf(ConstraintViolationException.class);
		
		Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
		  .isThrownBy(() -> this.hourContractRepository.save(contract));
		//nao conseguir pois a constraint...ex nao tem para tipo data
	}
	
	private HourContract createHourContract(){
		try {
			return HourContract.builder().date(sdf.parse("2022/08/02")).valuePerHour(20.0).hour(2).build();
		} catch (ParseException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
}
