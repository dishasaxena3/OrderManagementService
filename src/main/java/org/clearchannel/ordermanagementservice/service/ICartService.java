package org.clearchannel.ordermanagementservice.service;

import java.util.Map;

import org.clearchannel.ordermanagementservice.entity.Product;
import org.clearchannel.ordermanagementservice.entity.Customer;
import org.clearchannel.ordermanagementservice.request.CartRequest;

public interface ICartService {

	public boolean addProduct(CartRequest cartRequest) throws Exception;
	boolean deleteProduct(CartRequest cartRequest);
	public boolean updateProduct(CartRequest cartRequest);
	public Map<Product, Integer> getAllProducts(long customerId);
	public double totalPrice(Map<Product, Integer> products);
	
}
