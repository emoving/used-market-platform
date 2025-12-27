package com.example.product.application.in;

import com.example.product.adapter.in.ProductRequest;
import com.example.product.domain.Product;

public interface ProductUseCase {
	Product getProduct(Long productId);

	void registerProduct(ProductRequest request);

	void reserveProduct(Long orderId, Long productId, String sagaId, String eventId);

	void completeSale(Long orderId, Long productId, String sagaId, String eventId);

	void cancelReservation(Long productId, String sagaId, String eventId);
}
