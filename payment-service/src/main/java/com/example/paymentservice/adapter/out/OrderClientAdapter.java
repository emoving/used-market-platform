package com.example.paymentservice.adapter.out;

import org.springframework.stereotype.Component;

import com.example.common.dto.PaymentReservationResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderClientAdapter {

	private final OrderFeignClient feign;

	public PaymentReservationResponse reservePayment(Long orderId, String sagaId) {
		return feign.pend(orderId, sagaId);
	}
}
