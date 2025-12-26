package com.example.order.application.out;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.order.domain.OrderEventLog;

public interface EventLogJpaRepository extends JpaRepository<OrderEventLog, String> {
}
