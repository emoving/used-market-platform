package com.example.common.event;

public record OrderCreatedEvent(
	String sagaId,
	String eventId,
	Long orderId,
	Long productId,
	Integer price
) {
}
