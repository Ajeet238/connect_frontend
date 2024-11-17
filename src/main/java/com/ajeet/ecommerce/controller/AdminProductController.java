package com.ajeet.ecommerce.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajeet.ecommerce.exception.ProductException;
import com.ajeet.ecommerce.exception.orderException;
import com.ajeet.ecommerce.model.Order;
import com.ajeet.ecommerce.model.Product;
import com.ajeet.ecommerce.request.CreateProductRequest;
import com.ajeet.ecommerce.response.ApiResponse;
import com.ajeet.ecommerce.service.OrderService;
import com.ajeet.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api/admin/product")
public class AdminProductController {
	
	@Autowired
	private ProductService productService;

	// create product
	@PostMapping("/")
	public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req){
		Product product = productService.createProduct(req);
		return new ResponseEntity<Product>(product,HttpStatus.CREATED);
	}
	
	// get all product
	@GetMapping("/all")
	public ResponseEntity<List<Product>> getAllProducts(){
		List<Product> product = productService.findAllProduct();
		return new ResponseEntity<List<Product>>(product,HttpStatus.OK);
	}
	
// update Product	
	@PutMapping("/{productId}/update")
	public ResponseEntity<Product> updateProduct(@RequestBody Product req, @PathVariable Long productId) throws ProductException{
		Product product = productService.updateProduct(productId, req);
		return new ResponseEntity<>(product,HttpStatus.CREATED);

	}
	
//	@PostMapping("/creates")
//	public ResponseEntity<Order> createMultipleProduct(@RequestBody CreateProductRequest req) throws ProductException{
//		Order order = productService;
//		return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
//
//	}
	
	// delete Product
	@DeleteMapping("/{productIdId}/delete")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) throws ProductException{
		productService.deleteProduct(productId);
		ApiResponse res = new ApiResponse();
		res.setMsg("Product Deleted Successfully");
		res.setStatus(true);
		return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);

	}
}
