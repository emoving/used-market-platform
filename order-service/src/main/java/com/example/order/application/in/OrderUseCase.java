package com.example.order.application.in;

import com.example.common.dto.PaymentReservationResponse;

public interface OrderUseCase {
	long createOrder(Long productId, String sagaId);

	void completeOrder(Long orderId, String sagaId, String eventId);

	void cancelOrder(Long orderId, String sagaId, String eventId);

	PaymentReservationResponse pend(Long orderId, String sagaId);
}
