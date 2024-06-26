<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<br>
<br>



<div class="container" style="width: 70%">

	<!-- 장바구니에 상품을 담기 위해 itemId 값이 필요하다. -->
	<div class="row">
		<input type="hidden" value="${item.id}" id="id">
	</div>
	<div class="row" style="float: left; text-align: center; width: 35%;">
		<img alt="productPhoto" src="/image/upload/${fn:substringAfter(item.itemImage, 'C:\\fakepath\\')}" width="150%"">
	</div>

	<div class="row productInfo" style="width: 40%; float: right;">
		<div class="form-group" style="text-align: left;">
			<h1>${item.itemName }</h1>
			<c:if test="${item.user.id == principal.user.id}">
				<a href="/seller/update/${item.id}" class="btn btn-default">수정</a>
				<button id="btn-delete" class="btn btn-default">삭제</button>
			</c:if>
			<br> <br> <br> <br>
			<p>가격 : ${item.price}원</p>

			<c:choose>
				<c:when test="${item.price >= 30000}">
					<p>배송비: 무료</p>
				</c:when>
				<c:otherwise>
					<p>배송비: 2500원</p>
				</c:otherwise>
			</c:choose>

			<p>3만원 이상 결제시 무료배송</p>
			<!-- fmt:formatNumber 태그를 사용하여 정수형으로 만든다. (소수점 제거) -->
			<p>
				적립금:
				<fmt:formatNumber value="${item.price div 100}" pattern="###,###" />
				원
			</p>

			<c:choose>
				<c:when test="${item.stock < 1}">
					<br>
					<p style="color: red;">품절된 상품입니다.</p>
				</c:when>
				<c:otherwise>
					<div class="form-horizontal" style="text-align: left;">
						<label>구매수량 : </label> <select class="form-control" id="count">
							<c:forEach begin="1" end="${item.stock}" var="count">
								<option>${count}</option>
							</c:forEach>
						</select>
					</div>
				</c:otherwise>
			</c:choose>
		</div>


		<c:if test="${item.stock >= 1}">
			<div class="row">
				<div class="selected_option" style="text-align: right;"></div>
				<div style="text-align: center;">
					<button class="btn btn-default" id="btn-orderItemForm">주문하기</button>
					<button class="btn btn-default" id="btn-cart">장바구니</button>
					<button class="btn btn-default" id="btn-wishlist">위시리스트</button>
				</div>
			</div>
		</c:if>
	</div>
</div>




<div class="footer" style="position: fixed; left: 0; bottom: 0; width: 100%; background-color: #f5f5f5; text-align: center;">
	<script src="/js/item.js"></script>
	<script src="/js/cart.js"></script>
	<%@ include file="../layout/footer.jsp"%>
</div>