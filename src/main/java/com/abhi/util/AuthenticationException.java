package com.abhi.util;

/**
 * @author abhin
 *
 */
public class AuthenticationException extends Exception {

	private static final long serialVersionUID = 6686441769231655213L;

	public AuthenticationException(String message, Exception e) {
		super(message, e);
	}
}
