package org.clearchannel.ordermanagementservice.entity;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.clearchannel.ordermanagementservice.service.IProductService;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	private UUID id = UUID.randomUUID();
	@JsonFormat(pattern = "dd/MM/yyyy")
	@CreationTimestamp
	private LocalDate dateCreated = LocalDate.now();
	private OrderStatus status;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_detail_id", referencedColumnName = "orderDetailId")
	private OrderDetail orderDetail;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	public Order() {

	}

	public Order(OrderStatus status, Customer customer) {
		super();
		this.status = status;
		this.customer = customer;
	}

	public UUID getId() {
		return this.id;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public void setOrderDetails(Customer customer) {

		OrderDetail orderDetail = new OrderDetail(this.id, customer);

		this.orderDetail = orderDetail;
	}

	public OrderDetail getOrderDetails() {
		return orderDetail;
	}

}
