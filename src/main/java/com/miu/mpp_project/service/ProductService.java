package com.miu.mpp_project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miu.mpp_project.model.Product;
import com.miu.mpp_project.repository.ProductRepository;
/**
 * @author HtinSharKyaw
 *
 */
@Service
public class ProductService {
	private final ProductRepository productRepository;

	@Autowired
	public ProductService(ProductRepository projectRepository) {
		this.productRepository = projectRepository;
	}

	public List<Product> getProducts() {
		return productRepository.findAll();
	}

	public void addProduct(Product newProduct) {
		productRepository.save(newProduct);
	}

	public void deleteProduct(Integer id) {
		boolean isExist = productRepository.existsById(id);
		if(!isExist) {
			throw new IllegalStateException(
					"Product with id" + id + "does not exists");
		}	
		productRepository.deleteById(id); 
	}

}
