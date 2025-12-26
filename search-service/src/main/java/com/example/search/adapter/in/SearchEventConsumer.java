package com.example.search.adapter.in;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.common.KafkaTopics;
import com.example.common.event.ProductCreatedEvent;
import com.example.common.event.ProductStatusChangedEvent;
import com.example.search.domain.ProductDocument;
import com.example.search.application.out.SearchEsRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@KafkaListener(
	topics = KafkaTopics.PRODUCT_SEARCH_SYNC,
	groupId = "search-group"
)
public class SearchEventConsumer {

	private final SearchEsRepository searchRepository;

	@KafkaHandler
	public void onProductCreated(ProductCreatedEvent event) {
		ProductDocument document = ProductDocument.create(event);

		searchRepository.save(document);
	}

	@KafkaHandler
	public void onProductStatusChanged(ProductStatusChangedEvent event) {
		searchRepository.findById(event.productId())
			.ifPresent(doc -> {
				doc.updateStatus(event.status());

				searchRepository.save(doc);
			});
	}
}