package com.projetoTrabalhador.resources;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	 
	@GetMapping(value = "/admin/all")
	public ResponseEntity<Page<Worker>> findAll(Pageable pageable){
		return ResponseEntity.ok(service.findAll(pageable));
	}
	
	@GetMapping(value = "/admin")
	public ResponseEntity<List<Worker>> findAllNonPageable(){
		return ResponseEntity.ok(service.findNonPageable());
	}
	
	@GetMapping(value = "/admin/find")
	public ResponseEntity<List<Worker>> findByName(@RequestParam String name){
		return ResponseEntity.ok(service.findByName(name));
	}
	
	@GetMapping(value = "/admin/{id}")
	public ResponseEntity<Worker> findById(@AuthenticationPrincipal UserDetails userDetails,  @PathVariable long id){
		log.info(userDetails);
	    return ResponseEntity.ok(service.findByIdOrElseThrowResourceNotFoundException(id));
	}
	
	@PostMapping(value="/admin/{id}")
	public ResponseEntity<Worker> insert(@RequestBody WorkerPostRequestBody workerPostRequestBody,@PathVariable long id){
		service.insert(workerPostRequestBody, id);
		return new ResponseEntity<>(service.insert(workerPostRequestBody, id), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value="/admin/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value="/admin/")
	public ResponseEntity<Void> update(@RequestBody WorkerPutRequestBody workerPutRequestBody){
	    service.update(workerPutRequestBody);
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping(value = "/admin/{id}/searchContracts")
	public ResponseEntity<CalculateContractTimeDTO> calculateHourContract(@RequestParam(value="date",defaultValue="")String date, @PathVariable long id){
		String montAndYear = date;
		int month = Integer.parseInt(montAndYear.substring(0, 2));
		int year = Integer.parseInt(montAndYear.substring(3));
		
		double baseSalary = service.income(id, year, month);
		Worker obj = service.findByIdOrElseThrowResourceNotFoundException(id);
		return ResponseEntity.ok().body(new CalculateContractTimeDTO(obj, baseSalary));
	}

}
