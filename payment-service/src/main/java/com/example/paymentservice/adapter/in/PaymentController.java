package com.example.paymentservice.adapter.in;

import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.paymentservice.application.in.PaymentUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

	private final PaymentUseCase paymentUseCase;

	@PostMapping
	public void pay(@RequestBody PaymentRequest request) {
		String sagaId = UUID.randomUUID().toString();

		paymentUseCase.processPayment(request.orderId(), request.amount(), sagaId);
	}
}
