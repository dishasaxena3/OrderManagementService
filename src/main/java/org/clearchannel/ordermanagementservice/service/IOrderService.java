package org.clearchannel.ordermanagementservice.service;

import java.util.List;
import java.util.UUID;

import org.clearchannel.ordermanagementservice.entity.Order;
import org.clearchannel.ordermanagementservice.request.CartRequest;
import org.clearchannel.ordermanagementservice.request.OrderRequest;

public interface IOrderService {

	   public List<Order> getAllOrdersForCustomer(long customer) throws Exception ;
	   public Order getOrderById(UUID orderId);
	   
	   public Order createOrder(CartRequest cartRequest) throws Exception;
	   public void update(Order order);
	
}
