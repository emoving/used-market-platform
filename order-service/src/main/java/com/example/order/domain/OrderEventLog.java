package com.example.order.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderEventLog {

	@Id
	private String eventId;

	private String sagaId;

	private LocalDateTime processedAt;

	public static OrderEventLog of(String eventId, String sagaId) {
		return new OrderEventLog(eventId, sagaId, LocalDateTime.now());
	}
}