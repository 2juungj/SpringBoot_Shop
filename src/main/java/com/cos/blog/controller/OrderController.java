package com.cos.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.cos.blog.model.CartItem;
import com.cos.blog.model.Item;
import com.cos.blog.repository.ItemRepository;
import com.cos.blog.service.OrderService;

@Controller
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	ItemRepository itemRepository;
	
	// 장바구니 페이지 -> 주문 페이지
	@GetMapping("/order/orderCart")
	public String orderCartForm(Model model, @RequestParam("id") List<Integer> ids) {
		
		List<CartItem> cartItemList = orderService.선택상품불러오기(ids);
		model.addAttribute("cartItems", cartItemList);
		return "order/orderCart";
	}
	
	// 상품 상세 페이지 -> 주문 페이지
	@GetMapping("/order/orderItem/{id}/{count}")
	public String orderItemForm(Model model, @PathVariable int id, @PathVariable int count) {
		Item item = itemRepository.findById(id).get();
		
		model.addAttribute("item", item);
		model.addAttribute("itemCount", count);
		return "order/orderItem";
	}

}
