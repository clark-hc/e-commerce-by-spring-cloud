package com.example.orderservice.service.impls;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.orderservice.client.ProductFeignClient;
import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.entity.Order;
import com.example.orderservice.exception.ResourceNotFoundException;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductFeignClient productFeignClient;

	@Override
	@Transactional(readOnly = false)
	public OrderDTO createOrder(OrderDTO orderDTO) {
		// 使用Ribbon进行客户端负载均衡,调用product-service检查库存
		ResponseEntity<Integer> response = productFeignClient.getStockByProductId(orderDTO.getProductId());
		if (response.getBody() < orderDTO.getQuantity()) {
			throw new InsufficientStockException("库存不足");
		}

		// 创建订单实体
		Order order = new Order();
		order.setUserId(orderDTO.getUserId());
		order.setProductId(orderDTO.getProductId());
		order.setQuantity(orderDTO.getQuantity());
		order.setStatus("CREATED");

		// 保存订单
		order = orderRepository.save(order);

		// 减少产品库存
		productFeignClient.decreaseStock(orderDTO.getProductId(), orderDTO.getQuantity());

		return mapToDTO(order);
	}

	@Override
	public OrderDTO getOrderById(Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
		return mapToDTO(order);
	}

	@Override
	public List<OrderDTO> getAllOrders() {
		List<Order> orders = orderRepository.findAll();
		return orders.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = false)
	public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
		Order existingOrder = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

		existingOrder.setUserId(orderDTO.getUserId());
		existingOrder.setProductId(orderDTO.getProductId());
		existingOrder.setQuantity(orderDTO.getQuantity());
		existingOrder.setStatus(orderDTO.getStatus());

		Order updatedOrder = orderRepository.save(existingOrder);
		return mapToDTO(updatedOrder);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteOrder(Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
		orderRepository.delete(order);
	}

	private OrderDTO mapToDTO(Order order) {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(order.getId());
		orderDTO.setUserId(order.getUserId());
		orderDTO.setProductId(order.getProductId());
		orderDTO.setQuantity(order.getQuantity());
		orderDTO.setStatus(order.getStatus());
		return orderDTO;
	}

	private Order mapToEntity(OrderDTO orderDTO) {
		Order order = new Order();
		order.setUserId(orderDTO.getUserId());
		order.setProductId(orderDTO.getProductId());
		order.setQuantity(orderDTO.getQuantity());
		order.setStatus(orderDTO.getStatus());
		return order;
	}
}