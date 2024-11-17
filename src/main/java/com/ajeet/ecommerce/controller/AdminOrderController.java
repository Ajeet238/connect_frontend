package com.ajeet.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajeet.ecommerce.exception.orderException;
import com.ajeet.ecommerce.model.Order;
import com.ajeet.ecommerce.response.ApiResponse;
import com.ajeet.ecommerce.service.OrderService;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/")
	public ResponseEntity<List<Order>> getAllOrders(){
		List<Order> orders = orderService.getAllOrders();
		return new ResponseEntity<List<Order>>(orders,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{orderId}/confirmed")
	public ResponseEntity<Order> confirmedOrder(@PathVariable Long orderId, @RequestHeader("Authorization")String jwt) throws orderException{
		Order order = orderService.confirmedOrder(orderId);
		return new ResponseEntity<Order>(order,HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/ship")
	public ResponseEntity<Order> ShippedOrder(@PathVariable Long orderId, @RequestHeader("Authorization")String jwt) throws orderException{
		Order order = orderService.shippedOrder(orderId);
		return new ResponseEntity<Order>(order,HttpStatus.ACCEPTED);

	}
	
	@PutMapping("/{orderId}/deliver")
	public ResponseEntity<Order> deliVerOrder(@PathVariable Long orderId, @RequestHeader("Authorization")String jwt) throws orderException{
		Order order = orderService.deliveredOrder(orderId);
		return new ResponseEntity<>(order,HttpStatus.ACCEPTED);

	}
	
	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<Order> cancelOrder(@PathVariable Long orderId, @RequestHeader("Authorization")String jwt) throws orderException{
		Order order = orderService.canceledOrder(orderId);
		return new ResponseEntity<>(order,HttpStatus.ACCEPTED);

	}
	
	@DeleteMapping("/{orderId}/delete")
	public ResponseEntity<ApiResponse> deleteOrder(@PathVariable Long orderId, @RequestHeader("Authorization")String jwt) throws orderException{
		 orderService.deleteOrder(orderId);
		ApiResponse res = new ApiResponse();
		res.setMsg("Order Deleted Successfully");
		res.setStatus(true);
		return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);

	}
}
