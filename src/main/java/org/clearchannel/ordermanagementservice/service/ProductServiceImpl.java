package org.clearchannel.ordermanagementservice.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.clearchannel.ordermanagementservice.dao.ProductRepository;
import org.clearchannel.ordermanagementservice.entity.Product;
import org.clearchannel.ordermanagementservice.errorhandler.OrderServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductServiceImpl implements IProductService {

	@Autowired
	ProductRepository productRepository;

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Optional<Product> getProduct(long id) throws OrderServiceException {

		return productRepository.findById(id);

	}

	@Override
	public Product save(Product product) {
		return productRepository.save(product);
	}

	@Override
	public void deleteProductById(long productId) {

		productRepository.deleteById(productId);

	}

	@Override
	public void deleteAllProduct() {

		productRepository.deleteAll();

	}

	@Override
	public Product updateProduct(Product product) {

		return productRepository.save(product);
	}

}
