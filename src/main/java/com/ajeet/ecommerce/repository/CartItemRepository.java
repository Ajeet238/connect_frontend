package com.ajeet.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ajeet.ecommerce.model.Cart;
import com.ajeet.ecommerce.model.CartItem;
import com.ajeet.ecommerce.model.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	
	@Query("SELECT ci from CartItem ci where ci.cart = :cart AND ci.product = :product AND ci.size = :size AND ci.userId = :userId")
	public CartItem isCartItemExist(@Param ("cart") Cart cart,@Param ("product") Product product, @Param ("size")String size, @Param ("userId") Long userId);
	
}
