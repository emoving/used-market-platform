package com.example.paymentservice.application.out;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.paymentservice.domain.Payment;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {
}
