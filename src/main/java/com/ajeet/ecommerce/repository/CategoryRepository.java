package com.ajeet.ecommerce.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.ajeet.ecommerce.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	public Category findByName(String name);

	// Spring Data JPA recognizes the underscore _ notation and automatically generates the query to correctly compare the name field of the category with the provided name parameter and the name field of the parent category with the provided parentCategoryName parameter.
	@Query("SELECT c FROM Category c where c.namee = :name AND c.parentCategory.name = :parentCategoryName ")
	public Category findByNameAndParentCategory_Name(@Param("name") String name, @Param("parentCategoryName") String parentCategoryName);


}
