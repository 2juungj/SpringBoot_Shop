package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	Optional<Cart> findByUserId(int id);
	Optional<Cart> findById(int id);
}