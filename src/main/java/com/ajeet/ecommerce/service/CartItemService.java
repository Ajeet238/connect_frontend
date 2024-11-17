package com.ajeet.ecommerce.service;

import com.ajeet.ecommerce.exception.CartItemException;
import com.ajeet.ecommerce.exception.UserException;
import com.ajeet.ecommerce.model.Cart;
import com.ajeet.ecommerce.model.CartItem;
import com.ajeet.ecommerce.model.Product;

public interface CartItemService {
	public CartItem createCartItem(CartItem cartItem);
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;
	public CartItem isCartItemExist(Cart cart, Product product, String size, long userId);
	public void removeCartItem(Long userId,Long cartItemId)throws CartItemException, UserException;
	public CartItem findCartItembyId(Long cartItemId)throws CartItemException;
	
}
