package org.clearchannel.ordermanagementservice.controller;

import java.util.List;
import java.util.Optional;

import org.clearchannel.ordermanagementservice.entity.Customer;
import org.clearchannel.ordermanagementservice.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oms/api/v1")
public class CustomerController {

	@Autowired
	ICustomerService customerService;

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public List<Customer> getAllCustomers() {
		return customerService.getAllCustomers();
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/customer/{customerId}", method = RequestMethod.GET)
	public Optional<Customer> getCustomerById(@PathVariable long customerId) {
		return customerService.getCustomerById(customerId);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/customer", method = RequestMethod.POST)
	public Customer createCustomer(@RequestBody Customer customer) {
		return customerService.createCustomer(customer);
	}

	@RequestMapping(value = "/customer/delete/{id}", method = RequestMethod.DELETE)
	public void deleteById(@PathVariable int id) throws Exception {
		System.out.println(this.getClass().getSimpleName() + " delete Customer called with customerId: " + id);
		Optional<Customer> customer = customerService.getCustomerById(id);
		if (!customer.isPresent()) {
			throw new Exception("Employee not present with id :" + id);
		}

		customerService.deleteCustomerById(id);

	}

	@RequestMapping(value = "/customer/deleteAll", method = RequestMethod.DELETE)
	public void deleteAllCustomers() {
		System.out.println(this.getClass().getSimpleName() + " delete all Customers");
		customerService.deleteAllCustomers();

	}

}
