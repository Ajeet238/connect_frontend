package com.ajeet.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajeet.ecommerce.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
	
}
