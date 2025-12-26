package com.example.common.event;

public record ProductReservedEvent(
	String sagaId,
	String eventId,
	Long productId,
	Long orderId,
	Integer price
) {}