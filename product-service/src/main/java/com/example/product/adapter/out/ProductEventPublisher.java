package com.example.product.adapter.out;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.example.common.KafkaTopics;
import com.example.common.event.ProductCreatedEvent;
import com.example.common.event.ProductReservedEvent;
import com.example.common.event.ProductSoledEvent;
import com.example.common.event.ProductStatusChangedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductEventPublisher {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleProductReserved(ProductReservedEvent event) {
		kafkaTemplate.send(KafkaTopics.PRODUCT_RESERVED, event.sagaId(), event);
	}

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleProductSoled(ProductSoledEvent event) {
		kafkaTemplate.send(KafkaTopics.PRODUCT_SOLD, event.sagaId(), event);
	}

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleProductCreated(ProductCreatedEvent event) {
		kafkaTemplate.send(KafkaTopics.PRODUCT_SEARCH_SYNC, String.valueOf(event.productId()), event);
	}

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleStatusChanged(ProductStatusChangedEvent event) {
		kafkaTemplate.send(KafkaTopics.PRODUCT_SEARCH_SYNC, String.valueOf(event.productId()), event);
	}
}
