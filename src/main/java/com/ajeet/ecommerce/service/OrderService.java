package com.ajeet.ecommerce.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ajeet.ecommerce.exception.orderException;
import com.ajeet.ecommerce.exception.ProductException;
import com.ajeet.ecommerce.model.Address;
import com.ajeet.ecommerce.model.Order;
import com.ajeet.ecommerce.model.Product;
import com.ajeet.ecommerce.model.User;
import com.ajeet.ecommerce.request.CreateProductRequest;

public interface OrderService {
	
	public Order createOrder(User user, Address shippingAddress);
	public Order findOrderById(Long id) throws orderException;
	public List<Order> userOrderHistory(Long id);
	public Order placedOrder(Long orderId) throws orderException;
	public Order confirmedOrder(Long orderId) throws orderException;
	public Order shippedOrder(Long orderId) throws orderException;
	public Order deliveredOrder(Long orderId) throws orderException;
	public Order canceledOrder(Long orderId) throws orderException;
	public List<Order> getAllOrders();
	public void deleteOrder(Long orderId) throws orderException;
	public Page<Product> getAllProduct(String category,List<String>colors, List<String>sizes,Integer minPrice, Integer maxPrice,Integer minDiscount,String sort,String stock,Integer pageNumber, Integer paheSize);

}
