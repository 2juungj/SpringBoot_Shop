<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<br>
<div class="container">
	<div class="col-md-12" style="text-align: center;">
		<h1 class="page-header">주문내역</h1>
		<br>
	</div>
	<c:forEach var="order" items="${orders}">
		<div class="card m-2">
			<div class="card-body">
				<p>${order.createDate}</p>
				<p>주문 ID: ${order.id}</p>
				<a href="/order/detail/${order.id}" class="btn-default">상세보기</a>
			</div>
		</div>
	</c:forEach>

</div>



<%@ include file="../layout/footer.jsp"%>