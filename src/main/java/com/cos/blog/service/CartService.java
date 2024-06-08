package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Cart;
import com.cos.blog.model.CartItem;
import com.cos.blog.model.Item;
import com.cos.blog.model.User;
import com.cos.blog.repository.CartItemRepository;
import com.cos.blog.repository.CartRepository;
import com.cos.blog.repository.ItemRepository;

@Service
public class CartService {

	@Autowired
	CartRepository cartRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	CartItemRepository cartItemRepository;

	@Transactional
	public void 장바구니담기(Item newItem, User user) {
		Cart cart = cartRepository.findByUserId(user.getId()) //사용자 userId로 사용자 장바구니 찾기
				.orElseGet(()->{ // 장바구니가 없다면 생성
					Cart newCart = Cart.builder()
							.user(user)
							.count(0)
							.build();
					return cartRepository.save(newCart);
				});
		
		Item item = itemRepository.findItemById(newItem.getId()); // itemId로 item를 불러온다.
		
		cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId()) // cartId와 itemId로 cartItem을 불러온다.
			    .map(existingCartItem -> { // CartItem이 존재할 경우 수량 추가
			    	if((existingCartItem.getCount() + newItem.getCount()) > item.getStock()) { // 장바구니에 담긴 상품 개수가 재고보다 많으면 재고 최대량에 맞게
			    		cart.setCount(cart.getCount() + item.getStock() - existingCartItem.getCount()); // 기존 장바구니 총 개수 + 해당 상품 재고 수 + 기존 해당 상품을 담아놓은 개수
			    		existingCartItem.setCount(item.getStock());
			    		cartRepository.save(cart);
			    	}
			    	else {
			    		existingCartItem.setCount(existingCartItem.getCount() + newItem.getCount()); // 재고보다 적으면 선택한 개수만큼 장바구니에 추가
			    		cart.setCount(cart.getCount() + newItem.getCount()); // 추가한 상품 수 만큼 cart 내 상품 총 개수 증가
			    		cartRepository.save(cart);
			    	}
			        return cartItemRepository.save(existingCartItem);
			    })
			    .orElseGet(() -> { // CartItem이 존재하지 않을 경우 생성
			        CartItem newCartItem = CartItem.builder()
			                .cart(cart)
			                .item(item)
			                .count(newItem.getCount())
			                .build();
			        cart.setCount(cart.getCount() + newItem.getCount());
		    		cartRepository.save(cart);
			        return cartItemRepository.save(newCartItem);
			    });
		
	}
}
