package com.example.common.dto;

public record PaymentReservationResponse(
	Long orderId,
	Long productId,
	Integer price) {
}
