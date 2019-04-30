package org.clearchannel.ordermanagementservice.entity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.clearchannel.ordermanagementservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;

import groovy.lang.Tuple2;

public class CheckOut {

	private Customer customer;
	private Cart cart;
	IPaymentStrategy paymentStrategy;
	@Autowired
	IProductService productService;

	public CheckOut(Customer customer, Cart cart, IPaymentStrategy paymentStrategy) {

		this.customer = customer;
		this.cart = cart;
		this.paymentStrategy = paymentStrategy;

		if (checkOrderFeasibility().size() > 0) {
			ammendOrder();
		} else {
			boolean isPaymentDone = takePayment();

			if (isPaymentDone)
				placeOrder();

		}

	}

	private void placeOrder() {

	}

	private boolean takePayment() {

		System.out.println("your payment iss succesfull");

		return true;
	}

	private void ammendOrder() {

		System.out.println("reduce the quantity of product");

	}

	private List<Tuple2<Product, Integer>> checkOrderFeasibility() {

		return cart.getProductMap().entrySet().stream().map(entry -> {

			// int availableQuantity = entry.getKey().getQuantity();
			if (entry.getKey().getQuantity() <= entry.getValue())
				return new Tuple2<Product, Integer>(entry.getKey(), entry.getKey().getQuantity());

			return null;

		}).collect(Collectors.toList());

	}

}
