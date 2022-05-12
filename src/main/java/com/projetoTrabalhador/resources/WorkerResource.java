package com.projetoTrabalhador.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projetoTrabalhador.dto.CalculateContractTimeDTO;
import com.projetoTrabalhador.entities.Worker;
import com.projetoTrabalhador.service.WorkerService;

@RestController
@RequestMapping(value = "/workers")
public class WorkerResource {

	@Autowired
	private WorkerService service;
	
	
	@RequestMapping
	public ResponseEntity<List<Worker>> findAll(){
		
		List<Worker> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public ResponseEntity<Worker> findById(@PathVariable long id){
		Worker obj = service.findById(id);
	    return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value="/{id}",method = RequestMethod.POST)
	public ResponseEntity<Worker> insert(@RequestBody Worker obj,@PathVariable long id){
		Worker worker = service.insert(obj, id);
		return ResponseEntity.ok().body(worker);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Worker> update(@RequestBody Worker obj, @PathVariable long id){
	    Worker worker = service.update(obj, id);
	    return ResponseEntity.ok().body(worker);
	}
	
	@RequestMapping(value = "/{id}/searchContracts", method = RequestMethod.GET)
	public ResponseEntity<CalculateContractTimeDTO> calculateHourContract(@RequestParam(value="date",defaultValue="")String date, @PathVariable long id){
		String montAndYear = date;
		int month = Integer.parseInt(montAndYear.substring(0, 2));
		int year = Integer.parseInt(montAndYear.substring(3));
		
		double baseSalary = service.income(id, year, month);
		Worker obj = service.findById(id);
		return ResponseEntity.ok().body(new CalculateContractTimeDTO(obj, baseSalary));
	}
}
