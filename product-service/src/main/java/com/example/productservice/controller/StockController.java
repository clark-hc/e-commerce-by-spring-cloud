package com.example.productservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.productservice.exception.InsufficientStockException;
import com.example.productservice.exception.ResourceNotFoundException;
import com.example.productservice.service.ProductService;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

	private final ProductService productService;

	public StockController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/{productId}")
	public ResponseEntity<Integer> getStockByProductId(@PathVariable Long productId) {
		try {
			//
			try {
				int sleepTimes = System.currentTimeMillis() % 2 == 0 ? 10 : 1000; // 使用sleep达到随机超时的目的, 触发Hystrix生效
				Thread.sleep(sleepTimes);
				System.out.println("@@@--->>>Sleep: " + sleepTimes + " ms");
			} catch (InterruptedException e) {
			}
			//
			int stock = productService.getStockByProductId(productId);
			return ResponseEntity.ok(stock);
		} catch (ResourceNotFoundException ex) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{productId}/decrease/{quantity}")
	public ResponseEntity<Void> decreaseStock(@PathVariable Long productId, @PathVariable int quantity) {
		try {
			productService.decreaseStock(productId, quantity);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			return ResponseEntity.notFound().build();
		} catch (InsufficientStockException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}