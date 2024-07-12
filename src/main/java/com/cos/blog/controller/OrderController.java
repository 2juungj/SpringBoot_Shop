package com.cos.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.CartItem;
import com.cos.blog.model.Item;
import com.cos.blog.model.Order;
import com.cos.blog.model.OrderItem;
import com.cos.blog.repository.ItemRepository;
import com.cos.blog.repository.OrderRepository;
import com.cos.blog.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private OrderRepository orderRepository;

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

	// 주문 확인 페이지
	@GetMapping("/order/orderCheck")
	public String orderCheckForm(Model model, @AuthenticationPrincipal PrincipalDetail principal) {
		List<Order> orderList = orderService.주문불러오기(principal);

		model.addAttribute("orders", orderList);
		return "order/orderCheck";
	}

	// 주문 상세 페이지
	@GetMapping("/order/detail/{id}")
	public String orderDetailForm(Model model, @PathVariable int id) { // id: orderId
		List<OrderItem> orderItemList = orderService.주문상품불러오기(id);
		Order order = orderRepository.findById(id).get();

		model.addAttribute("orderItems", orderItemList);
		model.addAttribute("order", order);
		return "order/orderDetail";
	}

	// 판매자 주문 관리 페이지
	@GetMapping("/seller/orderCheck")
	public String sellerOrderCheckForm(Model model) {
		List<Order> orderList = orderService.판매자주문불러오기();

		model.addAttribute("orders", orderList);
		return "seller/orderCheck";
	}

	// 판매자 주문 상세 페이지
	@GetMapping("/seller/order/{id}")
	public String sellerOrderDetailForm(Model model, @PathVariable int id) {
		List<OrderItem> orderItemList = orderService.주문상품불러오기(id);
		Order order = orderRepository.findById(id).get();

		model.addAttribute("orderItems", orderItemList);
		model.addAttribute("order", order);
		return "seller/orderDetail";
	}

}
