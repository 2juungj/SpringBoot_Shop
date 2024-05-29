package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Item;
import com.cos.blog.service.ItemService;

@Controller
public class ItemApiController {

	@Autowired
	private ItemService itemService;

	// 상품 등록 (POST)
	@PostMapping("/seller/new/item")
	@ResponseBody
	public ResponseDto<Integer> itemSave(@RequestBody Item item, @AuthenticationPrincipal PrincipalDetail principal) {
		itemService.상품등록(item, principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	// 상품 수정 (POST)
	@PostMapping("/seller/update/item/{id}")
	public String itemUpdate(Item item, @PathVariable("id") int id) {
		itemService.상품수정(item, id);
		return "redrect:/";
	}
	
	// 상품 삭제 (DELETE)
	@DeleteMapping("/seller/delete/{id}")
	public String itemDelete(@PathVariable("id") int id) {
		itemService.상품삭제(id);
		return "/";
	}

}
