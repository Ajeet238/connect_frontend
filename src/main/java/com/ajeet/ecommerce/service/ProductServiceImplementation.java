package com.ajeet.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ajeet.ecommerce.exception.ProductException;
import com.ajeet.ecommerce.model.Category;
import com.ajeet.ecommerce.model.Product;
import com.ajeet.ecommerce.repository.CategoryRepository;
import com.ajeet.ecommerce.repository.ProductRepository;
import com.ajeet.ecommerce.request.CreateProductRequest;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductServiceImplementation implements ProductService {

	@Autowired
	public ProductRepository productRepository;
	
	@Autowired
	public CategoryRepository categoryRepository;
	
//	@Autowired
//	public Product product;
	
	public Product createProduct(CreateProductRequest req) {
		Category topLevelCategory = categoryRepository.findByName(req.getTopLevelCategory());
		
		if(topLevelCategory == null) { 
			Category topLevel = new Category();
			topLevel.setName(req.getTopLevelCategory());
			topLevel.setLevel(1);
			categoryRepository.save(topLevel);
			topLevelCategory = categoryRepository.findByName(req.getTopLevelCategory());
		}
		
		Category secondLevelCategory = categoryRepository.findByNameAndParentCategory_Name(req.getSecondLevelCategory(), topLevelCategory.getName());

		if(secondLevelCategory == null) { 
			Category secondLevel = new Category();
			secondLevel.setName(req.getSecondLevelCategory());
			secondLevel.setLevel(2);
			secondLevel.setParentCategory(topLevelCategory);

			categoryRepository.save(secondLevel);
			secondLevelCategory = categoryRepository.findByNameAndParentCategory_Name(req.getSecondLevelCategory(), topLevelCategory.getName());

		}
		Category thirdLevelCategory = categoryRepository.findByNameAndParentCategory_Name(req.getThirdLevelCategory(), secondLevelCategory.getName());
	
		if(thirdLevelCategory == null) { 
			Category thirdLevel = new Category();
			thirdLevel.setName(req.getThirdLevelCategory());
			thirdLevel.setLevel(3);
			thirdLevel.setParentCategory(secondLevelCategory);
			categoryRepository.save(thirdLevel);
			 thirdLevelCategory = categoryRepository.findByNameAndParentCategory_Name(req.getThirdLevelCategory(), secondLevelCategory.getName());

		}
		Product product = new Product();
		product.setTitle(req.getTitle());
		product.setDescription(req.getDescription());
		product.setBrand(req.getBrand());
		product.setCategory(thirdLevelCategory);
		product.setColor(req.getColor());
		product.setDiscountedPrice(req.getDiscountedPrice());
		product.setDiscountPresent(req.getDiscountPresent());
		product.setImageUrl(req.getImageUrl());
		product.setPrice(req.getPrice());
		product.setQuantity(req.getQuantity());
		product.setCreatedAt(LocalDateTime.now());
		product.setSizes(req.getSize());
		
		Product savedProduct = productRepository.save(product);
		return savedProduct;
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {
		Optional<Product> deleteProductOptional = productRepository.findById(productId);
	
		Product deleteProduc = deleteProductOptional.orElseThrow(() -> new EntityNotFoundException("User not found"));
		deleteProduc.getSizes().clear();
		productRepository.delete(deleteProduc);
		
		return "Product deleted successfully";
	}

	@Override
	public Product updateProduct(Long productId, Product req) throws ProductException {
		Optional<Product> productOptional = productRepository.findById(productId);
		
		Product updateProduc = productOptional.orElseThrow(() -> new EntityNotFoundException("Product not found with ID: "+ productId));
		updateProduc.setTitle(req.getTitle());
		updateProduc.setDescription(req.getDescription());
		updateProduc.setBrand(req.getBrand());
		updateProduc.setCategory(req.getCategory());
		updateProduc.setColor(req.getColor());
		updateProduc.setDiscountedPrice(req.getDiscountedPrice());
		updateProduc.setDiscountPresent(req.getDiscountPresent());
		updateProduc.setImageUrl(req.getImageUrl());
		updateProduc.setPrice(req.getPrice());
		updateProduc.setQuantity(req.getQuantity());
		updateProduc.setCreatedAt(LocalDateTime.now());
		
		Product saveUpdateProduct = productRepository.save(updateProduc);
		return saveUpdateProduct;
	}

	@Override
	public Product findProductById(Long id) throws ProductException {
		Optional <Product> opt = productRepository.findById(id);
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new ProductException("Product not found with id"+ id);
		
		
	}

	@Override
	public List<Product> findProductByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
		System.out.println("controller"+category+"category"+minPrice+"minPrice"+maxPrice+"maxPrice"+minDiscount+"minDiscount"+sort+"sort"+stock+"stock"+pageNumber+"pageNumber"+pageSize+"pageSize");

		Pageable pageble =  (Pageable) PageRequest.of(pageNumber, pageSize);
		List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);
		System.out.println("controller"+category+"category"+minPrice+"minPrice"+maxPrice+"maxPrice"+minDiscount+"minDiscount"+sort+"sort"+stock+"stock"+pageNumber+"pageNumber"+pageSize+"pageSize");
		// product with given list of color
		
		if(!colors.isEmpty()) {
			products = products.stream().filter(p -> colors.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
		}
	// product with in stock/out of stock 
		
		if(stock != null) {
			if(stock.equals("in_stock")) {
				products = products.stream().filter(p -> p.getQuantity()>0).collect(Collectors.toList()) ;
			}else if(stock.equals("out_of_stock")) {
				products = products.stream().filter(p -> p.getQuantity()<1).collect(Collectors.toList()) ;
			}
		}
	
		int startIndex = (int) pageble.getOffset(); // This line calculates the start index of the current page by getting the offset from the Pageable object
		int endIndex = Math.min(startIndex + pageble.getPageSize(), products.size());
		List<Product> pageContent = products.subList(startIndex, endIndex);
 
	//	The line of code you've provided is creating a new Page object containing the filtered products.		
		Page<Product> filteredProducts = new PageImpl<>(pageContent,pageble,products.size());
		return filteredProducts;
	}

	@Override
	public List<Product> findAllProduct() {
		List<Product> productList = productRepository.findAll();
		return productList;
	}

	
}
