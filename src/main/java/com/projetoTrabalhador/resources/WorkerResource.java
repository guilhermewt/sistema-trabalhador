package com.projetoTrabalhador.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projetoTrabalhador.dto.CalculateContractTimeDTO;
import com.projetoTrabalhador.entities.Worker;
import com.projetoTrabalhador.service.WorkerService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "/workers")
@Log4j2
public class WorkerResource {

	@Autowired
	private WorkerService service;
	 
	@RequestMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Worker>> findAll(){
		List<Worker> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Worker> findById(@AuthenticationPrincipal UserDetails userDetails,  @PathVariable long id){
		log.info(userDetails);
		Worker obj = service.findById(id);
	    return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value="/{id}",method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Worker> insert(@RequestBody Worker obj,@PathVariable long id){
		Worker worker = service.insert(obj, id);
		return ResponseEntity.ok().body(worker);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Worker> update(@RequestBody Worker obj, @PathVariable long id){
	    Worker worker = service.update(obj, id);
	    return ResponseEntity.ok().body(worker);
	}
	
	@RequestMapping(value = "/{id}/searchContracts", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CalculateContractTimeDTO> calculateHourContract(@RequestParam(value="date",defaultValue="")String date, @PathVariable long id){
		String montAndYear = date;
		int month = Integer.parseInt(montAndYear.substring(0, 2));
		int year = Integer.parseInt(montAndYear.substring(3));
		
		double baseSalary = service.income(id, year, month);
		Worker obj = service.findById(id);
		return ResponseEntity.ok().body(new CalculateContractTimeDTO(obj, baseSalary));
	}
}
