package com.ntloc.test_junit.customer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerRepositoryTest {

	@Autowired
	CustomerRepository underTest;
	
	@Test
	void shouldReturnCustomerWhenFindByEmail() {
		//given
		String email = "salfriadez@gmail.com";
		Customer customer = Customer.create(
				"Sebastian Alfredo", 
				email, 
				"San Francisco Mz E Lt 2"); 
		underTest.save(customer);
		//when
		Optional<Customer> customerByEmail = underTest.findByEmail(email);
		//then
		assertThat(customerByEmail).isPresent();
	}

}
