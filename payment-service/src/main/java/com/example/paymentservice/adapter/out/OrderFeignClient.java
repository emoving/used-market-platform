package com.example.paymentservice.adapter.out;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.common.dto.PaymentReservationResponse;

@FeignClient(name = "order-service", url = "http://order-service:8080")
public interface OrderFeignClient {

	@PostMapping("/orders/{orderId}")
	PaymentReservationResponse pend(
		@PathVariable("orderId") Long orderId,
		@RequestHeader("X-Saga-Id") String sagaId
	);
}
