package com.ajeet.ecommerce.service;

import java.time.LocalDateTime;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajeet.ecommerce.exception.ProductException;
import com.ajeet.ecommerce.model.Product;
import com.ajeet.ecommerce.model.Rating;
import com.ajeet.ecommerce.model.User;
import com.ajeet.ecommerce.repository.RatingRepository;
import com.ajeet.ecommerce.repository.ReviewRepository;
import com.ajeet.ecommerce.request.RatingRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class RatingServiceImplementation implements RatingService {
	@Autowired
	private RatingRepository ratingRepository;
	
	@Autowired
	private ProductService productService;
	
	@Override
	public Rating createRating(RatingRequest req, User user) throws ProductException {
		Product product = productService.findProductById(req.getProductId());
		Rating rating = new Rating();
		rating.setProducts(product);
		rating.setUser(user);
		rating.setRatings(req.getRating());
		rating.setCreatedAt(LocalDateTime.now());
		
		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getProductsRating(Long productId) {
		 
		return ratingRepository.getAllProductsRating(productId);
	}

}
