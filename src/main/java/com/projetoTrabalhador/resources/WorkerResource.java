package com.projetoTrabalhador.resources;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.projetoTrabalhador.requests.WorkerPostRequestBody;
import com.projetoTrabalhador.requests.WorkerPutRequestBody;
import com.projetoTrabalhador.service.WorkerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "/workers")
@Log4j2
@RequiredArgsConstructor
public class WorkerResource {

	private final WorkerService service;
	 
	@RequestMapping(value = "/admin")
	public ResponseEntity<List<Worker>> findAll(){
		List<Worker> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/admin/{id}",method = RequestMethod.GET)
	public ResponseEntity<Worker> findById(@AuthenticationPrincipal UserDetails userDetails,  @PathVariable long id){
		log.info(userDetails);
		Worker obj = service.findById(id);
	    return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value="/admin/{id}",method = RequestMethod.POST)
	public ResponseEntity<Worker> insert(@RequestBody WorkerPostRequestBody workerPostRequestBody,@PathVariable long id){
		Worker worker = service.insert(workerPostRequestBody, id);
		return ResponseEntity.ok().body(worker);
	}
	
	@RequestMapping(value="/admin/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/admin/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Worker> update(@RequestBody WorkerPutRequestBody workerPutRequestBody){
	    service.update(workerPutRequestBody);
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "/admin/{id}/searchContracts", method = RequestMethod.GET)
	public ResponseEntity<CalculateContractTimeDTO> calculateHourContract(@RequestParam(value="date",defaultValue="")String date, @PathVariable long id){
		String montAndYear = date;
		int month = Integer.parseInt(montAndYear.substring(0, 2));
		int year = Integer.parseInt(montAndYear.substring(3));
		
		double baseSalary = service.income(id, year, month);
		Worker obj = service.findById(id);
		return ResponseEntity.ok().body(new CalculateContractTimeDTO(obj, baseSalary));
	}

}
