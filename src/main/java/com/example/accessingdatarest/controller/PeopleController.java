package com.example.accessingdatarest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.accessingdatarest.entity.Person;
import com.example.accessingdatarest.repository.PersonRepository;
import com.example.accessingdatarest.service.FamilyIntegrityException;
import com.example.accessingdatarest.service.FamilyService;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;

@RestController // Since we are using JpaRepository all methods return JSON by default...
@RequestMapping(path="/people")
public class PeopleController {
	
	@Autowired
	PersonRepository personRepo;
	
	@Autowired
	FamilyService fService;
	
	/**
	 * Adds a Person entry to database.
	 * @param A person Object
	 * @return The persisted person object
	 * @author Camilo F.
	 * @throws FamilyIntegrityException 
	 */
	@PostMapping("")
	public Person addPerson(@RequestBody Person p) throws FamilyIntegrityException {
		if(!fService.validateParents(p.getIdMother()))
			throw new FamilyIntegrityException("Mother's id does not exists. This item will not be saved.");	
		if(!fService.validateParents(p.getIdFather()))
			throw new FamilyIntegrityException("Mother's id does not exists. This item will not be saved.");
		if(!fService.validateSilbings(p.getIdSilbings()))
			throw new FamilyIntegrityException("Some silbings's ids do not exist. This item will not be saved.");
		personRepo.save(p);
		return p;
	}
	
	/**
	 * Retrieves every person persisted in database
	 * @return a list containing all entries
	 * @author Camilo F.
	 */
	@RequestMapping("")
	public List<Person> getPeople() {
		return personRepo.findAll();
	}
	
	/**
	 * Updates a person by removing it from Database and adding a new one
	 * with the same id. Returns the removed element
	 * @param id - the id of item to update
	 * @param newPerson - object that holds the new data
	 * @return - the replaced item, for undo purposes
	 * @author Camilo F.
	 * @throws PersonNotFoundException 
	 */
	@PutMapping("/{id}")
	public Person updatePerson(@PathVariable long id, @RequestBody Person newPerson) throws PersonNotFoundException  {
		Person oldPerson;
		try {
			oldPerson = personRepo.getOne(id);
			oldPerson.setFirstName(newPerson.getFirstName());
			oldPerson.setLastName(newPerson.getLastName());
			oldPerson.setIdMother(newPerson.getIdMother());
			oldPerson.setIdFather(newPerson.getIdFather());
			oldPerson.setIdSilbings(newPerson.getIdSilbings());
			
			personRepo.save(oldPerson);
			return oldPerson;
		} catch(EmptyResultDataAccessException e) {
			throw new PersonNotFoundException("This person's id: " + id + " does not exists");
		} catch(Exception e) {
			throw new PersonNotFoundException("This person's id: " + id + " does not exists");
		}
	}
	
	@DeleteMapping("/{id}")
	public Person deletePerson(@PathVariable long id) {
		Person deletedPerson;
		try {
			deletedPerson = personRepo.getOne(id);
			personRepo.deleteById(id);
			return deletedPerson;
		} catch(EmptyResultDataAccessException e) {
			return new Person();
		}
	}
	
	@RequestMapping("/getByFirstName/{name}")
	public List<Person> getPeopleByName1(@PathVariable("name") String name) {
		return personRepo.findByFirstNameSorted(name);
	}
	
	@RequestMapping("/getByFirstName")
	public List<Person> getPeopleByName2(@RequestParam("name") String name) {
		return personRepo.findByFirstNameSorted(name);
	}
}