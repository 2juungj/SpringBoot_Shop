package com.cos.blog.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
	Optional<Item> findById(int id);
	Page<Item> findByItemNameContaining(String searchKeyword, Pageable pageable);
}
