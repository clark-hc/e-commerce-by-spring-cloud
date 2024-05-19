package com.example.orderservice.service.impls;

public class InsufficientStockException extends RuntimeException {
	public InsufficientStockException(String message) {
		super(message);
	}
}