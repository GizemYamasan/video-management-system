package com.emlakjet.videostore.exception;

public class ContentNotFoundException extends RuntimeException {

	public ContentNotFoundException() {
		super("content is not found");
	}

}
