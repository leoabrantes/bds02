package com.devsuperior.bds02.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.services.CityService;
import com.devsuperior.bds02.services.exception.DatabaseException;
import com.devsuperior.bds02.services.exception.ResourceNotFoundException;


@RestController
@RequestMapping(value = "/cities")
public class CityController {
	
	@Autowired
	private CityService service;
	
	@GetMapping
	public ResponseEntity<List<CityDTO>> findAll(){
		List<CityDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
		
	@PostMapping
	public ResponseEntity<CityDTO> insert(@RequestBody CityDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();		
		return ResponseEntity.created(uri).body(dto);
	}
	

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<CityDTO> delete(@PathVariable Long id){
		try {
		service.delete(id);
		return ResponseEntity.noContent().build();
		}
		catch(ResourceNotFoundException e) {
		return ResponseEntity.notFound().build();
		}
		catch(DatabaseException e) {
		return ResponseEntity.badRequest().build();	
		}
	}
	
}
