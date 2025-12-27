package com.example.search.application.in;

import java.util.List;

import com.example.search.domain.ProductDocument;

public interface ProductSearchUseCase {
	List<ProductDocument> searchByTitle(String keyword);

	List<ProductDocument> searchByCategory(String category, String keyword);

	void save(ProductDocument productDocument);

	void update(Long eventId, String status);
}
