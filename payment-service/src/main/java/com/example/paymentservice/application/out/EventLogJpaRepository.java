package com.example.paymentservice.application.out;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.paymentservice.domain.PaymentEventLog;

public interface EventLogJpaRepository extends JpaRepository<PaymentEventLog, String> {
}
