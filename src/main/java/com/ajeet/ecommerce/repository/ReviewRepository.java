package com.ajeet.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ajeet.ecommerce.model.Rating;
import com.ajeet.ecommerce.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Query("SELECT r from Review r where r.products.id = :productId ")
	public List<Review> getAllProductsReview(@Param("productId")Long productId);

}
