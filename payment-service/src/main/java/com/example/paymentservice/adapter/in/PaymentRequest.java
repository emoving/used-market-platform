package com.example.paymentservice.adapter.in;

public record PaymentRequest(
	Long orderId,
	Integer amount
) {
}
