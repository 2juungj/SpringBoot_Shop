package com.cos.blog.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
	Item findItemById(int id);
	Page<Item> findByItemNameContaining(String searchKeyword, Pageable pageable);
}
