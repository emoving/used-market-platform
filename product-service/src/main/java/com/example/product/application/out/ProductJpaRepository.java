package com.example.product.application.out;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.product.domain.Product;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
}
