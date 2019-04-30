package org.clearchannel.ordermanagementservice.dao;

import org.clearchannel.ordermanagementservice.entity.Cart;
import org.clearchannel.ordermanagementservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
	
	Cart findByCustomerId(long customerId);

}
