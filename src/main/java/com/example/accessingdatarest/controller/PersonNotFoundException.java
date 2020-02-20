package com.example.accessingdatarest.controller;

public class PersonNotFoundException extends Exception {

	private static final long serialVersionUID = -2871033627239748159L;
	
	public PersonNotFoundException(String msj) {
		super(msj);
	}
}
