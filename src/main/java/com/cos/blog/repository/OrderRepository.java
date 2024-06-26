package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
