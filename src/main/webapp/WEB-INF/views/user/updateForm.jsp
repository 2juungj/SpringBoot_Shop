<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<br>
<div class="container">
	<form action="/action_page.php">
		<input type="hidden" id="id" value="${principal.user.id}"/>
		<div class="form-group">
			<label for="username">아이디:</label> 
			<input type="text" value="${principal.user.username}" class="form-control" placeholder="Enter username" id="username" readonly>
		</div>

		<c:if test="${empty principal.user.oauth }">
			<div class="form-group">
				<label for="password">비밀번호:</label> 
				<input type="password" class="form-control" placeholder="새 비밀번호 입력" id="password">
			</div>
		</c:if>

		<div class="form-group">
			<label for="email">이메일:</label> 
			<input type="email" value="${principal.user.email}" class="form-control" placeholder="Enter email" id="email" readonly>
		</div>
		
		<div class="form-group">
			<label for="email">주소:</label> 
			<input type="text" value="${principal.user.address}" class="form-control" placeholder="주소를 입력해주세요." id="address" >
		</div>
		
		<div class="form-group">
			<label for="email">전화번호:</label> 
			<input type="tel" value="${principal.user.tel}" class="form-control" placeholder="전화번호를 입력해주세요." id="tel" >
		</div>
	</form>

	<button id="btn-update" class="btn btn-primary">회원정보수정</button>

</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>



