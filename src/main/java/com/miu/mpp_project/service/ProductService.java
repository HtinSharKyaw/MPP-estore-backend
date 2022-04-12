package com.miu.mpp_project.service;

import java.util.List;

import com.miu.mpp_project.enums.ProductStatusEnum;
import com.miu.mpp_project.enums.ResultEnum;
import com.miu.mpp_project.exceptions.MyException;
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

	public Product findOne(Integer productId) {
		Product product = productRepository.getById(productId);
		return product;
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


	public void increaseStock(Integer productId, int amount) {
		Product product = findOne(productId);
		if (product == null) throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);

		int update = product.getProductStock() + amount;
		product.setProductStock(update);
		productRepository.save(product);
	}

	public void decreaseStock(Integer productId, int amount) {
		Product product = findOne(productId);
		if (product == null) throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);

		int update = product.getProductStock() - amount;
		if(update <= 0) throw new MyException(ResultEnum.PRODUCT_NOT_ENOUGH );

		product.setProductStock(update);
		productRepository.save(product);
	}

	public Product offSale(Integer productId) {
		Product product = findOne(productId);
		if (product == null) throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);

		if (product.getProductStatus() == ProductStatusEnum.DOWN.getCode()) {
			throw new MyException(ResultEnum.PRODUCT_STATUS_ERROR);
		}

		product.setProductStatus(ProductStatusEnum.DOWN.getCode());
		return productRepository.save(product);
	}

	public Product onSale(Integer productId) {
		Product product = findOne(productId);
		if (product == null) throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);

		if (product.getProductStatus() == ProductStatusEnum.UP.getCode()) {
			throw new MyException(ResultEnum.PRODUCT_STATUS_ERROR);
		}

		product.setProductStatus(ProductStatusEnum.UP.getCode());
		return productRepository.save(product);
	}

	public Product update(Product product) {

		if(product.getProductStatus() > 1) {
			throw new MyException(ResultEnum.PRODUCT_STATUS_ERROR);
		}

		return productRepository.save(product);
	}

	public void delete(Integer productId) {
		Product productInfo = findOne(productId);
		if (productInfo == null) throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);
		productRepository.delete(productInfo);

	}



}
