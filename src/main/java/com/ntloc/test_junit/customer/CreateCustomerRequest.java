package com.ntloc.test_junit.customer;

public record CreateCustomerRequest(String name,
                                    String email,
                                    String address) {
}
