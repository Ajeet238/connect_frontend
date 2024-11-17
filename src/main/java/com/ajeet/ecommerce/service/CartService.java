package com.ajeet.ecommerce.service;

import com.ajeet.ecommerce.exception.ProductException;
import com.ajeet.ecommerce.model.Cart;
import com.ajeet.ecommerce.model.User;
import com.ajeet.ecommerce.request.AddItemRequest;

public interface CartService {
	public Cart createCart(User user);
	public String addCartItem(Long userId,AddItemRequest req) throws ProductException;
	public Cart findUserCart(Long userId);
}
