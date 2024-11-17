package com.ajeet.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajeet.ecommerce.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
