package com.cos.blog.service;

import java.util.ArrayList;
import java.util.Collections;
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
		Item item = itemRepository.findById(itemId).orElseThrow(() -> {
			return new IllegalArgumentException("상품 불러오기 실패: 아이디를 찾을 수 없음."); // 구매 진행 아이템 호출
		});

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
	
	@Transactional(readOnly = true)
	public List<Order> 주문불러오기(PrincipalDetail principal){
		int userId = principal.getUser().getId(); // Order 엔티티는 User와 연결되어 있지 않고 userId 값만 지닌다.
		
		List<Order> userOrders = new ArrayList<>(); // 해당 사용자의 Order를 담을 리스트
		List<Order> orders = orderRepository.findAll(); // DB의 모든 Order를 리스트로 호출

		for (Order order : orders) { // userId와 order.userId가 일치하는 order를 userOrders에 추가
			if (order.getUserId() == userId) {
				userOrders.add(order);
			}
		}
		// 리스트를 역순으로 정렬 (최신 주문 건이 상단에 위치하도록 하기 위함)
	    Collections.reverse(userOrders);
	    
		return userOrders;
	}
	
	@Transactional(readOnly = true)
	public List<OrderItem> 주문상품불러오기(int id){
		List<OrderItem> userOrderItems = new ArrayList<>(); // 해당 orderId의 OrderItem들을 담을 리스트
		List<OrderItem> orderItems = orderItemRepository.findAll(); // DB의 모든 OrderItem을 리스트로 호출
		
		for (OrderItem orderItem : orderItems) { // orderId가 일치하는 orderItem을 userOrderItems 리스트에 추가
			if(orderItem.getOrder().getId() == id) {
				userOrderItems.add(orderItem);
			}
		}
		return userOrderItems;
	}
	
	@Transactional(readOnly = true)
	public List<Order> 판매자주문불러오기(){
		List<Order> orderList = orderRepository.findAll(); // 모든 관리자와 판매자가 주문을 공통 관리할 것이므로 모든 order를 리스트로 호출
		
		// 리스트를 역순으로 정렬 (최신 주문 건이 상단에 위치하도록 하기 위함)
	    Collections.reverse(orderList);
	    
	    return orderList;
	}
	
	@Transactional
	public void 주문취소요청(Order order) {
		Order userOrder = orderRepository.findById(order.getId()).orElseThrow(() -> {
			return new IllegalArgumentException("주문 불러오기 실패: 아이디를 찾을 수 없음.");
		});
		
		// order의 주문취소사유와 취소현황 수정
		userOrder.setCancelText(order.getCancelText());
		userOrder.setCancel(1);
	}
	
	@Transactional
	public void 주문취소(Order order) {
		Order userOrder = orderRepository.findById(order.getId()).orElseThrow(() -> {
			return new IllegalArgumentException("주문 불러오기 실패: 아이디를 찾을 수 없음.");
		});
		
		// order의 주문취소현황을 주문취소로 수정
		userOrder.setCancel(2);
	}

}
