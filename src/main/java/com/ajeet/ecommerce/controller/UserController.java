package com.ajeet.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajeet.ecommerce.exception.ProductException;
import com.ajeet.ecommerce.exception.UserException;
import com.ajeet.ecommerce.model.User;
import com.ajeet.ecommerce.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
// we will show this as yes this use is logged in now	
	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization")String jwt)throws UserException,ProductException{
		User user = userService.findUserProfileByJwt(jwt);
	
		
		return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
	} 
}
