package com.abhi.banking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abhi.account.dto.TransactionDto;
import com.abhi.util.RestTemplateUtility;

@RestController
public class TransactionController {

	@Autowired
	private RestTemplateUtility restTemplateUtility;

	@PostMapping("/transaction")
	public ResponseEntity<TransactionDto> handleTransaction(@RequestBody TransactionDto transactionDto) {
		return restTemplateUtility.postForObject("http://localhost:4000/account", transactionDto, TransactionDto.class);
	}
}
