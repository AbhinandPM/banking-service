package com.abhi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.abhi.customer.dto.ErrorDto;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger EXE_LOGGER = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

	@ExceptionHandler(value = { InvalidInputException.class })
	protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {
		EXE_LOGGER.error(ex.getMessage(), ex);
		ErrorDto error = new ErrorDto("Exception : " + ex.getMessage());
		return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = { AuthenticationException.class })
	protected ResponseEntity<Object> handleAuthenticationExce(Exception ex, WebRequest request) {
		EXE_LOGGER.error(ex.getMessage(), ex);
		ErrorDto error = new ErrorDto("Exception : " + ex.getMessage());
		return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
	}

	@ExceptionHandler(value = { RestClientResponseException.class })
	protected ResponseEntity<Object> handleRestTemplateException(RestClientResponseException ex, WebRequest request) {
		EXE_LOGGER.error(ex.getMessage(), ex);
		return handleExceptionInternal(ex, ex.getResponseBodyAsString(), new HttpHeaders(),
				HttpStatus.valueOf(ex.getRawStatusCode()), request);
	}

}