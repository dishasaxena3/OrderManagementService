package org.clearchannel.ordermanagementservice.dao;
import org.clearchannel.ordermanagementservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
