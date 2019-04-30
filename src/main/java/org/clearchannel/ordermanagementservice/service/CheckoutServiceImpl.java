package org.clearchannel.ordermanagementservice.service;

import java.util.Map;

import org.clearchannel.ordermanagementservice.dao.CustomerRepository;
import org.clearchannel.ordermanagementservice.dao.OrderDetailRepository;
import org.clearchannel.ordermanagementservice.entity.Order;
import org.clearchannel.ordermanagementservice.entity.OrderStatus;
import org.clearchannel.ordermanagementservice.entity.Product;
import org.clearchannel.ordermanagementservice.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckoutServiceImpl implements ICheckoutService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	IProductService productService;
	
	@Autowired
	OrderServiceImpl orderService;
	
	
	@Autowired
	IOrderDetailService orderDetailService;

	@Override
	public Order performCheckout(long customerId) {
		Order order=null;
		Customer customer = customerRepository.findById(customerId).get();

		if (takePayment()) {

			// Update product quantity in database
			updateProductQuantity(customer.getCustomerCart().getProductMap());

			 order=createOrder(customer);
			
			if (order!=null) {
				// clear the customer cart now
				
				orderService.linkOrderWithCustomer(order.getId(), customer);
				
		
			
		}

			return order;
		}
		return order;
	}

	/**
	 * This method will update the product quantity in repository
	 * 
	 * @param productMap
	 */
	private void updateProductQuantity(Map<Product, Integer> productMap) {

		productMap.entrySet().stream().forEach(entry -> {

			int quantity = entry.getKey().getQuantity() - entry.getValue();
			Product product = entry.getKey();
			product.setQuantity(quantity);
			productService.updateProduct(product);

		});
	}

	/**
	 * This method will do the payment and return boolean accordingly
	 * 
	 * @return
	 */
	private boolean takePayment() {

		// will check if payment is done , returning true else false.

		return true;
	}

	@Override
	public Order createOrder(Customer customer) {
		Order order = new Order(OrderStatus.COMPLETED, customer);
		order.setOrderDetails(customer);
		orderDetailService.saveOrder(order.getOrderDetails());
		orderService.update(order);
		
		return order;
	}

}
