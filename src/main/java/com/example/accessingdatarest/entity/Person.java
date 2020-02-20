package com.example.accessingdatarest.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.model.IdPropertyIdentifierAccessor;

import com.example.accessingdatarest.service.FamilyIntegrityException;
import com.example.accessingdatarest.service.FamilyService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
/**
 * @author Camilo
 * CRATE DATABASE people
 * 
 * spring.jpa.hibernate.ddl-auto=update
 */


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private @Getter @Setter long id;

	private @Getter @Setter String firstName;
	private @Getter @Setter String lastName;
	
	private @Getter @Setter long idMother;
	private @Getter @Setter long idFather;
	private @Getter @Setter long[] idSilbings;
	
	@Transient
	@Autowired
	FamilyService fService;

	public Person() {
		this.firstName = "";
		this.lastName = "";
		this.idMother = -1;
	}

	public Person(String firstName, String lastName, long idMother, long idFather, long[] idSilbings) throws FamilyIntegrityException {
		this.firstName = firstName;
		this.lastName = lastName;
		this.idMother = idMother;
		this.idFather = idFather;
		this.idSilbings = idSilbings;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
}