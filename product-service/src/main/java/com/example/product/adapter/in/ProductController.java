package com.example.product.adapter.in;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.dto.ProductResponse;
import com.example.product.application.in.ProductUseCase;
import com.example.product.domain.Product;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductUseCase productUseCase;

	@GetMapping("/{productId}")
	public ProductResponse find(@PathVariable Long productId,
		@RequestHeader("X-Saga-Id") String sagaId) {
		Product product = productUseCase.getProduct(productId);

		return new ProductResponse(product.getId(), product.getPrice());
	}

	@PostMapping
	public ResponseEntity<Void> register(@RequestBody ProductRequest request) {
		productUseCase.registerProduct(request);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}