package org.clearchannel.ordermanagementservice.dao;

import java.util.UUID;

import org.clearchannel.ordermanagementservice.entity.OrderDetail;
import org.springframework.data.repository.CrudRepository;

public interface OrderDetailRepository extends CrudRepository<OrderDetail, UUID> {

}
