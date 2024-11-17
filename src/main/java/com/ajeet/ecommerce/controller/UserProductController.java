package com.ajeet.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ajeet.ecommerce.exception.ProductException;
import com.ajeet.ecommerce.exception.UserException;
import com.ajeet.ecommerce.model.Cart;
import com.ajeet.ecommerce.model.Product;
import com.ajeet.ecommerce.model.User;
import com.ajeet.ecommerce.service.ProductService;

import io.micrometer.core.ipc.http.HttpSender.Response;

@RestController
@RequestMapping("/api")
public class UserProductController {
	
	@Autowired
	private ProductService productService;
	
// find product by category
	@GetMapping("/product")
	public ResponseEntity<Page<Product>> findProductByCategory(@RequestParam String category,@RequestParam List<String>color,
			@RequestParam List<String> size,@RequestParam Integer minPrice,
			@RequestParam Integer maxPrice,@RequestParam Integer minDiscount,
			@RequestParam String sort,@RequestParam String stock,@RequestParam Integer pageNumber,
			@RequestParam Integer pageSize){
		System.out.println("controller"+category+"category"+minPrice+"minPrice"+maxPrice+"maxPrice"+minDiscount+"minDiscount"+sort+"sort"+stock+"stock"+pageNumber+"pageNumber"+pageSize+"pageSize");

		Page<Product> res = productService.getAllProduct(category, color, size, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);
		return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
		
	}
// find product by id
	@GetMapping("/{ProductId}")
	public ResponseEntity<Product>findProductById(@PathVariable Long ProductId)throws ProductException{
Product product = productService.findProductById(ProductId)	;
		return new ResponseEntity<Product>(product,HttpStatus.ACCEPTED); 
		
	}
}
