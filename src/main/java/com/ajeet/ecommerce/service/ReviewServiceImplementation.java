package com.ajeet.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajeet.ecommerce.exception.ProductException;
import com.ajeet.ecommerce.exception.UserException;
import com.ajeet.ecommerce.model.Product;
import com.ajeet.ecommerce.model.Review;
import com.ajeet.ecommerce.model.User;
import com.ajeet.ecommerce.repository.ProductRepository;
import com.ajeet.ecommerce.repository.ReviewRepository;
import com.ajeet.ecommerce.request.ReviewRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class ReviewServiceImplementation implements ReviewService {

	@Autowired
	public ReviewRepository reviewRepository;
	
	@Autowired
	public ProductService productService;
	
	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException, UserException {
		Product product = productService.findProductById(req.getProductId());
		Review review = new Review();
		review.setUser(user);
		review.setProducts(product);
		review.setReview(req.getReview());
		review.setCreatedAt(LocalDateTime.now());
		
		
		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getAllReview(Long productId) {
		// TODO Auto-generated method stub
		return reviewRepository.getAllProductsReview(productId);
	}
	
}
