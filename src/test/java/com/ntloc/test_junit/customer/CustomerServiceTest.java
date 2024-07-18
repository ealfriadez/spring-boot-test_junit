package com.ntloc.test_junit.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ntloc.test_junit.exception.CustomerEmailUnavailableException;
import com.ntloc.test_junit.exception.CustomerNotFoundException;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

	CustomerService customerService;
	@Mock
	CustomerRepository customerRepository;
	@Captor
	ArgumentCaptor<Customer> customerArgumentCaptor;

	@BeforeEach
	void setUp() {
		customerService = new CustomerService(customerRepository);
	}

	@AfterEach
	@Disabled
	void tearDown() {
	}

	@Test
	@Disabled
	void shouldGetAllGetCustomers() {
		// given
		// when
		customerService.getCustomers();
		// then
		verify(customerRepository).findAll();
	}

	@Test
	void shouldCreateCustomer() {
		// given
		CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest("Santiago Nicolas",
				"snalfriadez@gmail.com", "Loas Alisos");
		// when
		customerService.createCustomer(createCustomerRequest);
		// then
		verify(customerRepository).save(customerArgumentCaptor.capture());
		Customer customerCaptured = customerArgumentCaptor.getValue();

		assertThat(customerCaptured.getName()).isEqualTo(createCustomerRequest.name());
		assertThat(customerCaptured.getEmail()).isEqualTo(createCustomerRequest.email());
		assertThat(customerCaptured.getAddress()).isEqualTo(createCustomerRequest.address());

	}

	@Test
	void shouldNotCreateCustomerAndThrowExceptionWhenCustomerFindByEmailIsPresent() { 
		// given 
		CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest(
				"Santiago Nicolas", 
				"snalfriadez@gmail.com", 
				"Loas Alisos"); 
		// when
		when(customerRepository.findByEmail(anyString())).thenReturn(Optional.of(new Customer())); 
		// then		
		assertThatThrownBy(() ->
			customerService.createCustomer(createCustomerRequest))
			.isInstanceOf(CustomerEmailUnavailableException.class)
			.hasMessageContaining("The email " + createCustomerRequest.email() + " unavailable.");
	}

	@Test
	void sholdThrowNotFoundWhenGivenInvalidIdWhileUpdateCustomer() {
		//given
		long id = 5L;
		String name = "Doris Pilar";
		String email = "pilaxis@gmail.com";
		String address = "Ñaña";
		//when
		//then
		assertThatThrownBy(() ->
			customerService.updateCustomer(id, name, email, address))
		.isInstanceOf(CustomerNotFoundException.class)
		.hasMessage("Customer with id " + id + " doesn't found");
		
		verify(customerRepository, never()).save(any());
	}

	@Test
	@Disabled
	void testDeleteCustomer() {
		fail("Not yet implemented");
	}

}
