package com.example.accessingdatarest;

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

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;

@RestController // Since we are using JpaRepository all methods return JSON by default...
@RequestMapping(path="/people")
public class PeopleController {
	
	@Autowired
	PersonRepository personRepo;
	
	/**
	 * Adds a Person entry to database.
	 * @param A person Object
	 * @return The persisted person object
	 * @author Camilo F.
	 */
	@PostMapping("")
	public Person addPerson(@RequestBody Person p) {
		System.out.println(p);
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
	 */
	@PutMapping("/{id}")
	public Person updatePerson(@PathVariable long id, @RequestBody Person newPerson) throws InvalidDefinitionException {
		Person oldPerson;
		try {
			oldPerson = personRepo.getOne(id);
			oldPerson.setFirstName(newPerson.getFirstName());
			oldPerson.setLastName(newPerson.getLastName());
			return oldPerson;
		} catch(EmptyResultDataAccessException e) {
			return new Person();
		} catch(Exception e) {
			System.out.println("[ERROR] InvocationException");
			return new Person();
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