package com.example.common.event;

public record ProductSoledEvent(
	String sagaId,
	String eventId,
	Long orderId,
	Long productId
) {
}
