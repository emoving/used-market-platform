package com.example.product.application.out;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.product.domain.ProductEventLog;

public interface EventLogRepository extends JpaRepository<ProductEventLog, String> {
}
