package com.example.order.adapter.out;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.example.common.KafkaTopics;
import com.example.common.event.OrderCreatedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleOrderCreated(OrderCreatedEvent event) {
		kafkaTemplate.send(KafkaTopics.ORDER_CREATED, event.sagaId(), event);
	}
}
