package com.cos.blog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "orderId")
	private Order order;

	// 구매자 혹은 판매자가 탈퇴하여도 거래내역이 남아 있어야 함.
	// 불량 상품 AS 등
	private int userId; // 구매자
	private int sellerId; // 판매자

	private int itemId; // 주문 상품 id
	private String itemName; // 주문 상품명
	private int itemPrice; // 주문 상품 가격
	private int itemCount; // 주문 상품 수량
	private int itemAllPrice; // 총 금액 (= 가격 * 수량)

	private int cancel; // 주문 취소 여부 (0: 취소X, 1: 취소 O)

	// 빌더는 1개만 만들 수 있으므로 사용X
	// 장바구니 주문 (장바구니 페이지)
	public static OrderItem orderCartItem(User user, CartItem cartItem, Order order) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItemId(cartItem.getItem().getId());
		orderItem.setUserId(user.getId());
		orderItem.setOrder(order);
		orderItem.setItemName(cartItem.getItem().getItemName());
		orderItem.setItemPrice(cartItem.getItem().getPrice());
		orderItem.setItemCount(cartItem.getCount());
		orderItem.setItemAllPrice(cartItem.getItem().getPrice() * cartItem.getCount());
		orderItem.setCancel(0);
		return orderItem;
	}
	
	// 상품 개별 주문 (상품 페이지)
	public static OrderItem orderItem(User user, Item item, int count, Order order) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItemId(item.getId());
		orderItem.setUserId(user.getId());
		orderItem.setSellerId(item.getUser().getId());
		orderItem.setOrder(order);
		orderItem.setItemName(item.getItemName());
		orderItem.setItemPrice(item.getPrice());
		orderItem.setItemCount(count);
		orderItem.setItemAllPrice(item.getPrice() * count);
		orderItem.setCancel(0);
		return orderItem;
	}

}