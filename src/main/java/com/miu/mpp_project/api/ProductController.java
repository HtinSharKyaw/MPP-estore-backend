package com.miu.mpp_project.api;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miu.mpp_project.model.Product;
import com.miu.mpp_project.service.ProductService;
/**
 * @author HtinSharKyaw
 *
 */
@RestController
@RequestMapping(value = "/api/products")
public class ProductController {
	private final ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping("/getProducts")
	public List<Product> getProducts(){
		return productService.getProducts();
	}
	
	//ToDo (HtinShar will have to change this code to PostMapping,later)   
	@PostMapping("/save")
	public boolean postProduct(@RequestBody Product product ) {
		try {
//			Product product1 = new Product("bike", 2000, "https://bike image sample url");
//			Product product2 = new Product("cars", 50000, "https://cars image sample url");
//			Product product3 = new Product("laptops", 3000, "https://laptops image sample url");
//			productService.addProduct(product1);
//			productService.addProduct(product2);
			productService.addProduct(product);
			return true;
		}catch (Exception e) {
			System.out.println("Some errors in saving the data");
			return false;
		}
	
	}
}
