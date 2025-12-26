package com.example.common.event;

public record PaymentFailedEvent(
	String sagaId,
	String eventId,
	Long orderId,
	Long productId
) {}