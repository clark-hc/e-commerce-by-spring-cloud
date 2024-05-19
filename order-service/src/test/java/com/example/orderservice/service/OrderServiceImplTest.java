package com.example.orderservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.entity.Order;
import com.example.orderservice.exception.ResourceNotFoundException;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.impls.OrderServiceImpl;

@SpringBootTest
@Transactional
public class OrderServiceImplTest {

	@Autowired
	private OrderServiceImpl orderService;

	@Autowired
	private OrderRepository orderRepository;

	@Test
	void testCreateOrder() {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setUserId(1L);
		orderDTO.setProductId(1L);
		orderDTO.setQuantity(2);
		orderDTO.setStatus("CREATED");

		OrderDTO createdOrderDTO = orderService.createOrder(orderDTO);

		assertNotNull(createdOrderDTO.getId());
		assertEquals(orderDTO.getUserId(), createdOrderDTO.getUserId());
		assertEquals(orderDTO.getProductId(), createdOrderDTO.getProductId());
		assertEquals(orderDTO.getQuantity(), createdOrderDTO.getQuantity());
		assertEquals(orderDTO.getStatus(), createdOrderDTO.getStatus());
	}

	@Test
	void testGetOrderById() {
		Order order = new Order();
		order.setUserId(1L);
		order.setProductId(1L);
		order.setQuantity(2);
		order.setStatus("CREATED");
		Order savedOrder = orderRepository.save(order);

		OrderDTO orderDTO = orderService.getOrderById(savedOrder.getId());

		assertNotNull(orderDTO);
		assertEquals(savedOrder.getId(), orderDTO.getId());
		assertEquals(savedOrder.getUserId(), orderDTO.getUserId());
		assertEquals(savedOrder.getProductId(), orderDTO.getProductId());
		assertEquals(savedOrder.getQuantity(), orderDTO.getQuantity());
		assertEquals(savedOrder.getStatus(), orderDTO.getStatus());
	}

	@Test
	void testGetOrderByIdNotFound() {
		assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(999L));
	}

	@Test
	void testGetAllOrders() {
		Order order1 = new Order();
		order1.setUserId(1L);
		order1.setProductId(1L);
		order1.setQuantity(2);
		order1.setStatus("CREATED");
		orderRepository.save(order1);

		Order order2 = new Order();
		order2.setUserId(2L);
		order2.setProductId(2L);
		order2.setQuantity(1);
		order2.setStatus("PAID");
		orderRepository.save(order2);

		List<OrderDTO> orderDTOs = orderService.getAllOrders();

		assertEquals(2, orderDTOs.size());
	}

}