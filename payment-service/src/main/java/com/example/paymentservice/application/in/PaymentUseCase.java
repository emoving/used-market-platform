package com.example.paymentservice.application.in;

public interface PaymentUseCase {

	void processPayment(Long orderId, Integer price, String sagaId);
}
