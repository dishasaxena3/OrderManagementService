package org.clearchannel.ordermanagementservice.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;
	private String customerName;
	private String customerAddress;
	@Enumerated(EnumType.STRING)
	private CustomerType customerType;
	@Transient
	private IPaymentStrategy paymentStrategy;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cart_id", referencedColumnName = "id")
	private Cart customerCart;

	@OneToMany(mappedBy = "customer")
	private List<Order> orderList;

	public Customer(String customerName, String customerAddress, Cart customerCart) {
		super();
		this.customerName = customerName;
		this.customerAddress = customerAddress;
		this.customerCart = customerCart;
	}

	public Customer() {

	}

	public void setCustomerCart(Cart customerCart) {
		this.customerCart = customerCart;
	}

	public Cart getShoppingCart() {
		return customerCart;
	}

	public List<Order> getCustomerOrders() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public IPaymentStrategy getPaymentStrategy() {
		return paymentStrategy;
	}

	public Cart getCustomerCart() {
		return customerCart;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerAddress == null) ? 0 : customerAddress.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
		result = prime * result + ((customerType == null) ? 0 : customerType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (customerAddress == null) {
			if (other.customerAddress != null)
				return false;
		} else if (!customerAddress.equals(other.customerAddress))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (customerName == null) {
			if (other.customerName != null)
				return false;
		} else if (!customerName.equals(other.customerName))
			return false;
		if (customerType != other.customerType)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RegularCustomer [customerId=" + customerId + ", customerName=" + customerName + ", customerAddress="
				+ customerAddress + ", customerType=" + customerType + ", paymentStrategy=" + paymentStrategy
				+ ", customerCart=" + customerCart + ", orderList=" + orderList + "]";
	}

}
