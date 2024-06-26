package com.cos.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.User;
import com.cos.blog.model.Cart;
import com.cos.blog.model.CartItem;
import com.cos.blog.repository.CartRepository;
import com.cos.blog.repository.UserRepository;
import com.cos.blog.service.CartService;

@Controller
public class CartController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	CartService cartService;

	// 장바구니 페이지
	@GetMapping("/cart")
	public String cart(Model model, @AuthenticationPrincipal PrincipalDetail principal) {
		User user =  userRepository.findById(principal.getUser().getId()).get(); // 로그인 한 사용자의 id로 user 데이터 호출
		Cart cart = cartRepository.findByUserId(user.getId()).get(); // 로그인 한 사용자의 장바구니 호출

		List<CartItem> cartItemList = cartService.장바구니불러오기(cart); // 해당 사용자의 장바구니에 담긴 상품 모두 호출
		
		model.addAttribute("cartItems", cartItemList);
		model.addAttribute("user", user);
		return "cart/cart";
	}

}
