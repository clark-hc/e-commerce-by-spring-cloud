package com.example.productservice.service;

import java.util.List;

import com.example.productservice.dto.ProductDTO;

public interface ProductService {

	ProductDTO createProduct(ProductDTO productDTO);

	ProductDTO getProductById(Long id);

	List<ProductDTO> getAllProducts();

	ProductDTO updateProduct(Long id, ProductDTO productDTO);

	void deleteProduct(Long id);

	int getStockByProductId(Long id);

	void decreaseStock(Long productId, int quantity);

}