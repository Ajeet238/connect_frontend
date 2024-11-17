package com.ajeet.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajeet.ecommerce.exception.ProductException;
import com.ajeet.ecommerce.model.Cart;
import com.ajeet.ecommerce.model.CartItem;
import com.ajeet.ecommerce.model.Product;
import com.ajeet.ecommerce.model.User;
import com.ajeet.ecommerce.repository.CartRepository;
import com.ajeet.ecommerce.request.AddItemRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Service
public class CartServiceImplementation implements CartService {
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private ProductService productService;
	
	@Override
	public Cart createCart(User user) {
		Cart newCart = new Cart();
		newCart.setUser(user);
		return cartRepository.save(newCart);
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
		Cart cart = cartRepository.findByUserId(userId);
		Product product = productService.findProductById(req.getProductId());
		CartItem isCartItemPresent = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);
		System.out.println(isCartItemPresent + "isCartItemPresent");
		if(isCartItemPresent == null) {
			CartItem cartItem = new CartItem();
			cartItem.setCart(cart);
			cartItem.setProduct(product);
			cartItem.setSize(req.getSize());
			cartItem.setQuantity(req.getQuantity());;
			cartItem.setUserId(userId);
			cartItem.setPrice(product.getDiscountedPrice()*req.getQuantity());
			
			// 
			CartItem createdCartItem = cartItemService.createCartItem(cartItem);
			cart.getCartItems().add(createdCartItem);
		}
		
		return "Item Added to Cart";
	}

	@Override
	public Cart findUserCart(Long userId) {
		Cart cart = cartRepository.findByUserId(userId);
		
		 int totalPrice = 0;
		 int totalItem = 0;
		 int totalDiscountedPrice = 0;
		
		 for(CartItem cartItem: cart.getCartItems()) {
			totalPrice += cartItem.getPrice(); 
			totalDiscountedPrice += cartItem.getDiscountedPrice();
			totalItem += cartItem.getQuantity();
		 }
		 
		
		 cart.setTotalItem(totalItem);
		 cart.setTotalPrice(totalPrice);
		 cart.setTotalDiscountedPrice(totalDiscountedPrice);
		 cart.setDiscount(totalPrice - totalDiscountedPrice);
		return cart;	
	}
	
}
