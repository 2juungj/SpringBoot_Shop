package com.cos.blog.model;



import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	User user; // 해당 유저의 장바구니
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "itemId")
	private Item item;
	
	private int cartCount; // 카트에 담긴 상품 개수
	
}