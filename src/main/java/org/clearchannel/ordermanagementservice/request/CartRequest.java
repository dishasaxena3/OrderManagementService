package org.clearchannel.ordermanagementservice.request;

import org.clearchannel.ordermanagementservice.entity.Product;
import org.clearchannel.ordermanagementservice.entity.Customer;

public class CartRequest {

	private long customerId;
	private Product product;
	private int quantity;
	
	public CartRequest() {
	}
	
	public CartRequest(long customerId, Product product , int quantity) {
		super();
		this.customerId = customerId;
		this.product= product;
		this.quantity=quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (customerId ^ (customerId >>> 32));
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + quantity;
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
		CartRequest other = (CartRequest) obj;
		if (customerId != other.customerId)
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}

	public long getCustomerId() {
		return customerId;
	}
	
	public Product getProduct() {
		return product;
	}

	public int getQuantity() {
		return quantity;
	}

}
