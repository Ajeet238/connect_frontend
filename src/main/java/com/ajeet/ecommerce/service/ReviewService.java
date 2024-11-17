package com.ajeet.ecommerce.service;

import java.util.List;

import com.ajeet.ecommerce.exception.ProductException;
import com.ajeet.ecommerce.exception.UserException;
import com.ajeet.ecommerce.model.Review;
import com.ajeet.ecommerce.model.User;
import com.ajeet.ecommerce.request.ReviewRequest;

public interface ReviewService {
	public Review createReview(ReviewRequest req, User user) throws ProductException,UserException;
	public List<Review> getAllReview(Long productId);
}
