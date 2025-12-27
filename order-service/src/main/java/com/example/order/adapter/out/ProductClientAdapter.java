package com.example.order.adapter.out;

import org.springframework.stereotype.Component;

import com.example.common.dto.ProductResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductClientAdapter {

	private final ProductFeignClient feignClient;

	public ProductResponse getProduct(Long orderId, String sagaId) {
		return feignClient.getProduct(orderId, sagaId);
	}
}
