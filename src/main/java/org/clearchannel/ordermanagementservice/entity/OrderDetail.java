package org.clearchannel.ordermanagementservice.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class OrderDetail {

	@Id
	private UUID orderDetailId=UUID.randomUUID();

	private UUID  orderId;

	@ElementCollection
	@MapKeyJoinColumn(name="product_id")
    @Column(name="products")
	private Map<Product, Integer> productMap;
	
	public UUID getOrderDetailId() {
		return orderDetailId;
	}

	public UUID getOrderId() {
		return orderId;
	}

	public Map<Product, Integer> getProductMap() {
		return productMap;
	}

	public String getAddress() {
		return address;
	}

	private String address;

	public OrderDetail() {
	}

	public OrderDetail(UUID orderId, Customer  customer) {
		this.orderId = orderId;
		this.productMap = new HashMap<Product, Integer>(customer.getCustomerCart().getProductMap());
		this.address = customer.getCustomerAddress();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((orderDetailId == null) ? 0 : orderDetailId.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((productMap == null) ? 0 : productMap.hashCode());
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
		OrderDetail other = (OrderDetail) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (orderDetailId == null) {
			if (other.orderDetailId != null)
				return false;
		} else if (!orderDetailId.equals(other.orderDetailId))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (productMap == null) {
			if (other.productMap != null)
				return false;
		} else if (!productMap.equals(other.productMap))
			return false;
		return true;
	}




	
}
