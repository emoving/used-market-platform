package com.example.product.adapter.in;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.common.KafkaTopics;
import com.example.common.event.OrderCreatedEvent;
import com.example.common.event.PaymentCompletedEvent;
import com.example.common.event.PaymentFailedEvent;
import com.example.product.application.in.ProductUseCase;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductEventConsumer {

	private final ProductUseCase productUseCase;

	@KafkaListener(topics = KafkaTopics.ORDER_CREATED, groupId = "product-group")
	public void onOrderCreated(OrderCreatedEvent event) {
		productUseCase.reserveProduct(
			event.orderId(),
			event.productId(),
			event.sagaId(),
			event.eventId()
		);
	}

	@KafkaListener(topics = KafkaTopics.PAYMENT_COMPLETED, groupId = "product-group")
	public void onPaymentCompleted(PaymentCompletedEvent event) {
		productUseCase.completeSale(
			event.orderId(),
			event.productId(),
			event.sagaId(),
			event.eventId()
		);
	}

	@KafkaListener(topics = KafkaTopics.PAYMENT_FAILED, groupId = "product-group")
	public void onPaymentFailed(PaymentFailedEvent event) {
		productUseCase.cancelReservation(
			event.productId(),
			event.sagaId(),
			event.eventId()
		);
	}
}