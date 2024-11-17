package com.ajeet.ecommerce.service;

import java.util.List;

import com.ajeet.ecommerce.exception.ProductException;
import com.ajeet.ecommerce.model.Rating;
import com.ajeet.ecommerce.model.User;
import com.ajeet.ecommerce.request.RatingRequest;

public interface RatingService {

	public Rating createRating (RatingRequest req, User user) throws ProductException;
	public List<Rating> getProductsRating(Long productId);
}
