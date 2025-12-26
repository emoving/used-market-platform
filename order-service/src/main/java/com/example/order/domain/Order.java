package com.example.order.domain;

import com.example.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

	@Column(unique = true)
	private Long productId;

	private Integer price;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	public static Order created(Long productId, Integer price) {
		Order order = new Order();

		order.productId = productId;
		order.price = price;
		order.status = OrderStatus.CREATED;

		return order;
	}

	public void pend() {
		if (this.status != OrderStatus.CREATED) {
			throw new IllegalStateException();
		}

		this.status = OrderStatus.PENDING;
	}

	public void complete() {
		this.status = OrderStatus.DONE;
	}

	public void cancel() {
		this.status = OrderStatus.CANCELED;
	}
}
