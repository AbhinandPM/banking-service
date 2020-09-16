package com.abhi.banking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abhi.customer.dto.CustomerDto;
import com.abhi.util.RestTemplateUtility;

@RestController
public class CustomerController {

	@Autowired
	private RestTemplateUtility restTemplateUtility;
	
	@PostMapping("/customer")
	public ResponseEntity<CustomerDto> registerCustomer(@RequestBody CustomerDto customer) {
		return restTemplateUtility.postForObject("http://localhost:4000/customer", customer, CustomerDto.class);
	}

	@GetMapping("/customer/username/{username}")	
	public ResponseEntity<CustomerDto> getCustomerByUsername(@PathVariable String username) {
		return restTemplateUtility.getForObject("http://localhost:4000/customer/username/" + username, CustomerDto.class);
	}

}
