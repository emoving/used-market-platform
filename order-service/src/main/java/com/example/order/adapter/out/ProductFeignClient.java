package com.example.order.adapter.out;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.common.dto.ProductResponse;

@FeignClient(name = "product-service")
public interface ProductFeignClient {

	@GetMapping("/products/{productId}")
	ProductResponse getProduct(
		@PathVariable Long productId,
		@RequestHeader("X-Saga-Id") String sagaId
	);
}
