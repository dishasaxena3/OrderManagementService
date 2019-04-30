package org.clearchannel.ordermanagementservice.controller;

import java.util.Map;

import org.clearchannel.ordermanagementservice.entity.Cart;
import org.clearchannel.ordermanagementservice.entity.Product;
import org.clearchannel.ordermanagementservice.entity.Customer;
import org.clearchannel.ordermanagementservice.request.CartRequest;
import org.clearchannel.ordermanagementservice.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/oms/api/v1/cart")
public class CartController {

	@Autowired
	ICartService cartService;
	
	@RequestMapping(value = "/addProduct", method = RequestMethod.PUT )
	public boolean addProduct(@RequestBody  CartRequest cartRequest) throws Exception {

		
		return cartService.addProduct(cartRequest);
		
	}
	
	@RequestMapping(value = "/deleteProduct", method = RequestMethod.DELETE )
	public boolean deleteProduct(@RequestBody CartRequest cartRequest) {

		return cartService.deleteProduct(cartRequest);
		
	}
	
	@RequestMapping(value = "/updateProduct", method = RequestMethod.PUT )
	public boolean updateProduct(@RequestBody CartRequest cartRequest) {

		return cartService.updateProduct(cartRequest);
		
	}
	
	
	@GetMapping
	@RequestMapping(value = "/{customerId}", method = RequestMethod.GET )
	public Map<Product,Integer> getAllProducts(@PathVariable long customerId){
		return cartService.getAllProducts(customerId);
	}
	
}
