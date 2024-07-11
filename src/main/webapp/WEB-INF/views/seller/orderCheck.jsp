<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<br>
<div class="container">
	<div class="col-md-12" style="text-align: center;">
		<h1 class="page-header">주문관리</h1>
		<br>
	</div>
	<c:forEach var="order" items="${orders}">
		<div class="card m-2">
			<div class="card-body">
				<p>${order.createDate}</p>
				<p>주문 ID: ${order.id}</p>
				<p>구매자 ID: ${order.userId}</p>
				<c:choose>
					<c:when test="${order.cancel == 0}">
						<p>주문 진행 중</p>
					</c:when>
					<c:when test="${order.cancel == 1}">
						<p style="color: red;">주문취소 요청</p>
					</c:when>
					<c:otherwise>
						<p style="color: blue;">주문취소 완료</p>
					</c:otherwise>
				</c:choose>
				<a href="/seller/order/${order.id}" class="btn-default">상세보기</a>
			</div>
		</div>
	</c:forEach>

</div>



<%@ include file="../layout/footer.jsp"%>