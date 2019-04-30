package org.clearchannel.ordermanagementservice.service;

import org.clearchannel.ordermanagementservice.entity.Cart;
import org.clearchannel.ordermanagementservice.entity.Order;
import org.clearchannel.ordermanagementservice.entity.Customer;

public interface ICheckoutService {

	
	Order performCheckout(long customerId);
	
	Order createOrder(Customer customer );
	
	
}
