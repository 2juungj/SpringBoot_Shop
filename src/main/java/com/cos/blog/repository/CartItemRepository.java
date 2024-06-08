package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
	Optional<CartItem> findByCartIdAndItemId(int cartId, int itemId);
}