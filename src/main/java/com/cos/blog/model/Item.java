package com.cos.blog.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	
	@Column(nullable = false)
	private String itemName;		// 상품명
	
	
	private String itemText;		// 상품 설명
	
	@Column(nullable = false)
	private int price;					// 가격
	
	@Column(nullable = false)
	private int count;					// 판매 수량	
	
	@Column(nullable = false)
	private int stock;					// 재고
	
	@Column(nullable = false)
	private boolean soldout;		// 품절 유무
	// 판매수량과 품절유무는 자동으로
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;					// 판매자 
	
	
	private String itemImage;	// 상품 사진
	
}
