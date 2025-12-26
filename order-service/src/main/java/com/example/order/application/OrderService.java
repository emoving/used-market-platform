package com.example.order.application;

import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.example.common.dto.PaymentReservationResponse;
import com.example.common.event.OrderCreatedEvent;
import com.example.order.application.in.OrderUseCase;
import com.example.order.application.out.EventLogJpaRepository;
import com.example.order.application.out.OrderJpaRepository;
import com.example.order.domain.Order;
import com.example.order.domain.OrderEventLog;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {

	private final ApplicationEventPublisher eventPublisher;
	private final OrderJpaRepository orderJpaRepository;
	private final EventLogJpaRepository eventLogJpaRepository;

	@Override
	public long createOrder(Long productId, Integer price, String sagaId) {
		Order savedOrder = orderJpaRepository.save(Order.created(productId, price));

		eventPublisher.publishEvent(
			new OrderCreatedEvent(sagaId, UUID.randomUUID().toString(), savedOrder.getId(), productId, price));

		return savedOrder.getId();
	}

	@Override
	public void completeOrder(Long orderId, String sagaId, String eventId) {
		if (eventLogJpaRepository.existsById(eventId)) {
			return;
		}

		Order order = orderJpaRepository.findById(orderId).orElseThrow();

		order.complete();

		eventLogJpaRepository.save(OrderEventLog.of(eventId, sagaId));
	}

	@Override
	public void cancelOrder(Long orderId, String sagaId, String eventId) {
		if (eventLogJpaRepository.existsById(eventId)) {
			return;
		}

		Order order = orderJpaRepository.findById(orderId).orElseThrow();

		order.cancel();

		eventLogJpaRepository.save(OrderEventLog.of(eventId, sagaId));
	}

	@Override
	public PaymentReservationResponse pend(Long orderId, String sagaId) {
		Order order = orderJpaRepository.findById(orderId).orElseThrow();

		order.pend();

		return new PaymentReservationResponse(order.getId(), order.getProductId(), order.getPrice());
	}
}
