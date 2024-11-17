package com.ajeet.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajeet.ecommerce.exception.UserException;
import com.ajeet.ecommerce.exception.orderException;
import com.ajeet.ecommerce.model.Address;
import com.ajeet.ecommerce.model.Order;
import com.ajeet.ecommerce.model.User;
import com.ajeet.ecommerce.service.OrderService;
import com.ajeet.ecommerce.service.UserService;

@RestController
@RequestMapping("/api/orders")
public class UserOrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
// create order	
	@PostMapping("/")
	public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress,@RequestHeader("Authorization")String jwt)throws UserException{
		User user = userService.findUserProfileByJwt(jwt);
		Order order = orderService.createOrder(user, shippingAddress);
		return new ResponseEntity<Order>(order,HttpStatus.CREATED);
		
	}
// users order history
	@GetMapping("/user")
	public ResponseEntity<List<Order>> userOrderHistory(@RequestHeader("Authorization")String jwt)throws UserException{
		User user = userService.findUserProfileByJwt(jwt);
		List<Order> orderList = orderService.userOrderHistory(user.getId());
		return new ResponseEntity<List<Order>>(orderList,HttpStatus.CREATED);
		
	}
	// find orders by id
		@GetMapping("/{id}")
		public ResponseEntity<Order> findOrderById(@PathVariable("id")Long orderId, @RequestHeader("Authorization")String jwt)throws UserException, orderException{
			User user = userService.findUserProfileByJwt(jwt);
			Order order = orderService.findOrderById(orderId);
			return new ResponseEntity<Order>(order,HttpStatus.ACCEPTED);
			
		}
	
}
