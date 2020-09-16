package com.abhi.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${transaction.url}")
	private String transactionUrl;

	@PostMapping("/transaction")
	public ResponseEntity<TransactionDto> handleTransaction(@RequestBody TransactionDto transactionDto) {
		return restTemplateUtility.postForObject(transactionUrl, transactionDto, TransactionDto.class);
	}
}
