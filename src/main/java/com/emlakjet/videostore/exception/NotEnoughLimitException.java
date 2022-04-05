package com.emlakjet.videostore.exception;

public class NotEnoughLimitException extends RuntimeException {

	public NotEnoughLimitException() {
		super("there is not enough limit to purchase");
	}
}
