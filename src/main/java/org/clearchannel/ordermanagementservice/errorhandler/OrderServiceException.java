package org.clearchannel.ordermanagementservice.errorhandler;

public class OrderServiceException extends RuntimeException{
	
	private static final long serialVersionUID = 42L;

    public OrderServiceException() {
        super();
    }

    public OrderServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public OrderServiceException(final String message) {
        super(message);
    }

    public OrderServiceException(final Throwable cause) {
        super(cause);
    }

}
