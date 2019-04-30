package org.clearchannel.ordermanagementservice.dao;

import java.util.UUID;

import org.clearchannel.ordermanagementservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>{

	Order findById(UUID orderId);
	
	
}
