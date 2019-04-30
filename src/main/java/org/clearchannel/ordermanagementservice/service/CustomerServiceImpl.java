package org.clearchannel.ordermanagementservice.service;

import java.util.List;
import java.util.Optional;

import org.clearchannel.ordermanagementservice.dao.CustomerRepository;
import org.clearchannel.ordermanagementservice.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements ICustomerService{
	@Autowired
	CustomerRepository customerRepository;

	@Override
	public List<Customer> getAllCustomers() {
	
		return customerRepository.findAll();
	}

	@Override
	public Optional<Customer> getCustomerById(long customerId) {
		return  customerRepository.findById(customerId);
	}

	@Override
	public Customer createCustomer(Customer regularCustomer) {
		return customerRepository.save(regularCustomer);
	}

	@Override
	public Customer updateCustomer(Customer customer) {
	
		return customerRepository.save(customer);
	}

	@Override
	public void deleteCustomerById(long customerId) {
	customerRepository.deleteById(customerId);
		
	}

	@Override
	public void deleteAllCustomers() {

		customerRepository.deleteAll();
	}
	
	
	

}
