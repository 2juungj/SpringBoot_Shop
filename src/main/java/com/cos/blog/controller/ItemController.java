package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.blog.model.Item;
import com.cos.blog.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	// 상품 페이지
	@GetMapping("/item")
	public String itemForm(Model model,
			@PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		model.addAttribute("items", itemService.글목록(pageable));
		return "item/itemForm";
	}
	
	// 상품 등록 페이지 (GET)
	@GetMapping("/seller/new")
	public String itemSaveForm(Item item) {
		return "seller/itemSave";
	}

	// 상품 수정 페이지 (GET)
	@GetMapping("/seller/update/{id}")
	public String itemUpdateForm(@PathVariable("id") int id, Model model) {
		model.addAttribute("item", itemService.상품불러오기(id));
		return "seller/itemUpdate";
	}
	
	// 상품 상세 페이지 (GET)
	@GetMapping("/item/{id}")
	public String itemView(Model model, @PathVariable("id") int id) {
		model.addAttribute("item", itemService.상품불러오기(id));
		return "item/detail";
	}

	// 상품 등록 (POST)
	@PostMapping("/seller/new/product")
	public String itemSave(Item item) {
		itemService.상품등록(item);
		return "/";
	}

	// 상품 수정 (POST)
	@PostMapping("/seller/update/product/{id}")
	public String itemUpdate(Item item, @PathVariable("id") int id) {
		itemService.상품수정(item, id);
		return "redrect:/";
	}
	
	// 상품 삭제 (DELETE)
	@GetMapping("/seller/delete/{id}")
	public String itemDelete(@PathVariable("id") int id) {
		itemService.상품삭제(id);
		return "/";
	}

}
