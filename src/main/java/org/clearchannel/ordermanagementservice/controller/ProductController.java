package org.clearchannel.ordermanagementservice.controller;

import java.util.List;
import java.util.Optional;

import org.clearchannel.ordermanagementservice.entity.Product;
import org.clearchannel.ordermanagementservice.errorhandler.OrderServiceException;
import org.clearchannel.ordermanagementservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oms/api/v1")
public class ProductController {

	@Autowired
	IProductService productService;

	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public List<Product> getAllProducts() throws Exception {

		System.out.println(this.getClass().getSimpleName() + " Getting all products");

		return productService.getAllProducts();
	}

	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public Product addProduct(@RequestBody Product product) {

		return productService.save(product);

	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
	public Product getProduct(@PathVariable long id) throws OrderServiceException {

		Optional<Product> product = productService.getProduct(id);
		if (!product.isPresent()) {
			throw new OrderServiceException("Could not find product with id :" + id);

		}

		return product.get();

	}

	
	@RequestMapping(value="/products/update/{id}",method=RequestMethod.PUT)
	public Product updateProduct(@RequestBody Product updatedProduct,@PathVariable long id) throws OrderServiceException {
		System.out.println(this.getClass().getSimpleName()+" update Product called with emp"+updatedProduct.toString()+ " and productId is "+id);
		Optional<Product> product=productService.getProduct(id);
		if(!product.isPresent()) {
			throw new OrderServiceException("Can't update , Product not found");
		}
		
		product.get().setQuantity(updatedProduct.getQuantity());

		return productService.updateProduct(product.get());
	}
	
	
	@RequestMapping(value = "/products/delete/{id}", method = RequestMethod.DELETE)
	public void deleteById(@PathVariable long id) throws Exception {
		System.out.println(this.getClass().getSimpleName() + " delete product called with productId is " + id);
		Optional<Product> product = productService.getProduct(id);
		if (!product.isPresent()) {
			throw new Exception("Product not present with id :" + id);
		}

		productService.deleteProductById(id);

	}

	@RequestMapping(value = "/products/deleteAll", method = RequestMethod.DELETE)
	public void deleteAllProducts() {
		System.out.println(this.getClass().getSimpleName() + " delete all products");
		productService.deleteAllProduct();

	}

}
