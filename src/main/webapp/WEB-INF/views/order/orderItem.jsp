<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="container">
		<div class="col-md-12" style="text-align: center;">
			<br>
			<h1 class="page-header">주문하기</h1>
			<br>
		</div>
		<div class="row qnas" style="text-align: center;">
			<table class="table table-hover" style="width: 70%; margin: auto; border-bottom: 1px solid #D5D5D5;">
				<thead>
					<tr>
						<!-- 두줄로 출력되지 않게 style="white-space: nowrap;" 설정 -->
						<th colspan="2" style="text-align: center;">이미지</th>
						<th style="white-space: nowrap;">상품명</th> 
						<th style="white-space: nowrap;">가격</th>
						<th style="white-space: nowrap;">수량</th>
						<th style="white-space: nowrap;">상품정보</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><img alt="thumbnail" src="/image/upload/${fn:substringAfter(item.itemImage, 'C:\\fakepath\\')}" width="20%"></td>
						<td>
							<input type="hidden" id="itemId" value="${item.id}">
							<input type="hidden" id="itemCount" value="${itemCount}">
						</td>
						<td>
							${item.itemName}
							<input type="hidden" id="itemName" value="${item.itemName}">	
						</td>
						<td>${item.price}원</td>
						<td>${itemCount}</td>
						<td>${item.itemText}</td>
						</tr>
				</tbody>
			</table>
		</div>
		
		<div class="col-md-12" style="text-align: center;">
			<br><br><br><br>
			<h1 class="page-header">주문정보 확인</h1>
			<h5 style="color: red;">주문자 정보와 배송지가 다른 경우 직접 입력해주세요.</h5>
			<br>
		</div>
		<div class="row justify-content-center">
			<div class="form-horizontal">
				<div class="form-group">
					<label for="username" class="col-sm-2 control-label" style="white-space: nowrap;">ID</label>
					<div class="col-sm-10">
						<input class="form-control" id="username"  style="width: 130%;" value="${principal.user.username}" readonly>
					</div>
				</div>
				
				<div class="form-group">
					<label for="ordername" class="col-sm-2 control-label" style="white-space: nowrap;">주문자 성함</label>
					<div class="col-sm-10">
						<input class="form-control" id="ordername" style="width: 130%;" value="${principal.user.ordername}" placeholder="이름 입력" required>
					</div>
				</div>				
				<div class="form-group">
					<label for="address" class="col-sm-2 control-label" style="white-space: nowrap;">주소</label>
					<div class="col-sm-10">
						<input class="form-control" type="text" id="address" value="${principal.user.address}" style="width: 130%;" placeholder="주소 입력" required>
					</div>
				</div>				
				<div class="form-group">
					<label for="email" class="col-sm-2 control-label" style="white-space: nowrap;">Email</label>
					<div class="col-sm-10">
						<input class="form-control" type="email" id="email"  value="${principal.user.email}" style="width: 130%;" placeholder="이메일 입력" required>
					</div>
				</div>
				<div class="form-group">
					<label for="tel" class="col-sm-2 control-label" style="white-space: nowrap;">전화번호</label>
					<div class="col-sm-10">
						<input class="form-control" type="tel" id="tel" value="${principal.user.tel}" style="width: 130%;" placeholder="전화번호 입력" required>
					</div>
				</div>				
			</div>
		</div>
		
		<div class="col-md-12" style="text-align: center; margin: 80px 0;">
			<hr>
			<div class="col-md-12" style="text-align: center; margin: 50px 0;">
				<c:set var="itemPrice" value="${item.price}" />
				<c:set var="itemCount" value="${itemCount}" />
				<label>상품가격 :&nbsp; ${itemPrice * itemCount}원</label>
				<label>
					<c:choose>
    					<c:when test="${itemPrice * itemCount >= 30000}">
      						  배송비 무료
      						  <c:set var="shippingFee" value="0" />
      						  <input type="hidden" id="shippingFee" value="0">
    					</c:when>
    					<c:otherwise>
        					배송비: 3000원 <!-- 테스트를 위해 배송비 0 -->
        					<c:set var="shippingFee" value="0" />
        					<input type="hidden" id="shippingFee" value="0">
  						</c:otherwise>
					</c:choose>
				</label>
				<div>
					<label style="font-size: 1.5em;">총 결제금액 : ${itemPrice * itemCount + shippingFee}원
					<input type="hidden" id="allPrice" value="${itemPrice * itemCount + shippingFee}">
					</label>
				</div>
			</div>
			<div>
				<button class="btn-default"  id="btn-orderItem">결제하기</button>
			</div>
		</div>
	</div>
	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<script src="/js/order.js"></script> <%@ include file="../layout/footer.jsp"%>