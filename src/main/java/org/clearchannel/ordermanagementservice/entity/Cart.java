package org.clearchannel.ordermanagementservice.entity;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cart")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@OneToOne(mappedBy="customerCart")
	private Customer customer;
	
	private long customerId;

	@ElementCollection
	@MapKeyJoinColumn(name="product_id")
    @Column(name="products")
	private Map<Product, Integer> productMap;

	
	public Cart( long customerId, Map<Product, Integer> productMap) {
		super();
		this.customerId = customerId;
		this.productMap = productMap;
	}

	public Cart() {
	}

	public long getCustomerId() {
		return customerId;
	}

	public Map<Product, Integer> getProductMap() {
		return productMap;
	}

	public void setProductMap(Map<Product, Integer> productMap) {
		this.productMap = productMap;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (customerId ^ (customerId >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Cart other = (Cart) obj;
		if (customerId != other.customerId)
			return false;
		if (id != other.id)
			return false;
		if (productMap == null) {
			if (other.productMap != null)
				return false;
		} else if (!productMap.equals(other.productMap))
			return false;
		return true;
	}

	
	@Override
	public String toString() {
		return "Cart [id=" + id + ", customer=" + customer + ", customerId=" + customerId + ", productMap=" + productMap
				+ "]";
	}
	

}
