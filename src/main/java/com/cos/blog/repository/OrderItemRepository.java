package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
