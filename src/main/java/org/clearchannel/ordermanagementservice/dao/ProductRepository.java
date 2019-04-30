package org.clearchannel.ordermanagementservice.dao;

import org.clearchannel.ordermanagementservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
