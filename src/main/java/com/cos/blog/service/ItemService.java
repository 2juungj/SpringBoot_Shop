package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Item;
import com.cos.blog.model.User;
import com.cos.blog.repository.ItemRepository;

@Service
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Transactional
	public void 상품등록(Item item, User user) {
		item.setCount(0);
		item.setUser(user);
		itemRepository.save(item);
	}
	
	@Transactional(readOnly = true)
	public Item 상품불러오기(int id) {
		return itemRepository.findById(id).get();
	}
	
	@Transactional
	public void 상품수정(Item item, int id) {
		Item updateItem = itemRepository.findById(id).get();
		updateItem.setItemName(item.getItemName());
		updateItem.setItemText(item.getItemText());
		updateItem.setPrice(item.getPrice());
		updateItem.setStock(item.getStock());
		if (item.getItemImage() != null && !item.getItemImage().isEmpty()) {
	        updateItem.setItemImage(item.getItemImage());
	    }
		itemRepository.save(updateItem);
	}
	
	@Transactional
	public void 상품삭제(int id) {
		itemRepository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public Page<Item> 글목록(Pageable pageable) {
		return itemRepository.findAll(pageable);
	}
}
	
	
