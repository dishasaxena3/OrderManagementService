package org.clearchannel.ordermanagementservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.clearchannel.ordermanagementservice.BaseTestIT;
import org.clearchannel.ordermanagementservice.entity.Cart;
import org.clearchannel.ordermanagementservice.entity.Product;
import org.clearchannel.ordermanagementservice.request.CartRequest;
import org.junit.Test;
public class CartServiceImplTest extends BaseTestIT {

	@Transactional
	@Test
	public void whenAddProductToCartIsCalled() throws Exception {
		
		Product product = productServiceImpl.getProduct(1L).get();
		Map<Product,Integer> map = new HashMap<Product, Integer>();
		map.put(product, 10);
		CartRequest cartRequest = new CartRequest(1L, product, 10);
		boolean retrivedResult = cartServiceImpl.addProduct(cartRequest);
		assertEquals(true, retrivedResult);
		Cart cart = customerServiceImpl.getCustomerById(1L).get().getCustomerCart();
		assertEquals(cart.getProductMap(),map);
		

	}
	
	@Transactional
	@Test
	public void addProductToCartWithQuantityMoreThanAvailable() throws Exception {

		Product product = productServiceImpl.getProduct(1L).get();
		CartRequest cartRequest = new CartRequest(1L, product, 40);
		boolean retrivedResult = cartServiceImpl.addProduct(cartRequest);

		assertEquals(false, retrivedResult);
		assertNull(customerServiceImpl.getCustomerById(1L).get().getCustomerCart());

	}
	
	@Test
	public void deleteProductForACustomerNegativeTest() {
		Product product = productServiceImpl.getProduct(1L).get();
		CartRequest cartRequest = new CartRequest(2L, product, 10);
		assertEquals(false, cartServiceImpl.deleteProduct(cartRequest));
		
	}

	@Test
	public void deleteProductForACustomerPositiveTest() throws Exception {
		Product product = productServiceImpl.getProduct(2L).get();
		CartRequest cartRequest = new CartRequest(3L, product, 10);
		cartServiceImpl.addProduct(cartRequest);
		assertEquals(true, cartServiceImpl.deleteProduct(cartRequest));
		
	}
}
