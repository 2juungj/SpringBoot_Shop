package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Item;
import com.cos.blog.service.CartService;

@RestController
public class CartApiController {
	
	@Autowired
	CartService cartService;
	
	@PostMapping("/cart/new")
	public ResponseDto<Integer> cartSave(@RequestBody Item item, @AuthenticationPrincipal PrincipalDetail principal){
		cartService.장바구니담기(item, principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}


