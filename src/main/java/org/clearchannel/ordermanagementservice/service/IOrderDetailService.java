package org.clearchannel.ordermanagementservice.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.clearchannel.ordermanagementservice.entity.OrderDetail;
import org.springframework.validation.annotation.Validated;

@Validated
public interface IOrderDetailService {

	//OrderDetail create(@NotNull(message = "The products for order cannot be null.") @Valid OrderDetail orderDetail);
	
	
	OrderDetail saveOrder(OrderDetail orderDetail);
	
}
