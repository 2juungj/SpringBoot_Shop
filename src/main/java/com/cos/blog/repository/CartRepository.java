package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {
}
