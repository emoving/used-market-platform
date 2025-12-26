package com.example.search.adapter.in;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.search.application.in.ProductSearchUseCase;
import com.example.search.domain.ProductDocument;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class ProductSearchController {

	private final ProductSearchUseCase searchUseCase;

	@GetMapping("/products")
	public ResponseEntity<List<ProductDocument>> search(@RequestParam String keyword) {
		return ResponseEntity.ok(searchUseCase.searchByTitle(keyword));
	}

	@GetMapping("/categories/{category}")
	public ResponseEntity<List<ProductDocument>> getByCategory(@PathVariable String category,
		@RequestParam(required = false) String keyword) {
		return ResponseEntity.ok(searchUseCase.searchByCategory(category, keyword));
	}
}