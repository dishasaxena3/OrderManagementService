package org.clearchannel.ordermanagementservice.request;

public class CheckoutRequest {

	private long customerId;

	public CheckoutRequest() {
	}

	public CheckoutRequest(long customerId) {
		super();
		this.customerId = customerId;

	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getCustomerId() {
		return customerId;
	}

}
