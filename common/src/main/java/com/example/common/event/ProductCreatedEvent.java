package com.example.common.event;

public record ProductCreatedEvent(
	String eventId,
	Long productId,
	String title,
	Integer price,
	String category
) {

}
