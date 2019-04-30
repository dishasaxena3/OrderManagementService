package org.clearchannel.ordermanagementservice.service;

import java.util.UUID;

import javax.transaction.Transactional;

import org.clearchannel.ordermanagementservice.dao.OrderDetailRepository;
import org.clearchannel.ordermanagementservice.entity.OrderDetail;
import org.clearchannel.ordermanagementservice.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderDetailServiceImpl implements IOrderDetailService{
	@Autowired
	ICustomerService customerService;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;

	
	@Override
	public OrderDetail saveOrder(OrderDetail orderDetail) {
		
		
		
		return this.orderDetailRepository.save(orderDetail);
	}

	
	
}
