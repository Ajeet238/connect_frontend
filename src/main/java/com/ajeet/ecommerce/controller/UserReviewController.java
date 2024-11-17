package com.ajeet.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajeet.ecommerce.exception.ProductException;
import com.ajeet.ecommerce.exception.UserException;
import com.ajeet.ecommerce.model.Rating;
import com.ajeet.ecommerce.model.Review;
import com.ajeet.ecommerce.model.User;
import com.ajeet.ecommerce.request.RatingRequest;
import com.ajeet.ecommerce.request.ReviewRequest;
import com.ajeet.ecommerce.service.RatingService;
import com.ajeet.ecommerce.service.ReviewService;
import com.ajeet.ecommerce.service.UserService;

@RestController
@RequestMapping("/api/reviews")
public class UserReviewController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReviewService reviewService;
	
// create rating
	@PostMapping("/create")
	public ResponseEntity<Review> createReview(@RequestBody ReviewRequest req,@RequestHeader("Authorization")String jwt)throws UserException,ProductException{
		User user = userService.findUserProfileByJwt(jwt);
		Review review = reviewService.createReview(req, user);
		
		return new ResponseEntity<Review>(review,HttpStatus.CREATED);
	} 
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Review>> getProductReview(@PathVariable Long productId,@RequestHeader("Authorization")String jwt)throws UserException,ProductException{
		User user = userService.findUserProfileByJwt(jwt);
		List<Review> review = reviewService.getAllReview(productId);
		return new ResponseEntity<>(review,HttpStatus.ACCEPTED);
	} 


}

