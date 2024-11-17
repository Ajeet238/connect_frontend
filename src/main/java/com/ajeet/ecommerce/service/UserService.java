package com.ajeet.ecommerce.service;

import com.ajeet.ecommerce.exception.UserException;
import com.ajeet.ecommerce.model.User;

public interface UserService {
	public User findUserById(Long userId)throws UserException;
	public User findUserProfileByJwt(String jwt)throws UserException;

}
