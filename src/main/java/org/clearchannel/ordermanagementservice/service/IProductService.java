package org.clearchannel.ordermanagementservice.service;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.clearchannel.ordermanagementservice.entity.Product;
import org.clearchannel.ordermanagementservice.errorhandler.OrderServiceException;

public interface IProductService {


	
	@NotNull List<Product> getAllProducts();

    Optional<Product> getProduct(@Min(value = 1L, message = "Invalid product ID.") long id) throws OrderServiceException;

    Product save(Product product);
    
    Product updateProduct(Product product);
    
    public void deleteProductById(long productId);
	public void deleteAllProduct();
}
