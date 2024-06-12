package com.cos.blog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.config.auth.PrincipalDetail;
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
	public void 장바구니담기(Item newItem, PrincipalDetail principal) {
		User user = principal.getUser();
		Cart cart = cartRepository.findByUserId(user.getId()) //사용자 userId로 사용자 장바구니 찾기
				.orElseGet(()->{ // 장바구니가 없다면 생성
					Cart newCart = Cart.builder()
							.user(user)
							.count(0)
							.build();
					return cartRepository.save(newCart);
				});
		
		Item item = itemRepository.findById(newItem.getId()).get(); // itemId로 item를 불러온다.
		
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
	
	// 카트 상품 리스트를 모두 불러와 해당 사용자의 cartId와 일치하는 상품만 반환
	@Transactional(readOnly = true)
	public List<CartItem> 장바구니불러오기(Cart cart){
		
		int userCartId = cart.getId(); // 사용자의 cartId 불러오기
		
		List<CartItem> userCartItems = new ArrayList<>(); // 사용자의 cartId와 일치하는 cartItem 담을 리스트
		
		List<CartItem> cartItems = cartItemRepository.findAll(); // id와 상관없이 cartItem의 모든 상품 불러오기
		
		for(CartItem cartItem : cartItems) { // 사용자의 cartId와 일치한 cartItem을 userCartItems에 담기
			if(cartItem.getCart().getId() == userCartId) {
				userCartItems.add(cartItem);
			}
		}
		return userCartItems;
	}
	
	@Transactional
	public void 장바구니삭제(List<Integer> ids, PrincipalDetail principal) {
		User user = principal.getUser();
		Cart cart = cartRepository.findByUserId(user.getId()).get();
		int cartCount = cart.getCount(); // 해당 유저의 cart 테이블의 count 호출
		
		for (Integer itemId : ids) {
			CartItem cartItem = cartItemRepository.findById(itemId).get();
			cartCount -= cartItem.getCount(); // 삭제할 상품이 담겨 있던 개수만큼 빼기
			cartItemRepository.deleteById(itemId);
		}
		
		cart.setCount(cartCount); // 수정된 count를 cart에 저장
		cartRepository.save(cart); // 수정된 cart를 DB에 저장
	}
	
}
