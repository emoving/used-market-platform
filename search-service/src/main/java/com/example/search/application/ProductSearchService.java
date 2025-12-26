package com.example.search.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.search.application.in.ProductSearchUseCase;
import com.example.search.application.out.SearchEsRepository;
import com.example.search.domain.ProductDocument;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductSearchService implements ProductSearchUseCase {

	private final SearchEsRepository searchRepository;

	@Override
	public List<ProductDocument> searchByTitle(String keyword) {
		return searchRepository.findByTitleContaining(keyword);
	}

	@Override
	public List<ProductDocument> searchByCategory(String category, String keyword) {
		if (StringUtils.hasText(keyword)) {
			return searchRepository.findByCategoryAndTitleContaining(category, keyword);
		}

		return searchRepository.findByCategory(category);
	}
}