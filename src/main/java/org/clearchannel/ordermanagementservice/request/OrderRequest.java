package org.clearchannel.ordermanagementservice.request;

import java.util.Map;

import org.clearchannel.ordermanagementservice.entity.Product;

public class OrderRequest {

	private long customerId;
	private Map<Product,Integer> productMap;
	
	public OrderRequest() {
	}

	public OrderRequest(long customerId, Map<Product, Integer> productMap) {
		super();
		this.customerId = customerId;
		this.productMap = productMap;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public Map<Product, Integer> getProductMap() {
		return productMap;
	}

	public void setProductMap(Map<Product, Integer> productMap) {
		this.productMap = productMap;
	}
	
	
	
}
