package com.cos.blog.model;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String itemName;		// 상품명
	private String itemText;		// 상품 설명
	private int price;					// 가격
	private int count;					// 판매 수량	
	private int stock;					// 재고
	private boolean soldout;		// 품절 유무
	// 판매수량과 품절유무는 자동으로
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;					// 판매자 
	
	@OneToMany(mappedBy = "item")
	private List<CartItem> cartItems = new ArrayList<>();
	
	private String itemImage;	// 상품 사진
	
}
