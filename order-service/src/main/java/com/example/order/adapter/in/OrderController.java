package com.example.order.adapter.in;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.dto.PaymentReservationResponse;
import com.example.order.application.in.OrderUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderUseCase orderUseCase;

	@PostMapping
	public ResponseEntity<Long> createOrder(@RequestBody OrderRequest request) {
		String sagaId = UUID.randomUUID().toString();

		long orderId = orderUseCase.createOrder(request.productId(), request.price(), sagaId);

		return ResponseEntity.ok(orderId);
	}

	@PostMapping("/{orderId}")
	public PaymentReservationResponse pend(@PathVariable Long orderId, @RequestHeader("X-Saga-Id") String sagaId) {
		return orderUseCase.pend(orderId, sagaId);
	}
}
