package com.example.product.application;

import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.example.common.event.ProductCreatedEvent;
import com.example.common.event.ProductReservedEvent;
import com.example.common.event.ProductSoledEvent;
import com.example.common.event.ProductStatusChangedEvent;
import com.example.product.adapter.in.ProductRequest;
import com.example.product.application.in.ProductUseCase;
import com.example.product.application.out.EventLogRepository;
import com.example.product.application.out.ProductJpaRepository;
import com.example.product.domain.Product;
import com.example.product.domain.ProductEventLog;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {

	private final ProductJpaRepository productJpaRepository;
	private final EventLogRepository eventLogRepository;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	public Product getProduct(Long productId) {
		return productJpaRepository.findById(productId).orElseThrow();
	}

	@Override
	public void registerProduct(ProductRequest request) {
		Product product = Product.of(request.title(), request.price(), request.category());

		productJpaRepository.save(product);

		eventPublisher.publishEvent(
			new ProductCreatedEvent(UUID.randomUUID().toString(), product.getId(), product.getTitle(),
				product.getPrice(), product.getCategory()));
	}

	@Override
	public void reserveProduct(Long orderId, Long productId, String sagaId, String eventId) {
		if (eventLogRepository.existsById(eventId)) {
			return;
		}

		Product product = productJpaRepository.findById(productId).orElseThrow();

		product.reserve();

		eventLogRepository.save(ProductEventLog.of(eventId, sagaId));

		eventPublisher.publishEvent(
			new ProductReservedEvent(sagaId, UUID.randomUUID().toString(), product.getId(), orderId, product.getPrice()));

		eventPublisher.publishEvent(
			new ProductStatusChangedEvent(UUID.randomUUID().toString(), productId, product.getStatus().name()));
	}

	@Override
	public void cancelReservation(Long productId, String sagaId, String eventId) {
		if (eventLogRepository.existsById(eventId)) {
			return;
		}

		Product product = productJpaRepository.findById(productId)
			.orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

		product.cancelReservation();

		eventLogRepository.save(ProductEventLog.of(eventId, sagaId));

		eventPublisher.publishEvent(
			new ProductStatusChangedEvent(UUID.randomUUID().toString(), productId, product.getStatus().name()));
	}

	@Override
	public void completeSale(Long orderId, Long productId, String sagaId, String eventId) {
		if (eventLogRepository.existsById(eventId)) {
			return;
		}

		Product product = productJpaRepository.findById(productId)
			.orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

		product.sold();

		eventLogRepository.save(ProductEventLog.of(eventId, sagaId));

		eventPublisher.publishEvent(new ProductSoledEvent(sagaId, UUID.randomUUID().toString(), orderId, productId));

		eventPublisher.publishEvent(
			new ProductStatusChangedEvent(UUID.randomUUID().toString(), productId, product.getStatus().name()));
	}
}
