package org.clearchannel.ordermanagementservice.controller;

import java.util.List;
import java.util.UUID;

import org.clearchannel.ordermanagementservice.dao.CustomerRepository;
import org.clearchannel.ordermanagementservice.dao.OrderRepository;
import org.clearchannel.ordermanagementservice.entity.Order;
import org.clearchannel.ordermanagementservice.errorhandler.OrderServiceException;
import org.clearchannel.ordermanagementservice.request.CartRequest;
import org.clearchannel.ordermanagementservice.request.OrderRequest;
import org.clearchannel.ordermanagementservice.service.CartServiceImpl;
import org.clearchannel.ordermanagementservice.service.ICartService;
import org.clearchannel.ordermanagementservice.service.ICheckoutService;
import org.clearchannel.ordermanagementservice.service.ICustomerService;
import org.clearchannel.ordermanagementservice.service.IOrderDetailService;
import org.clearchannel.ordermanagementservice.service.IOrderService;
import org.clearchannel.ordermanagementservice.service.IProductService;
import org.clearchannel.ordermanagementservice.service.OrderServiceImpl;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/oms/api/v1/orders")
public class OrderController {
	@Autowired
	IOrderService orderService;

	@GetMapping
	@RequestMapping(value = "/customer/{customerId}", method = RequestMethod.GET)
	public List<Order> getAllOrdersForCustomer(@PathVariable long customerId) throws Exception {
		return orderService.getAllOrdersForCustomer(customerId);
	}

	/**
	 * This method will take the cart request and directly goes for checkout for the
	 * existing customer
	 * 
	 * @param cartRequest
	 * @return
	 * @throws OrderServiceException
	 */
	@PostMapping
	public ResponseEntity<Order> createOrder(@RequestBody CartRequest cartRequest) throws Exception {

		Order order = new Order();

		order = this.orderService.createOrder(cartRequest);
		String uri = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/orders/{id}")
				.buildAndExpand(order.getId()).toString();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", uri);

		return new ResponseEntity<>(order, HttpStatus.CREATED);
	}

	@GetMapping
	@RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
	public ResponseEntity<Order> getOrderById(@PathVariable UUID orderId) {
		Order order = orderService.getOrderById(orderId);

		return new ResponseEntity<>(order, HttpStatus.OK);
	}

}
