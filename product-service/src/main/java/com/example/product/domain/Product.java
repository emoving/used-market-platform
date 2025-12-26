package com.example.product.domain;

import com.example.common.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {
	private String title;
	private Integer price;
	private String category;

	@Enumerated(EnumType.STRING)
	private ProductStatus status; // SALE, RESERVED, SOLD

	public static Product of(String title, Integer price, String category) {
		Product product = new Product();

		product.title = title;
		product.price = price;
		product.category = category;
		product.status = ProductStatus.SALE;

		return product;
	}

	public void reserve() {
		if (this.status != ProductStatus.SALE) {
			throw new IllegalStateException("판매 중인 상품만 예약 가능합니다.");
		}

		this.status = ProductStatus.RESERVED;
	}

	public void sold() {
		this.status = ProductStatus.SOLD;
	}

	public void cancelReservation() {
		this.status = ProductStatus.SALE;
	}
}