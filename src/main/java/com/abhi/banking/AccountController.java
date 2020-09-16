package com.abhi.banking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abhi.account.dto.AccountDto;
import com.abhi.util.RestTemplateUtility;

@RestController
public class AccountController {


	@Autowired
	private RestTemplateUtility restTemplateUtility;
	
	@PostMapping("/account")
	public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
		return restTemplateUtility.postForObject("http://localhost:4000/account", accountDto, AccountDto.class);
	}

	@GetMapping("/account/{accountNo}")
	public ResponseEntity<AccountDto> getAccountDetailsByAccountNo(@PathVariable Long accountNo) {
		return restTemplateUtility.getForObject("http://localhost:4000/account/" + accountNo, AccountDto.class);
	}

}
