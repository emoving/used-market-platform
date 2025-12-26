package com.example.search.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.example.common.event.ProductCreatedEvent;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Document(indexName = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDocument {

	@Id
	private Long id;

	@Field(type = FieldType.Text)
	private String title;

	@Field(type = FieldType.Integer)
	private Integer price;

	@Field(type = FieldType.Keyword)
	private String category;

	@Field(type = FieldType.Keyword)
	private String status;

	@Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
	private LocalDateTime createdAt;

	public static ProductDocument create(ProductCreatedEvent event) {
		ProductDocument doc = new ProductDocument();

		doc.id = event.productId();
		doc.title = event.title();
		doc.price = event.price();
		doc.category = event.category();
		doc.status = "SALE";
		doc.createdAt = LocalDateTime.now();

		return doc;
	}

	public void updateStatus(String status) {
		this.status = status;
	}
}