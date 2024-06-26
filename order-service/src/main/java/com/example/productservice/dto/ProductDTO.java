package com.example.productservice.dto;

import java.math.BigDecimal;

public class ProductDTO {

	private Long id;
	private String name;
	private BigDecimal price;
	private Integer stock;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer quantity) {
		this.stock = quantity;
	}

}