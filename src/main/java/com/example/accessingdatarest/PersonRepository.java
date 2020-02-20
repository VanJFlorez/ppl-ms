package com.example.accessingdatarest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// @RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepository extends JpaRepository<Person, Long> {
  // List<Person> findByLastName(@Param("name") String name);
	public List<Person> findByFirstName(String firstName);
	public List<Person> findByLastName(String lastName);
	
	@Query("from Person where firstName=?1 order by lastName")
	public List<Person> findByFirstNameSorted(String firstName);
	
}