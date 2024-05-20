package com.example.orderservice.service.contracts.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.example.productservice.dto.ProductDTO;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = "com.example:product-service:+:stubs:8090")
@DirtiesContext
public class ProductServiceContractTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeEach
	public void setup() {
		RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
	}

	private RestTemplate restTemplate = new RestTemplate();

	@Test
	public void shouldReturnProductDetails() {
		Long productId = 1l;

		ProductDTO productDTO = restTemplate.getForObject(//
				"http://localhost:8090/api/products/{productId}", ProductDTO.class, productId//
		);

		assertEquals(productId, productDTO.getId());
		assertEquals("Product 1", productDTO.getName());
		assertEquals(BigDecimal.valueOf(19.99), productDTO.getPrice());
		assertSame(10, productDTO.getStock());
	}

}