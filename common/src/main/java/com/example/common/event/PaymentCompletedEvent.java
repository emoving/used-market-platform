package com.example.common.event;

public record PaymentCompletedEvent(
	String sagaId,
	String eventId,
	Long orderId,
	Long productId
) {}
