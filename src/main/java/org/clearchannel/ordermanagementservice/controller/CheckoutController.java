package org.clearchannel.ordermanagementservice.controller;

import org.clearchannel.ordermanagementservice.entity.Order;
import org.clearchannel.ordermanagementservice.request.CheckoutRequest;
import org.clearchannel.ordermanagementservice.service.ICheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oms/api/v1")
public class CheckoutController {

	@Autowired 
	ICheckoutService checkoutService;
	
	@RequestMapping(value = "/checkout", method = RequestMethod.POST )
	public Order performCheckout(@RequestBody CheckoutRequest checkoutRequest) throws Exception {

		
		return checkoutService.performCheckout(checkoutRequest.getCustomerId());
		
	}
	
}
