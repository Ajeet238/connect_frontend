package com.ajeet.ecommerce.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ajeet.ecommerce.exception.orderException;
import com.ajeet.ecommerce.model.Address;
import com.ajeet.ecommerce.model.Cart;
import com.ajeet.ecommerce.model.CartItem;
import com.ajeet.ecommerce.model.Order;
import com.ajeet.ecommerce.model.OrderItem;
import com.ajeet.ecommerce.model.Product;
import com.ajeet.ecommerce.model.User;
import com.ajeet.ecommerce.repository.AddressRepository;
import com.ajeet.ecommerce.repository.OrderItemRepository;
import com.ajeet.ecommerce.repository.OrderRepository;
import com.ajeet.ecommerce.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class OrderServiceImplementation implements OrderService {

	
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private CartService cartService;
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public Order createOrder(User user, Address shippingAddress) {
		shippingAddress.setUser(user);
		Address address = addressRepository.save(shippingAddress);
		user.getAddress().add(address);
		userRepository.save(user);
		
		Cart cart = cartService.findUserCart(user.getId());
		List<OrderItem> orderItems = new ArrayList<>();
		
		for(CartItem item: cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setUserId(item.getUserId());
			orderItem.setDiscountPrice(item.getDiscountedPrice());
			
			OrderItem createdOrderItem = orderItemRepository.save(orderItem);
			orderItems.add(createdOrderItem);	
		}
	
	Order createdOrder = new Order();
	createdOrder.setUser(user);
	createdOrder.setOrderItems(orderItems);
	createdOrder.setTotalPrice(cart.getTotalPrice());
	createdOrder .setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
	createdOrder.setDiscount(cart.getDiscount());
	createdOrder.setTotalItem(cart.getTotalItem());
	createdOrder.setShippingAddress(shippingAddress);
	createdOrder.setOrderDate(LocalDateTime.now());
	createdOrder.setOrderStatus("PENDING");
	createdOrder.getPaymentDetails().setStatus("PENDING");
	createdOrder.setCreatedAt(LocalDateTime.now());

	Order savedOrder = orderRepository.save(createdOrder);
	
	for(OrderItem item: orderItems) {
		item.setOrder(savedOrder);
		orderItemRepository.save(item);
	}
	return savedOrder;
	
	}
	@Override
	public Order findOrderById(Long id) throws orderException {
		Optional<Order> orderOpt = orderRepository.findById(id);
		if(orderOpt.isPresent()) {
			return orderOpt.get();
		}else {
			throw new orderException("Order not found with Id " +id );
		}
		
	}
	@Override
	public List<Order> userOrderHistory(Long id) {
		List<Order> orderHistory = orderRepository.getUserOrders(id);
		
		return orderHistory;
	}
	@Override
	public Order placedOrder(Long orderId) throws orderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetails().setStatus("COMPLETED");
		return order;
	}
	
	@Override
	public Order confirmedOrder(Long orderId) throws orderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("CONFIRMED");
		return orderRepository.save(order);
	}
	@Override
	public Order shippedOrder(Long orderId) throws orderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("SHIPPED");
		return orderRepository.save(order);
		
	}
	@Override
	public Order deliveredOrder(Long orderId) throws orderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("DELIVERED");
		return orderRepository.save(order);
	}
	@Override
	public Order canceledOrder(Long orderId) throws orderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("CANCELLED");
		return orderRepository.save(order);
	}
	@Override
	public List<Order> getAllOrders() {
		List<Order> orderList = orderRepository.findAll();

		return orderList;
	}
	@Override
	public void deleteOrder(Long orderId) throws orderException {
		Order order = findOrderById(orderId);
		orderRepository.delete(order);
	
	}
	@Override
	public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer paheSize) {
		// TODO Auto-generated method stub
		return null;
	}
}
