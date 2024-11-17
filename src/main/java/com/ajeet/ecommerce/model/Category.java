package com.ajeet.ecommerce.model;

import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	
	private String name;
	
	// parentCategory represents the parent category of a given category.
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_category_id")
	private Category parentCategory;
	
	private int level;
}
