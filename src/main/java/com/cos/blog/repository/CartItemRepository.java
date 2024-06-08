package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
}
