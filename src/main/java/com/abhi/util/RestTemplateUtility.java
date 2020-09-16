package com.abhi.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateUtility {

	private static final Logger LOGGER = LogManager.getLogger(RestTemplateUtility.class);

	@Autowired
	RestTemplate restTemplate;

	public <T> ResponseEntity<T> getForObject(String url, Class<T> responseType) {
		LOGGER.info("RestTemplate GET call to url :<{}> starts>>>", url);
		ResponseEntity<T> response = restTemplate.getForEntity(url, responseType);
		LOGGER.info("RestTemplate call to url :<{}> ends>>>", url);
		return response;
	}

	public <T> ResponseEntity<T> postForObject(String url, T body, Class<T> responseType) {
		LOGGER.info("RestTemplate POST call to url :<{}> starts>>>", url);
		ResponseEntity<T> response = restTemplate.postForEntity(url, body, responseType);
		LOGGER.info("RestTemplate POST call to url :<{}> starts>>>", url);
		return response;
	}
}
