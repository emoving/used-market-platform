package com.example.paymentservice.adapter.out;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.example.common.KafkaTopics;
import com.example.common.event.PaymentCompletedEvent;
import com.example.common.event.PaymentFailedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentEventPublisher {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handlePaymentCompleted(PaymentCompletedEvent event) {
		kafkaTemplate.send(KafkaTopics.PAYMENT_COMPLETED, event.sagaId(), event);
	}

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
	public void handlePaymentFailed(PaymentFailedEvent event) {
		kafkaTemplate.send(KafkaTopics.PAYMENT_FAILED, event.sagaId(), event);
	}
}