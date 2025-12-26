package com.example.order.adapter.in;

public record OrderRequest(
	Long productId,
	Integer price
) {}