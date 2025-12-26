package com.example.order.application.out;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.order.domain.Order;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
}
