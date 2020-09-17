package com.abhi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.abhi.account.dto.AccountDto;
import com.abhi.account.dto.TransactionDto;
import com.abhi.customer.dto.CustomerDto;
import com.abhi.util.RestTemplateUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class BankingServiceApplicationTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@MockBean
	private RestTemplateUtility restTemplateUtility;

	private static ObjectMapper mapper = new ObjectMapper();

	@Test
	void contextLoads() {
		assertThat(context).isNotNull();
	}

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	@WithMockUser(value = "spring")
	void testSearchAccount() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders.get("/account/123").accept(MediaType.APPLICATION_JSON);

		AccountDto account = new AccountDto();
		account.setAccountNo(123L);
		account.setCustomerId(1L);
		account.setAccountBalance(BigDecimal.valueOf(1000L));
		account.setAccountType("current");
		account.setBranchCode(12L);
		account.setIfscCode("BANK123");

		ResponseEntity<Object> response = new ResponseEntity<>(account, HttpStatus.OK);

		when(restTemplateUtility.getForObject(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(response);

		mockMvc.perform(request).andExpect(status().isOk())
				// .andExpect(jsonPath("$.accountNo", Matchers.equalTo(account.getAccountNo())))
				.andExpect(jsonPath("$.customerId", Matchers.equalTo(1))).andReturn();

	}

	@Test
	@WithMockUser(value = "spring")
	void testCreateAccount() throws Exception {

		AccountDto account = new AccountDto();
		account.setAccountNo(123L);
		account.setCustomerId(1L);
		account.setAccountBalance(BigDecimal.valueOf(1000L));
		account.setAccountType("current");
		account.setBranchCode(12L);
		account.setIfscCode("BANK123");
		String json = mapper.writeValueAsString(account);

		ResponseEntity<Object> response = new ResponseEntity<>(account, HttpStatus.OK);

		when(restTemplateUtility.postForObject(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
				.thenReturn(response);

		mockMvc.perform(post("/account").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.accountNo", Matchers.equalTo(123)))
				.andExpect(jsonPath("$.customerId", Matchers.equalTo(1))).andReturn();
	}

	@Test
	@WithMockUser(value = "spring")
	void testTransaction() throws Exception {

		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setAccountNo(123L);
		transactionDto.setAmount(BigDecimal.valueOf(100L));
		transactionDto.setTransactionType("credit");

		String json = mapper.writeValueAsString(transactionDto);

		ResponseEntity<Object> response = new ResponseEntity<>(transactionDto, HttpStatus.OK);

		when(restTemplateUtility.postForObject(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
				.thenReturn(response);

		mockMvc.perform(post("/transaction").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.accountNo", Matchers.equalTo(123))).andReturn();
	}

	@Test
	@WithMockUser(value = "spring")
	void testSearchCustomer() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders.get("/customer/username/john")
				.accept(MediaType.APPLICATION_JSON);

		CustomerDto customer = new CustomerDto();
		customer.setFirstname("john");
		customer.setId(1L);
		customer.setUsername("john");
		customer.setFirstname("John");
		customer.setLastname("Abraham");

		ResponseEntity<Object> response = new ResponseEntity<>(customer, HttpStatus.OK);

		when(restTemplateUtility.getForObject(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(response);

		mockMvc.perform(request).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstname", Matchers.equalTo(customer.getFirstname())))
				.andExpect(jsonPath("$.lastname", Matchers.equalTo(customer.getLastname())))
				.andExpect(jsonPath("$.id", Matchers.equalTo(1))).andReturn();
	}

	@Test
	@WithMockUser(value = "spring")
	void tesRegisterCustomer() throws Exception {
		CustomerDto customer = new CustomerDto();

		customer.setFirstname("John");
		customer.setLastname("Doe");
		customer.setUsername("john");
		customer.setAddress("Abc");
		customer.setContactNo(9384483234L);
		String json = mapper.writeValueAsString(customer);
		customer.setId(1L);

		ResponseEntity<Object> response = new ResponseEntity<>(customer, HttpStatus.OK);

		when(restTemplateUtility.postForObject(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
				.thenReturn(response);

		mockMvc.perform(post("/customer").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstname", Matchers.equalTo(customer.getFirstname())))
				.andExpect(jsonPath("$.lastname", Matchers.equalTo(customer.getLastname())))
				.andExpect(jsonPath("$.id", Matchers.equalTo(1)));
	}

	@Test
	@WithMockUser(value = "spring")
	void tesRegisterCustomerWithNullValue() throws Exception {
		CustomerDto customer = new CustomerDto();

		customer.setFirstname("John");
		customer.setLastname("Doe");
		customer.setUsername("john");
		customer.setAddress("Abc");
		customer.setContactNo(9384483234L);
		String json = mapper.writeValueAsString(customer);

		ResponseEntity<Object> response = new ResponseEntity<>(customer, HttpStatus.BAD_REQUEST);

		when(restTemplateUtility.postForObject(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
				.thenReturn(response);

		mockMvc.perform(post("/customer").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

}
