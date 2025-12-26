package com.example.paymentservice.domain;

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
@Table(name = "payments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {
	private Long orderId;
	
	private Integer amount;

	@Enumerated(EnumType.STRING)
	private PaymentStatus status;

	public static Payment of(Long orderId, Integer price) {
		Payment payment = new Payment();

		payment.orderId = orderId;
		payment.amount = price;
		payment.status = PaymentStatus.PAID;

		return payment;
	}

	public void cancel() {
		this.status = PaymentStatus.CANCELED;
	}
}
