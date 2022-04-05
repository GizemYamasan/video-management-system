package com.emlakjet.videostore.exception;

public class EmailAlreadyRegisteredException extends RuntimeException {

	public EmailAlreadyRegisteredException() {
		super("email is already in use");
	}

}
