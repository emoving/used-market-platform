package com.example.paymentservice.application;

import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.example.common.dto.PaymentReservationResponse;
import com.example.common.event.PaymentCompletedEvent;
import com.example.common.event.PaymentFailedEvent;
import com.example.paymentservice.adapter.out.OrderClientAdapter;
import com.example.paymentservice.application.in.PaymentUseCase;
import com.example.paymentservice.application.out.EventLogJpaRepository;
import com.example.paymentservice.application.out.PaymentJpaRepository;
import com.example.paymentservice.domain.Payment;
import com.example.paymentservice.domain.PaymentEventLog;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService implements PaymentUseCase {

	private final OrderClientAdapter orderClientAdapter;
	private final PaymentJpaRepository paymentRepository;
	private final EventLogJpaRepository eventLogRepository;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	public void processPayment(Long orderId, Integer amount, String sagaId) {
		String eventId = UUID.randomUUID().toString();
		Long productId = null;

		try {
			PaymentReservationResponse res = orderClientAdapter.reservePayment(orderId, sagaId);
			productId = res.productId();

			if (!amount.equals(res.price())) {
				throw new IllegalArgumentException("결제 금액 불일치");
			}

			paymentRepository.save(Payment.of(orderId, res.price()));
			eventLogRepository.save(PaymentEventLog.of(eventId, sagaId));

			eventPublisher.publishEvent(new PaymentCompletedEvent(sagaId, eventId, orderId, productId));

		} catch (Exception e) {
			eventLogRepository.save(PaymentEventLog.of(eventId, sagaId));

			eventPublisher.publishEvent(new PaymentFailedEvent(sagaId, eventId, orderId, productId));

			throw e;
		}
	}
}