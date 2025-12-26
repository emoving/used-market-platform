package com.example.product.adapter.in;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.product.application.in.ProductUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductUseCase productService;

	@PostMapping
	public ResponseEntity<Void> register(@RequestBody ProductRequest request) {
		productService.registerProduct(request);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}