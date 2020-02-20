package com.example.accessingdatarest.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.accessingdatarest.repository.PersonRepository;

@Service
public class FamilyServiceImpl implements FamilyService {
	
	@Autowired
	PersonRepository personRepo;
	
	@Override
	public boolean validateSilbings(long[] silbings) {
		for(long id : silbings) {
			if(personRepo.findById(id).isPresent())
				return true;
			else {
				System.out.println("[ERROR] Silbing id: " + id + " not found.");
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean validateParents(long idParent) {
		if(idParent == -1 || personRepo.findById(idParent).isPresent())
			return true;
		else {
			System.out.println("[ERROR] Parent id: " + idParent + " not found.");
			return false;
		}
	}
}
