package com.example.common.event;

public record ProductStatusChangedEvent(
	String eventId,
	Long productId,
	String status
) {}