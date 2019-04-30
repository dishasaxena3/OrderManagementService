# OrderManagementService
A simple order management system for ABC online shop, where customer should be able to purchase items online. 2. The System should allow user to add items to a basket. 3. The System should allow user to delete items from a basket. 4. The System should allow user to create a new order 5. The System should be able to list all orders for a customer 6. The System should be able to fetch a specific order



This service is created taking  AMAZON as the base.
A person can create an order by adding products in cart .
There is One-to-one mapping between customer and cart.
A cart will be saved within customer account till the time it is successfully checkout.
A customer can add items and delete items from the cart.
A customer can have many orders,so there is one to many mapping between Customer and Order.
orders have associated orderDetails.
Checkout functionality has payment section which is currently not implemented.

API Calls :

POST http://localhost:8080/oms/api/v1/products
{
	"name":"Product1",
	"price":"20.0",
	"description":"Healthy Product",
	"quantity":40
	
}

GET http://localhost:8080/oms/api/v1/orders/243aefea-4855-4988-b17f-eac515c08a29

POST http://localhost:8080/oms/api/v1/orders/customer/1
{
		"customerId": "1",
		"quantity":"21",
	"product":{
		"id":"2"
	
	}
}

GET localhost:8080/oms/api/v1/customer/1

POST localhost:8080/oms/api/v1/checkout

{
        "customerId": "1"

}

POST localhost:8080/oms/api/v1/cart/addProduct
{
		"customerId": "1",
		"quantity":"10",
	"product":{
		"id":"1"
	
	}
}

