package com.ajeet.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajeet.ecommerce.model.User;

// long is the type of ID in user
public interface UserRepository extends JpaRepository<User, Long> {
	// naming convention must be in camelCase
	public User findByEmail(String email);
	
}
