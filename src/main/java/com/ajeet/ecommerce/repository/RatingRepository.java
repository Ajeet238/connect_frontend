package com.ajeet.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ajeet.ecommerce.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
	
	@Query("SELECT r from Rating r where r.products.id = :productId ")
	public List<Rating> getAllProductsRating(@Param("productId")Long productId);
}
