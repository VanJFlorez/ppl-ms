package com.example.accessingdatarest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.accessingdatarest.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

	public List<Person> findByFirstName(String firstName);
	public List<Person> findByLastName(String lastName);
	
	@Query("from Person where firstName=?1 order by lastName")
	public List<Person> findByFirstNameSorted(String firstName);
	
}