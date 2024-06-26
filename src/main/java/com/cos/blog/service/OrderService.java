package com.cos.blog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.CartItem;
import com.cos.blog.model.Item;
import com.cos.blog.model.Order;
import com.cos.blog.model.OrderItem;
import com.cos.blog.model.User;
import com.cos.blog.repository.CartItemRepository;
import com.cos.blog.repository.ItemRepository;
import com.cos.blog.repository.OrderItemRepository;
import com.cos.blog.repository.OrderRepository;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class OrderService {

	@Autowired
	CartItemRepository cartItemRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	OrderItemRepository orderItemRepository;

	@Autowired
	OrderRepository orderRepository;

	@Transactional(readOnly = true)
	public List<CartItem> 선택상품불러오기(List<Integer> ids) {
		List<CartItem> selectedCartItems = new ArrayList<>(); // 선택된 cartItem들의 리스트
		List<CartItem> cartItems = cartItemRepository.findAll(); // DB의 모든 cartItem을 리스트로 호출

		for (CartItem cartItem : cartItems) { // ids에 포함된 값과 cartItem.id가 일치하는 cartItem을 selectedCartItems에 추가
			if (ids.contains(cartItem.getId())) {
				selectedCartItems.add(cartItem);
			}
		}

		return selectedCartItems;
	}

	@Transactional
	public String 장바구니상품주문(JsonNode requestData, PrincipalDetail principal) {
		JsonNode idsNode = requestData.get("ids");
		JsonNode allPriceNode = requestData.get("allPrice");
		JsonNode allCountNode = requestData.get("allCount");
		JsonNode ordernameNode = requestData.get("ordername");
		JsonNode addressNode = requestData.get("address");
		JsonNode emailNode = requestData.get("email");
		JsonNode telNode = requestData.get("tel");
		JsonNode shippingFeeNode = requestData.get("shippingFee");

		if (idsNode == null || !idsNode.isArray() || allPriceNode == null || allCountNode == null
				|| ordernameNode == null || addressNode == null || emailNode == null || telNode == null
				|| shippingFeeNode == null) {
			throw new IllegalArgumentException("데이터가 누락되었습니다.");
		}

		int allPrice = allPriceNode.asInt();
		int allCount = allCountNode.asInt();
		String ordername = ordernameNode.asText();
		String address = addressNode.asText();
		String email = emailNode.asText();
		String tel = telNode.asText();
		int shippingFee = shippingFeeNode.asInt();

		User user = principal.getUser();

		Order order = Order.builder() // order 생성
				.user(user).shippingFee(shippingFee).allPrice(allPrice).allCount(allCount).orderName(ordername)
				.orderAddress(address).orderEmail(email).orderTel(tel).build();

		// 주문 항목 생성 및 저장

		for (JsonNode idNode : idsNode) {
			int cartItemId = idNode.asInt();
			CartItem cartItem = cartItemRepository.findById(cartItemId)
					.orElseThrow(() -> new NoSuchElementException("장바구니 항목을 찾을 수 없습니다. ID: " + cartItemId));
			if (cartItem != null) {
				Item item = cartItem.getItem();
				int itemCount = cartItem.getCount();

				// 재고 확인
				if (item.getStock() < itemCount) {
					System.out.println("상품의 재고가 부족합니다: " + item.getItemName());
				}

				OrderItem orderItem = OrderItem.orderItem(user, item, itemCount, order);
				orderItemRepository.save(orderItem);

				// 재고 업데이트
				item.setStock(item.getStock() - itemCount);
				itemRepository.save(item);
				
				// 장바구니에서 해당 상품 삭제
				cartItemRepository.deleteById(cartItemId);
			}
		}

		orderRepository.save(order);

		return "redirect:/";
	}

	@Transactional
	public String 단일상품주문(JsonNode requestData, PrincipalDetail principal) {
		// 가져온 데이터를 변수에 저장
		JsonNode idNode = requestData.get("id");
		JsonNode countNode = requestData.get("count");
		JsonNode allPriceNode = requestData.get("allPrice");
		JsonNode shippingFeeNode = requestData.get("shippingFee");
		JsonNode ordernameNode = requestData.get("ordername");
		JsonNode addressNode = requestData.get("address");
		JsonNode emailNode = requestData.get("email");
		JsonNode telNode = requestData.get("tel");

		// 필드가 null인지 확인 후 값을 가져오기
		if (idNode == null || countNode == null || allPriceNode == null || shippingFeeNode == null
				|| ordernameNode == null || addressNode == null || emailNode == null || telNode == null) {
			throw new IllegalArgumentException("데이터가 누락되었습니다.");
		}

		int itemId = idNode.asInt();
		int itemCount = countNode.asInt();
		int allPrice = allPriceNode.asInt();
		int shippingFee = shippingFeeNode.asInt();
		String ordername = ordernameNode.asText();
		String address = addressNode.asText();
		String email = emailNode.asText();
		String tel = telNode.asText();

		User user = principal.getUser();
		Item item = itemRepository.findById(itemId).get(); // 구매 진행 아이템 호출

		if (item.getStock() < itemCount) { // 주문 진행 전 다시 한번 재고 확인
			System.out.println("상품의 재고가 부족합니다.");
			return "redirect:/item/" + itemId;
		}

		Order order = Order.builder() // order 생성
				.user(user).shippingFee(shippingFee).allPrice(allPrice).allCount(itemCount).orderName(ordername)
				.orderAddress(address).orderEmail(email).orderTel(tel).build();

		// orderItem 생성
		OrderItem orderItem = OrderItem.orderItem(user, item, itemCount, order);

		// 주문 저장
		orderItemRepository.save(orderItem);
		orderRepository.save(order);

		// 주문 아이템 재고 업데이트
		item.setStock(item.getStock() - itemCount);
		itemRepository.save(item);
		return "redirect:/";
	}

}
