package com.example.order.adapter.in;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.common.KafkaTopics;
import com.example.common.event.PaymentFailedEvent;
import com.example.common.event.ProductSoledEvent;
import com.example.order.application.in.OrderUseCase;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

	private final OrderUseCase orderUseCase;

	@KafkaListener(topics = KafkaTopics.PRODUCT_SOLD, groupId = "order-group")
	public void onProductSoled(ProductSoledEvent event) {
		orderUseCase.completeOrder(
			event.orderId(),
			event.sagaId(),
			event.eventId()
		);
	}

	@KafkaListener(topics = KafkaTopics.PAYMENT_FAILED, groupId = "order-group")
	public void onPaymentFailed(PaymentFailedEvent event) {
		orderUseCase.cancelOrder(
			event.orderId(),
			event.sagaId(),
			event.eventId()
		);
	}
}
