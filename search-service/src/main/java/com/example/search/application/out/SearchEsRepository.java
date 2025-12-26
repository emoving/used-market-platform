package com.example.search.application.out;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.search.domain.ProductDocument;

public interface SearchEsRepository extends ElasticsearchRepository<ProductDocument, Long> {
	List<ProductDocument> findByTitleContaining(String title);

	List<ProductDocument> findByCategory(String category);

	List<ProductDocument> findByCategoryAndTitleContaining(String category, String title);

	List<ProductDocument> findByTitleContainingAndStatus(String title, String status);

	List<ProductDocument> findByCategoryAndStatus(String category, String status);
}