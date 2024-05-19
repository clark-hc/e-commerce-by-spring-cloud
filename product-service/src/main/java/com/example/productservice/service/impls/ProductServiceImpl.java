package com.example.productservice.service.impls;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.productservice.dto.ProductDTO;
import com.example.productservice.entity.Product;
import com.example.productservice.exception.ResourceNotFoundException;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public ProductDTO createProduct(ProductDTO productDTO) {
		Product product = mapToEntity(productDTO);
		Product savedProduct = productRepository.save(product);
		return mapToDTO(savedProduct);
	}

	@Override
	public ProductDTO getProductById(Long id) {
		Optional<Product> productOptional = productRepository.findById(id);
		if (productOptional.isPresent()) {
			return mapToDTO(productOptional.get());
		} else {
			throw new ResourceNotFoundException("Product not found with id: " + id);
		}
	}

	@Override
	public List<ProductDTO> getAllProducts() {
		List<Product> products = productRepository.findAll();
		return products.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
		Optional<Product> existingProductOptional = productRepository.findById(id);
		if (existingProductOptional.isPresent()) {
			Product existingProduct = existingProductOptional.get();
			existingProduct.setName(productDTO.getName());
			existingProduct.setPrice(productDTO.getPrice());
			existingProduct.setQuantity(productDTO.getStock());

			Product updatedProduct = productRepository.save(existingProduct);
			return mapToDTO(updatedProduct);
		} else {
			throw new ResourceNotFoundException("Product not found with id: " + id);
		}
	}

	@Override
	public void deleteProduct(Long id) {
		Optional<Product> productOptional = productRepository.findById(id);
		if (productOptional.isPresent()) {
			Product product = productOptional.get();
			productRepository.delete(product);
		} else {
			throw new ResourceNotFoundException("Product not found with id: " + id);
		}
	}

	private ProductDTO mapToDTO(Product product) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setPrice(product.getPrice());
		productDTO.setStock(product.getQuantity());
		return productDTO;
	}

	private Product mapToEntity(ProductDTO productDTO) {
		Product product = new Product();
		product.setName(productDTO.getName());
		product.setPrice(productDTO.getPrice());
		product.setQuantity(productDTO.getStock());
		return product;
	}
}