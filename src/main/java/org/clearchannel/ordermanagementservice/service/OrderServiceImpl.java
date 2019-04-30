package org.clearchannel.ordermanagementservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.clearchannel.ordermanagementservice.dao.OrderRepository;
import org.clearchannel.ordermanagementservice.entity.Cart;
import org.clearchannel.ordermanagementservice.entity.Order;
import org.clearchannel.ordermanagementservice.entity.Product;
import org.clearchannel.ordermanagementservice.entity.Customer;
import org.clearchannel.ordermanagementservice.request.CartRequest;
import org.clearchannel.ordermanagementservice.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	ICustomerService customerService;

	@Autowired
	ICheckoutService checkoutService;

	@Autowired
	CartServiceImpl cartServiceImpl;
	
	@Autowired
	IProductService productService;
	
	@Autowired
	ICartService cartService;

	@Override
	public List<Order> getAllOrdersForCustomer(long customerId) throws Exception {

		List<Order> orderList = null;
		Optional<Customer> customer = customerService.getCustomerById(customerId);

		if (customer.isPresent())
			orderList = customer.get().getOrderList();
		else
			throw new Exception("Customer does not exist");

		return orderList;
	}

	@Override
	public Order createOrder(CartRequest cartRequest) throws Exception {
		Order order = null;
		long customerId = cartRequest.getCustomerId();

		if(cartService.addProduct(cartRequest)) {
			order = checkoutService.performCheckout(customerId);
		} 
		
		else {
			throw new Exception("Unable to create Order,please check the request");
		}
		return order;

	}

	@Override
	public void update(Order order) {
		this.orderRepository.save(order);

	}

	@Override
	public Order getOrderById(UUID orderId) {
		return orderRepository.findById(orderId);
	}

	public void linkOrderWithCustomer(UUID orderId, Customer customer) {

		List<Order> orderList = new ArrayList<>();
		if (customer.getOrderList() != null) {
			orderList = customer.getOrderList();
			orderList.add(orderRepository.findById(orderId));
			customer.setOrderList(orderList);
		}

		else {
			orderList = Arrays.asList(orderRepository.findById(orderId));
			customer.setOrderList(orderList);

		}

		customer.setCustomerCart(null);
		customerService.updateCustomer(customer);

	}

}
