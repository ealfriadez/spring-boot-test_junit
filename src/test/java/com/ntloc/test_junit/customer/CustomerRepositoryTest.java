package com.ntloc.test_junit.customer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest {

	@Container
	@ServiceConnection
	static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.2"));
	@Autowired
	CustomerRepository underTest;

	@BeforeEach
	void setUp() {
		String email = "salfriadez@gmail.com";
		Customer customer = Customer.create(
				"Sebastian Alfredo",
				email,
				"San Francisco Mz E Lt 2");
		underTest.save(customer);
	}

	@AfterEach
	void tearDown() {
		underTest.deleteAll();
	}

	@Test
	void canEstablishedConnection(){
		assertThat(postgreSQLContainer.isCreated()).isTrue();
		assertThat(postgreSQLContainer.isRunning()).isTrue();
	}

	@Test
	void shouldReturnCustomerWhenFindByEmail() {
		//given
		//when
		Optional<Customer> customerByEmail = underTest.findByEmail("salfriadez@gmail.com");
		//then
		assertThat(customerByEmail).isPresent();
	}

	@Test
	void shouldNotCustomerWhenFindByEmailIsNoPresent() {
		//given
		//when
		Optional<Customer> customerByEmail = underTest.findByEmail("ealfriadez@gmail.com");
		//then
		assertThat(customerByEmail).isNotPresent();
	}
}
