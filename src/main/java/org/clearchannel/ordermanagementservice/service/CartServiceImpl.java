package org.clearchannel.ordermanagementservice.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.clearchannel.ordermanagementservice.dao.CartRepository;
import org.clearchannel.ordermanagementservice.dao.CustomerRepository;
import org.clearchannel.ordermanagementservice.entity.Cart;
import org.clearchannel.ordermanagementservice.entity.Product;
import org.clearchannel.ordermanagementservice.entity.Customer;
import org.clearchannel.ordermanagementservice.request.CartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements ICartService {

	@Autowired
	CartRepository cartRepository;

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	IProductService productService;
	private Map<Product, Integer> productMap = new HashMap();

	@Override
	public boolean addProduct(CartRequest cartRequest) throws Exception {
		Optional<Product> addedProduct = productService.getProduct(cartRequest.getProduct().getId());

		if (addedProduct.isPresent()) {
			if (addedProduct.get().getQuantity() >= cartRequest.getQuantity()) {
				this.productMap.put(addedProduct.get(), cartRequest.getQuantity());

				Customer customer = getCustomer(cartRequest);
				if (customer != null) {
					Cart cart = getCustomerCart(customer);
					cartRepository.save(cart);
					customer.setCustomerCart(cart);
					customerRepository.save(customer);
				} else {
					throw new Exception("for customer with id " + cartRequest.getCustomerId() + " cart cannot be updated");
				}

				return true;
			} else
				System.out.println(String.format("requested quantity %s for product %s is not available",
						cartRequest.getProduct().getQuantity(), addedProduct.get()));

		} else {
			System.out.println(String.format("product %s not availble", addedProduct.get()));
		}

		return false;
	}

	/**
	 * This method deletes a product from customer cart and does the update on cart
	 * and customer entities
	 * 
	 * @param cartRequest:
	 *            cart object with product delete
	 * @return
	 */
	@Override
	public boolean deleteProduct(CartRequest cartRequest) {

		Customer customer = getCustomer(cartRequest);
		if (customer != null) {
			Cart customerCart = customer.getCustomerCart();
			Map<Product, Integer> productMap = customerCart!=null ? customerCart.getProductMap() : null;
			if (productMap!=null && productMap.containsKey(cartRequest.getProduct().getId())) {
				productMap.remove(cartRequest.getProduct());
				customerCart.setProductMap(productMap);
				cartRepository.save(customerCart);
				customer.setCustomerCart(customerCart);
				customerRepository.save(customer);

				return true;
			}
		}

		return false;
	}

	/**
	 * This method updates a cart , can increase or decrease the quantity for a
	 * product
	 * 
	 * @param cartRequest:
	 *            cart object with product quantity to update
	 */
	@Override
	public boolean updateProduct(CartRequest cartRequest) {

		Customer customer = getCustomer(cartRequest);
		if (customer != null) {
			Cart customerCart = customer.getCustomerCart();
			Map<Product, Integer> productMap = customerCart!=null ? customerCart.getProductMap() :  null;
			if (productMap!=null && productMap.containsKey(cartRequest.getProduct().getId())) {
				productMap.put(cartRequest.getProduct(), cartRequest.getQuantity());
				customerCart.setProductMap(productMap);
				cartRepository.save(customerCart);
				customer.setCustomerCart(customerCart);
				customerRepository.save(customer);
			}
		}

		return false;

	}

	@Override
	public Map<Product, Integer> getAllProducts(long customerId) {

		Cart cart = cartRepository.findByCustomerId(customerId);

		return cart.getProductMap();
	}

	@Override
	public double totalPrice(Map<Product, Integer> products) {

		return products.entrySet().stream().mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue()).sum();

	}

	private Customer getCustomer(CartRequest cartRequest) {

		Optional<Customer> regularCustomer = customerRepository.findById(cartRequest.getCustomerId());

		if (regularCustomer.isPresent())

			return regularCustomer.get();

		return null;
	}

	private Cart getCustomerCart(Customer customer) {

		Cart cart = customer.getCustomerCart();

		if (cart == null) {
			cart = new Cart(customer.getCustomerId(), productMap);

		} else {
			Map<Product, Integer> result = new HashMap<>(cart.getProductMap());
			result.putAll(this.productMap);
			cart.setProductMap(result);
		}

		return cart;
	}
	
	public Cart saveCart(Cart cart) {
		return cartRepository.save(cart);
		
	}
}
