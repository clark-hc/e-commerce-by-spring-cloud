package com.example.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "product-service")
public interface ProductFeignClient {

    @GetMapping("/api/stocks/{productId}")
    ResponseEntity<Integer> getStockByProductId(@PathVariable Long productId);

    @PutMapping("/api/stocks/{productId}/decrease/{quantity}")
    ResponseEntity<Void> decreaseStock(@PathVariable Long productId, @PathVariable int quantity);
}