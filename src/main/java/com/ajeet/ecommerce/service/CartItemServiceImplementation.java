package com.ajeet.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajeet.ecommerce.exception.CartItemException;
import com.ajeet.ecommerce.exception.UserException;
import com.ajeet.ecommerce.model.Cart;
import com.ajeet.ecommerce.model.CartItem;
import com.ajeet.ecommerce.model.Product;
import com.ajeet.ecommerce.model.User;
import com.ajeet.ecommerce.repository.CartItemRepository;
import com.ajeet.ecommerce.repository.CartRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Service
public class CartItemServiceImplementation implements CartItemService {
	@Autowired
	private CartItemRepository cartItemRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private CartRepository cartRepository;
	
	@Override
	public CartItem createCartItem(CartItem cartItem) {
		cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
		cartItem.setDiscountedPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
		
		CartItem createdCartItem = cartItemRepository.save(cartItem);
		return createdCartItem;
		
	}

	@Override
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
		CartItem cart_Item = findCartItembyId(id);
		
		User user = userService.findUserById(cart_Item.getUserId());
		
		if(user.getId() == userId) {
			cart_Item.setQuantity(cartItem.getQuantity());
			cart_Item.setPrice(cartItem.getQuantity()*cartItem.getProduct().getPrice());
			cart_Item.setDiscountedPrice(cartItem.getQuantity()*cartItem.getProduct().getDiscountedPrice());
		}
		return cartItemRepository.save(cart_Item);
	}

	@Override
	public CartItem isCartItemExist(Cart cart, Product product, String size, long userId) {
		
		CartItem CartItem = cartItemRepository.isCartItemExist(cart, product, size, userId);
		
			return CartItem;
	}

	@Override
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
			CartItem cart_Item = findCartItembyId(cartItemId);
		
		User user = userService.findUserById(cart_Item.getUserId());
		User reqUser = userService.findUserById(userId);
		if(user.getId() == reqUser.getId()) {
			cartItemRepository.deleteById(cartItemId);
		}
		else {
		throw new UserException("You can't remover another user item");
		}
	}

	@Override
	public CartItem findCartItembyId(Long cartItemId) throws CartItemException {
		Optional<CartItem> cart_ItemOpt = cartItemRepository.findById(cartItemId);
		if(cart_ItemOpt.isPresent()) {
			return cart_ItemOpt.get();
		}
		throw new CartItemException("CartItem not found with id:" +cartItemId );
	}

}
