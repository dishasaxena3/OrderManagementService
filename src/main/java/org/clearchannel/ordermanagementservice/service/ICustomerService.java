package org.clearchannel.ordermanagementservice.service;

import java.util.List;
import java.util.Optional;

import org.clearchannel.ordermanagementservice.entity.Customer;



public interface ICustomerService {

	public List<Customer> getAllCustomers();
	
	public Optional<Customer> getCustomerById(long customerId);
	
	public Customer createCustomer(Customer regularCustomer);
	
	public Customer updateCustomer(Customer customer);
	
	public void deleteCustomerById(long customerId);
	
	public void deleteAllCustomers();
	
}
