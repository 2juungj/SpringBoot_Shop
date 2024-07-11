package com.cos.blog.model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders") // order는 SQL 예약어이기 때문에 테이블명을 orders로 한다.
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int userId; // 구매자 (구매자가 탈퇴하여도 주문내역은 남아 있어야 함.)

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@CreationTimestamp // 시간이 자동 입력
	private Timestamp createDate; // 주문한 날짜와 시간

	private int shippingFee; // 배송비

	private int allPrice; // 상품 총액(배송비 포함)
	
	private int allCount; // 해당 주문 건 상품 총 개수
	
	// 구매자의 개인정보가 수정되어도 주문한 정보는 변경되면 안되므로 order에 저장
	private String orderName;
	private String orderAddress;
	private String orderEmail;
	private String orderTel;
	
	private int cancel; // 주문 취소 여부 (0: 취소X, 1: 취소 요청, 2: 취소 O)
	private String cancelText; // 주문 취소 사유

	@Builder
	public Order(User user, Timestamp createDate, int shippingFee, int allPrice, int allCount, String orderName, String orderAddress, String orderEmail, String orderTel) {
		this.userId = user.getId();
		this.createDate = createDate;
		this.shippingFee = shippingFee;
		this.allPrice = allPrice;
		this.allCount = allCount;
		this.orderName = orderName;
		this.orderAddress = orderAddress;
		this.orderEmail =orderEmail;
		this.orderTel = orderTel;
		this.cancel = 0;
	}
}